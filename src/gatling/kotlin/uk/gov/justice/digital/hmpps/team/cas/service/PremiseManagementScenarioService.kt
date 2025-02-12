package uk.gov.justice.digital.hmpps.team.cas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.http.HttpDsl
import uk.gov.justice.digital.hmpps.helper.HttpRequestHelper
import uk.gov.justice.digital.hmpps.team.cas.jdbc.PremisesFeeder

class PremiseManagementScenarioService(
    private val premisesFeeder: PremisesFeeder = PremisesFeeder(),
    private val httpRequestHelper: HttpRequestHelper = HttpRequestHelper(),
    private val pageOrchestrationService: PremiseManagementPageOrchestrationService = PremiseManagementPageOrchestrationService()
) {

    fun buildScenario(
        scenarioName: String,
        pauseBeforeStart: Pair<Long, Long>,
        pauseOnViewPremisePage: Pair<Long, Long>,
        pauseOnViewSpacesInPremisePage: Pair<Long, Long>,
        pauseOnOccupancyViewForDayPage: Pair<Long, Long>,
    ): ScenarioBuilder {
        val premiseManagementChainBuilder = CoreDsl.feed(premisesFeeder.getJdbcFeederForSouthWestSouthCentralPremises())
            .exec(HttpDsl.addCookie(httpRequestHelper.connectSidAuthCookie!!))
            .pause(pauseBeforeStart.first, pauseBeforeStart.second)
            .exec(
                pageOrchestrationService.hitViewPremisePageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnViewPremisePage.first, pauseOnViewPremisePage.second)
            .exec(
                pageOrchestrationService.hitViewSpacesInPremisePageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnViewSpacesInPremisePage.first, pauseOnViewSpacesInPremisePage.second)
            .exec(
                pageOrchestrationService.hitOccupancyViewForDayPageAndDoChecks()
            )
            .exitHereIfFailed()
            .pause(pauseOnOccupancyViewForDayPage.first, pauseOnOccupancyViewForDayPage.second)

        return CoreDsl.scenario(scenarioName)
            .exec(premiseManagementChainBuilder)
    }
}
