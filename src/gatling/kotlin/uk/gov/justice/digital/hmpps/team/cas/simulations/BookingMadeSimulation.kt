package uk.gov.justice.digital.hmpps.team.cas.simulations

import io.gatling.javaapi.core.CoreDsl.*
import uk.gov.justice.digital.hmpps.BaseSimulationFrontEndRoutes
import uk.gov.justice.digital.hmpps.team.cas.constants.*
import uk.gov.justice.digital.hmpps.team.cas.service.BookingMadeScenarioService
import kotlin.time.*
import kotlin.time.Duration.Companion.seconds

const val dev_profile: String = "dev"
const val test_profile: String = "test"

class BookingMadeSimulation(bookingMadeScenarioService: BookingMadeScenarioService = BookingMadeScenarioService()): BaseSimulationFrontEndRoutes() {
    init {
        val cruManagementAreaId = if (System.getProperty("profile") == dev_profile || System.getProperty("profile") == test_profile) {
            apAreaIdForNorthEast
        } else apAreaIdForSouthWestAndSouthCentral

        val fastBookingMadeScenario =
            bookingMadeScenarioService.buildBookingMadeScenario(
                scenarioName = "Booking Made Scenario - Fast Users",
                cruDashboardNotMatchedStatus,
                cruManagementAreaId,
                fastBookingMadePauseOnCruDashboardPage,
                fastBookingMadePauseOnPlacementRequestPage,
                fastBookingMadePauseOnFindASpacePage,
                fastBookingMadePauseOnOccupancyViewPage,
                fastBookingMadePauseOnConfirmBookingPage,
                fastBookingMadePauseOnConfirmBookingSubmitPage
            )

        val slowerBookingMadeScenario =
            bookingMadeScenarioService.buildBookingMadeScenario(
                scenarioName = "Booking Made Scenario - Slower Users",
                cruDashboardNotMatchedStatus,
                cruManagementAreaId,
                slowerBookingMadePauseOnCruDashboardPage,
                slowerBookingMadePauseOnPlacementRequestPage,
                slowerBookingMadePauseOnFindASpacePage,
                slowerBookingMadePauseOnOccupancyViewPage,
                slowerBookingMadePauseOnConfirmBookingPage,
                slowerBookingMadePauseOnConfirmBookingSubmitPage
            )
        setUp(
            fastBookingMadeScenario.injectClosed(
                constantConcurrentUsers(noOfFastUsers).during(20.seconds.toJavaDuration())
            ),
            slowerBookingMadeScenario.injectClosed(
                constantConcurrentUsers(noOfSlowerUsers).during(20.seconds.toJavaDuration())
            ),
        ).protocols(httpProtocol)
            .maxDuration(20.seconds.toJavaDuration())
    }
}
