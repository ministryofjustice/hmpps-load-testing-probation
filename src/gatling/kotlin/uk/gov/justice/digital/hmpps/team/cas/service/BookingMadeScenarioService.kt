package uk.gov.justice.digital.hmpps.team.cas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.http.HttpDsl
import uk.gov.justice.digital.hmpps.helper.HttpRequestHelper
import uk.gov.justice.digital.hmpps.team.cas.jdbc.CruDashboardFeeder
import java.util.*

class BookingMadeScenarioService(
    private val cruDashboardFeeder: CruDashboardFeeder = CruDashboardFeeder(),
    private val httpRequestHelper: HttpRequestHelper = HttpRequestHelper(),
    private val pageOrchestrationService: BookingMadePageOrchestrationService = BookingMadePageOrchestrationService()
) {

    fun buildBookingMadeScenario(
        scenarioName: String,
        status: String,
        cruManagementAreaId: UUID,
        pauseOnCruDashboardPage: Long,
        pauseOnPlacementRequestPage: Long,
        pauseOnFindASpacePage: Long,
        pauseOnOccupancyViewPage: Long,
        pauseOnConfirmBookingPage: Long,
        pauseOnConfirmBookingSubmitPage: Long
    ): ScenarioBuilder {
        val bookingMadeChainBuilder = CoreDsl.feed(cruDashboardFeeder.getJdbcFeeder(status, cruManagementAreaId))
            .exec(HttpDsl.addCookie(httpRequestHelper.connectSidAuthCookie!!))
            .exec(
                pageOrchestrationService.hitCruDashboardPageAndDoChecks()
            )
            .pause(pauseOnCruDashboardPage)
            .exec(
                pageOrchestrationService.hitPlacementRequestPageAndDoChecks()
            )
            .pause(pauseOnPlacementRequestPage)
            .exec(
                pageOrchestrationService.hitFindASpacePageAndDoChecks()
            )
            .pause(pauseOnFindASpacePage)
            .exec(
                pageOrchestrationService.hitOccupancyViewPageAndDoChecks()
            )
            .pause(pauseOnOccupancyViewPage)
            .exec(
                pageOrchestrationService.hitConfirmBookingPageAndDoChecks()
            )
            .pause(pauseOnConfirmBookingPage)
            .exec(
                pageOrchestrationService.submitConfirmBookingFormAndDoChecks()
            )
            .pause(pauseOnConfirmBookingSubmitPage)
            .exitHereIfFailed()

        return CoreDsl.scenario(scenarioName)
            .exec(bookingMadeChainBuilder)
    }
}
