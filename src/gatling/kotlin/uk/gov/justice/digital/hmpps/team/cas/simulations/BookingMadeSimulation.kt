package uk.gov.justice.digital.hmpps.team.cas.simulations

import io.gatling.javaapi.core.CoreDsl.*
import uk.gov.justice.digital.hmpps.BaseSimulationFrontEndRoutes
import uk.gov.justice.digital.hmpps.team.cas.constants.*
import uk.gov.justice.digital.hmpps.team.cas.service.BookingMadeScenarioService
import kotlin.time.*
import kotlin.time.Duration.Companion.minutes


class BookingMadeSimulation(bookingMadeScenarioService: BookingMadeScenarioService = BookingMadeScenarioService()): BaseSimulationFrontEndRoutes() {
    init {
        val fastBookingMadeScenario =
            bookingMadeScenarioService.buildScenario(
                scenarioName = "Booking Made Scenario - Fast users",
                status = cruDashboardNotMatchedStatus,
                cruManagementAreaId = cruManagementAreaIdForNorthEast,
                fastBookingMadePauseOnCruDashboardPage,
                fastBookingMadePauseOnPlacementRequestPage,
                fastBookingMadePauseOnFindASpacePage,
                fastBookingMadePauseOnOccupancyViewPage,
                fastBookingMadePauseOnConfirmBookingPage,
                fastBookingMadePauseOnConfirmBookingSubmitPage
            )

        val averageSpeedBookingMadeScenario =
            bookingMadeScenarioService.buildScenario(
                scenarioName = "Booking Made Scenario - Average speed users",
                status = cruDashboardNotMatchedStatus,
                cruManagementAreaId = cruManagementAreaIdForNorthEast,
                averageSpeedBookingMadePauseOnCruDashboardPage,
                averageSpeedBookingMadePauseOnPlacementRequestPage,
                averageSpeedBookingMadePauseOnFindASpacePage,
                averageSpeedBookingMadePauseOnOccupancyViewPage,
                averageSpeedBookingMadePauseOnConfirmBookingPage,
                averageSpeedBookingMadePauseOnConfirmBookingSubmitPage
            )

        val slowerBookingMadeScenario =
            bookingMadeScenarioService.buildScenario(
                scenarioName = "Booking Made Scenario - Slower users",
                status = cruDashboardNotMatchedStatus,
                cruManagementAreaId = cruManagementAreaIdForNorthEast,
                slowerBookingMadePauseOnCruDashboardPage,
                slowerBookingMadePauseOnPlacementRequestPage,
                slowerBookingMadePauseOnFindASpacePage,
                slowerBookingMadePauseOnOccupancyViewPage,
                slowerBookingMadePauseOnConfirmBookingPage,
                slowerBookingMadePauseOnConfirmBookingSubmitPage
            )
        setUp(
            fastBookingMadeScenario.injectClosed(
                constantConcurrentUsers(noOfFastUsers).during(2.minutes.toJavaDuration())
            ),
            averageSpeedBookingMadeScenario.injectClosed(
                constantConcurrentUsers(noOfAverageSpeedUsers).during(2.minutes.toJavaDuration())
            ),
            slowerBookingMadeScenario.injectClosed(
                constantConcurrentUsers(noOfSlowerUsers).during(2.minutes.toJavaDuration())
            ),
        ).protocols(httpProtocol)
            .maxDuration(2.minutes.toJavaDuration())
    }
}
