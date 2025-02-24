package uk.gov.justice.digital.hmpps.team.cas.simulations

import io.gatling.javaapi.core.CoreDsl.*
import uk.gov.justice.digital.hmpps.BaseSimulationFrontEndRoutes
import uk.gov.justice.digital.hmpps.team.cas.constants.*
import uk.gov.justice.digital.hmpps.team.cas.service.BookingManagementScenarioService
import kotlin.time.*
import kotlin.time.Duration.Companion.minutes

class BookingManagementSimulation(bookingManagementScenarioService: BookingManagementScenarioService = BookingManagementScenarioService()): BaseSimulationFrontEndRoutes() {
    init {
        val bookingManagementScenario = bookingManagementScenarioService.buildScenario(
            scenarioName = "Booking Management Scenario",
            premiseQCode = swscMenPremise1QCode,
            pauseBeforeStart,
            pauseOnViewPremisePage,
            pauseOnViewPlacementPage,
            pauseOnRecordArrivalPage,
            pauseAfterRecordArrival,
            pauseOnEditKeyWorkerPage,
            pauseAfterKeyWorkerEdited,
            pauseOnRecordDeparturePage,
            pauseAfterSubmitRecordDeparture,
            pauseOnRecordDepartureNotesPage,
            pauseAfterSubmitRecordDepartureNotes
        )

        setUp(
            bookingManagementScenario.injectClosed(
                constantConcurrentUsers(noOfBookingManagementUsers).during(2.minutes.toJavaDuration())
            ),
        ).protocols(httpProtocol)
            .maxDuration(2.minutes.toJavaDuration())
    }
}
