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
        pauseOnViewPremisePage: Long,
        pauseOnViewPlacementPage: Long,
        pauseOnRecordArrivalPage: Long,
        pauseAfterRecordArrival: Long,
        pauseOnEditKeyWorkerPage: Long,
        pauseAfterKeyWorkerEdited: Long,
        pauseOnRecordDeparturePage: Long,
        pauseAfterSubmitRecordDeparture: Long,
        pauseOnRecordDepartureNotesPage: Long,
        pauseAfterSubmitRecordDepartureNotes: Long,
    ): ScenarioBuilder {
        val bookingMadeChainBuilder = CoreDsl.feed(spaceBookingsFeeder.getJdbcFeederForUpcomingSpaceBookings(premiseQCode))
            .exec(HttpDsl.addCookie(httpRequestHelper.connectSidAuthCookie!!))
            .exec(
                pageOrchestrationService.hitViewPremisePageAndDoChecks()
            )
            .pause(pauseOnViewPremisePage)
            .exec(
                pageOrchestrationService.hitViewPlacementPageAndDoChecks()
            )
            .pause(pauseOnViewPlacementPage)
            .exec(
                pageOrchestrationService.hitRecordArrivalPageAndDoChecks()
            )
            .pause(pauseOnRecordArrivalPage)
            .exec(
                pageOrchestrationService.submitRecordArrivalFormAndDoChecks()
            )
            .pause(pauseAfterRecordArrival)
            .exec(
                pageOrchestrationService.hitEditKeyWorkerPageAndDoChecks()
            )
            .pause(pauseOnEditKeyWorkerPage)
            .exec(
                pageOrchestrationService.submitEditKeyWorkerFormAndDoChecks()
            )
            .pause(pauseAfterKeyWorkerEdited)
            .exec(
                pageOrchestrationService.hitRecordDeparturePageAndDoChecks()
            )
            .pause(pauseOnRecordDeparturePage)
            .exec(
                pageOrchestrationService.submitRecordDepartureFormAndDoChecks()
            )
            .pause(pauseAfterSubmitRecordDeparture)
            .exec(
                pageOrchestrationService.hitRecordDepartureNotesPageAndDoChecks()
            )
            .pause(pauseOnRecordDepartureNotesPage)
            .exec(
                pageOrchestrationService.submitRecordDepartureNotesFormAndDoChecks()
            )
            .pause(pauseAfterSubmitRecordDepartureNotes)
            .exitHereIfFailed()

        return CoreDsl.scenario(scenarioName)
            .exec(bookingMadeChainBuilder)
    }
}
