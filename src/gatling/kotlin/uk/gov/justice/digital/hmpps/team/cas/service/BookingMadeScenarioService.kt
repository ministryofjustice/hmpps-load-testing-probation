package uk.gov.justice.digital.hmpps.team.cas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.http.HttpDsl
import uk.gov.justice.digital.hmpps.helper.HttpRequestHelper
import uk.gov.justice.digital.hmpps.team.cas.jdbc.CruDashboardFeeder
import java.util.UUID

class BookingMadeScenarioService(
    private val cruDashboardFeeder: CruDashboardFeeder = CruDashboardFeeder(),
    private val httpRequestHelper: HttpRequestHelper = HttpRequestHelper(),
    private val pageOrchestrationService: BookingMadePageOrchestrationService = BookingMadePageOrchestrationService()
) {

    fun buildScenario(
        scenarioName: String,
        status: String,
        cruManagementAreaId: UUID,
        pauseBeforeStart: Pair<Long, Long>,
        pauseOnCruDashboardPage: Pair<Long, Long>,
        pauseOnPlacementRequestPage: Pair<Long, Long>,
        pauseOnFindASpacePage: Pair<Long, Long>,
        pauseOnOccupancyViewPage: Pair<Long, Long>,
        pauseOnConfirmBookingPage: Pair<Long, Long>,
        pauseOnConfirmBookingSubmitPage: Pair<Long, Long>
    ): ScenarioBuilder {
        val bookingMadeChainBuilder = CoreDsl.feed(cruDashboardFeeder.getJdbcFeeder(status, cruManagementAreaId))
            .exec(HttpDsl.addCookie(httpRequestHelper.connectSidAuthCookie!!))
            .pause(pauseBeforeStart.first, pauseBeforeStart.second)
            .exec(
                pageOrchestrationService.hitCruDashboardPageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnCruDashboardPage.first, pauseOnCruDashboardPage.second)
            .exec(
                pageOrchestrationService.hitPlacementRequestPageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnPlacementRequestPage.first, pauseOnPlacementRequestPage.second)
            .exec(
                pageOrchestrationService.hitFindASpacePageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnFindASpacePage.first, pauseOnFindASpacePage.second)
            .exec(
                pageOrchestrationService.hitOccupancyViewPageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnOccupancyViewPage.first, pauseOnOccupancyViewPage.second)
            .exec(
                pageOrchestrationService.hitConfirmBookingPageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnConfirmBookingPage.first, pauseOnConfirmBookingPage.second)
            .exec(
                pageOrchestrationService.submitConfirmBookingFormAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnConfirmBookingSubmitPage.first, pauseOnConfirmBookingSubmitPage.second)

        return CoreDsl.scenario(scenarioName)
            .exec(bookingMadeChainBuilder)
    }
}
