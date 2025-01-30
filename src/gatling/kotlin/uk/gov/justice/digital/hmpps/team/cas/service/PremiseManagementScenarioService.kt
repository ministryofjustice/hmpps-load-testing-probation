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
        pauseOnViewPremisePage: Long,
        pauseOnViewSpacesInPremisePage: Long,
        pauseOnOccupancyViewForDayPage: Long
    ): ScenarioBuilder {
        val premiseManagementChainBuilder = CoreDsl.feed(premisesFeeder.getJdbcFeederForSouthWestSouthCentralPremises())
            .exec(HttpDsl.addCookie(httpRequestHelper.connectSidAuthCookie!!))
            .exec(
                pageOrchestrationService.hitViewPremisePageAndDoChecks()
            )
            .pause(pauseOnViewPremisePage)
            .exec(
                pageOrchestrationService.hitViewSpacesInPremisePageAndDoChecks()
            )
            .pause(pauseOnViewSpacesInPremisePage)
            .exec(
                pageOrchestrationService.hitOccupancyViewForDayPageAndDoChecks()
            )
            .pause(pauseOnOccupancyViewForDayPage)
            .exitHereIfFailed()

        return CoreDsl.scenario(scenarioName)
            .exec(premiseManagementChainBuilder)
    }
}
