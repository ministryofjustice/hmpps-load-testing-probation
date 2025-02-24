package uk.gov.justice.digital.hmpps.team.cas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.http.HttpDsl
import uk.gov.justice.digital.hmpps.helper.HttpRequestHelper
import uk.gov.justice.digital.hmpps.team.cas.jdbc.SpaceBookingsFeeder

class BookingManagementScenarioService(
    private val spaceBookingsFeeder: SpaceBookingsFeeder = SpaceBookingsFeeder(),
    private val httpRequestHelper: HttpRequestHelper = HttpRequestHelper(),
    private val pageOrchestrationService: BookingManagementPageOrchestrationService = BookingManagementPageOrchestrationService()
) {

    fun buildScenario(
        scenarioName: String,
        premiseQCode: String,
        pauseBeforeStart: Pair<Long, Long>,
        pauseOnViewPremisePage: Pair<Long, Long>,
        pauseOnViewPlacementPage: Pair<Long, Long>,
        pauseOnRecordArrivalPage: Pair<Long, Long>,
        pauseAfterRecordArrival: Pair<Long, Long>,
        pauseOnEditKeyWorkerPage: Pair<Long, Long>,
        pauseAfterKeyWorkerEdited: Pair<Long, Long>,
        pauseOnRecordDeparturePage: Pair<Long, Long>,
        pauseAfterSubmitRecordDeparture: Pair<Long, Long>,
        pauseOnRecordDepartureNotesPage: Pair<Long, Long>,
        pauseAfterSubmitRecordDepartureNotes: Pair<Long, Long>,
    ): ScenarioBuilder {
        val bookingMadeChainBuilder = CoreDsl.feed(spaceBookingsFeeder.getJdbcFeederForUpcomingSpaceBookings(premiseQCode))
            .exec(HttpDsl.addCookie(httpRequestHelper.connectSidAuthCookie!!))
            .pause(pauseBeforeStart.first, pauseBeforeStart.second)
            .exec(
                pageOrchestrationService.hitViewPremisePageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnViewPremisePage.first, pauseOnViewPremisePage.second)
            .exec(
                pageOrchestrationService.hitViewPlacementPageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnViewPlacementPage.first, pauseOnViewPlacementPage.second)
            .exec(
                pageOrchestrationService.hitRecordArrivalPageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnRecordArrivalPage.first, pauseOnRecordArrivalPage.second)
            .exec(
                pageOrchestrationService.submitRecordArrivalFormAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseAfterRecordArrival.first, pauseAfterRecordArrival.second)
            .exec(
                pageOrchestrationService.hitEditKeyWorkerPageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnEditKeyWorkerPage.first, pauseOnEditKeyWorkerPage.second)
            .exec(
                pageOrchestrationService.submitEditKeyWorkerFormAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseAfterKeyWorkerEdited.first, pauseAfterKeyWorkerEdited.second)
            .exec(
                pageOrchestrationService.hitRecordDeparturePageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnRecordDeparturePage.first, pauseOnRecordDeparturePage.second)
            .exec(
                pageOrchestrationService.submitRecordDepartureFormAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseAfterSubmitRecordDeparture.first, pauseAfterSubmitRecordDeparture.second)
            .exec(
                pageOrchestrationService.hitRecordDepartureNotesPageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnRecordDepartureNotesPage.first, pauseOnRecordDepartureNotesPage.second)
            .exec(
                pageOrchestrationService.submitRecordDepartureNotesFormAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseAfterSubmitRecordDepartureNotes.first, pauseAfterSubmitRecordDepartureNotes.second)

        return CoreDsl.scenario(scenarioName)
            .exec(bookingMadeChainBuilder)
    }
}
