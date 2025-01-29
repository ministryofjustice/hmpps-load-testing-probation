package uk.gov.justice.digital.hmpps.team.cas.simulations

import io.gatling.javaapi.core.CoreDsl.*
import uk.gov.justice.digital.hmpps.BaseSimulationFrontEndRoutes
import uk.gov.justice.digital.hmpps.team.cas.constants.*
import uk.gov.justice.digital.hmpps.team.cas.service.BookingManagementScenarioService
import kotlin.time.*
import kotlin.time.Duration.Companion.minutes

class BookingManagementSimulation(bookingMadeScenarioService: BookingManagementScenarioService = BookingManagementScenarioService()): BaseSimulationFrontEndRoutes() {
    init {
        val slowBookingManagementScenario = bookingMadeScenarioService.buildBookingManagementScenario(
            scenarioName = "Booking Management Scenario - Slow users",
            premiseQCode = swscMenPremise1QCode,
            pauseOnViewPremisePage = slowBookingManagementPauseOnViewPremisePage,
            pauseOnViewPlacementPage = slowBookingManagementPauseOnViewPlacementPage,
            pauseOnRecordArrivalPage = slowBookingManagementPauseOnRecordArrivalPage,
            pauseAfterRecordArrival = slowBookingManagementPauseAfterRecordArrival,
            pauseOnEditKeyWorkerPage = slowBookingManagementPauseOnEditKeyWorkerPage,
            pauseAfterKeyWorkerEdited = slowBookingManagementPauseAfterKeyWorkerEdited,
            pauseOnRecordDeparturePage = slowBookingManagementPauseOnRecordDeparturePage,
            pauseAfterSubmitRecordDeparture = slowBookingManagementPauseAfterSubmitRecordDeparture,
            pauseOnRecordDepartureNotesPage = slowBookingManagementPauseOnRecordDepartureNotesPage,
            pauseAfterSubmitRecordDepartureNotes = slowBookingManagementPauseAfterSubmitRecordDepartureNotes
        )
        val averageSpeedBookingManagementScenario = bookingMadeScenarioService.buildBookingManagementScenario(
            scenarioName = "Booking Management Scenario - Average speed users",
            premiseQCode = swscMenPremise1QCode,
            pauseOnViewPremisePage = averageSpeedBookingManagementPauseOnViewPremisePage,
            pauseOnViewPlacementPage = averageSpeedBookingManagementPauseOnViewPlacementPage,
            pauseOnRecordArrivalPage = averageSpeedBookingManagementPauseOnRecordArrivalPage,
            pauseAfterRecordArrival = averageSpeedBookingManagementPauseAfterRecordArrival,
            pauseOnEditKeyWorkerPage = averageSpeedBookingManagementPauseOnEditKeyWorkerPage,
            pauseAfterKeyWorkerEdited = averageSpeedBookingManagementPauseAfterKeyWorkerEdited,
            pauseOnRecordDeparturePage = averageSpeedBookingManagementPauseOnRecordDeparturePage,
            pauseAfterSubmitRecordDeparture = averageSpeedBookingManagementPauseAfterSubmitRecordDeparture,
            pauseOnRecordDepartureNotesPage = averageSpeedBookingManagementPauseOnRecordDepartureNotesPage,
            pauseAfterSubmitRecordDepartureNotes = averageSpeedBookingManagementPauseAfterSubmitRecordDepartureNotes
        )
        val fastBookingManagementScenario = bookingMadeScenarioService.buildBookingManagementScenario(
            scenarioName = "Booking Management Scenario - Fast users",
            premiseQCode = swscMenPremise1QCode,
            pauseOnViewPremisePage = fastBookingManagementPauseOnViewPremisePage,
            pauseOnViewPlacementPage = fastBookingManagementPauseOnViewPlacementPage,
            pauseOnRecordArrivalPage = fastBookingManagementPauseOnRecordArrivalPage,
            pauseAfterRecordArrival = fastBookingManagementPauseAfterRecordArrival,
            pauseOnEditKeyWorkerPage = fastBookingManagementPauseOnEditKeyWorkerPage,
            pauseAfterKeyWorkerEdited = fastBookingManagementPauseAfterKeyWorkerEdited,
            pauseOnRecordDeparturePage = fastBookingManagementPauseOnRecordDeparturePage,
            pauseAfterSubmitRecordDeparture = fastBookingManagementPauseAfterSubmitRecordDeparture,
            pauseOnRecordDepartureNotesPage = fastBookingManagementPauseOnRecordDepartureNotesPage,
            pauseAfterSubmitRecordDepartureNotes = fastBookingManagementPauseAfterSubmitRecordDepartureNotes
        )

        setUp(
            slowBookingManagementScenario.injectClosed(
                constantConcurrentUsers(noOfBookingManagementSlowUsers).during(5.minutes.toJavaDuration())
            ),
            averageSpeedBookingManagementScenario.injectClosed(
                constantConcurrentUsers(noOfBookingManagementAverageSpeedUsers).during(5.minutes.toJavaDuration())
            ),
            fastBookingManagementScenario.injectClosed(
                constantConcurrentUsers(noOfBookingManagementFastUsers).during(5.minutes.toJavaDuration())
            )
        ).protocols(httpProtocol)
            .maxDuration(5.minutes.toJavaDuration())
    }
}
