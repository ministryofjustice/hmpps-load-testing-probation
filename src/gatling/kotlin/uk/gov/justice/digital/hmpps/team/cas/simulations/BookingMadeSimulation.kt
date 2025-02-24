package uk.gov.justice.digital.hmpps.team.cas.simulations

import io.gatling.javaapi.core.CoreDsl.*
import uk.gov.justice.digital.hmpps.BaseSimulationFrontEndRoutes
import uk.gov.justice.digital.hmpps.team.cas.constants.*
import uk.gov.justice.digital.hmpps.team.cas.service.BookingMadeScenarioService
import kotlin.time.*
import kotlin.time.Duration.Companion.minutes


class BookingMadeSimulation(bookingMadeScenarioService: BookingMadeScenarioService = BookingMadeScenarioService()): BaseSimulationFrontEndRoutes() {
    init {
        val bookingMadeScenario =
            bookingMadeScenarioService.buildScenario(
                scenarioName = "Booking Made Scenario - Fast users",
                status = cruDashboardNotMatchedStatus,
                cruManagementAreaId = cruManagementAreaIdForNorthEast,
                bookingMadePauseBeforeStart,
                bookingMadePauseOnCruDashboardPage,
                bookingMadePauseOnPlacementRequestPage,
                bookingMadePauseOnFindASpacePage,
                bookingMadePauseOnOccupancyViewPage,
                bookingMadePauseOnConfirmBookingPage,
                bookingMadePauseOnConfirmBookingSubmitPage
            )
        setUp(
            bookingMadeScenario.injectClosed(
                constantConcurrentUsers(noOfBookingMadeUsers).during(2.minutes.toJavaDuration())
            )
        ).protocols(httpProtocol)
            .maxDuration(2.minutes.toJavaDuration())
    }
}
