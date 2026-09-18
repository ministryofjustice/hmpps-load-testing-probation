package uk.gov.justice.digital.hmpps.team.sas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.ScenarioBuilder
import io.gatling.javaapi.http.HttpDsl
import uk.gov.justice.digital.hmpps.team.sas.helper.SasHttpRequestHelper
import uk.gov.justice.digital.hmpps.team.sas.jdbc.CasesFeeder

class CaseListScenarioService(
    private val casesFeeder: CasesFeeder = CasesFeeder(),
    private val httpRequestHelper: SasHttpRequestHelper = SasHttpRequestHelper(),
    private val pageOrchestrationService: CaseListPageOrchestrationService =
        CaseListPageOrchestrationService()
) {

    fun buildScenario(
        scenarioName: String
    ): ScenarioBuilder {
        val caseListChainBuilder =
            CoreDsl.feed(casesFeeder.getJdbcFeederForSasCases())
                .exec(HttpDsl.addCookie(httpRequestHelper.sessionCookie!!))
                .exec(
                    pageOrchestrationService.hitCaseListPageAndDoChecks()
                )
                .exitHereIfFailed()

        return CoreDsl.scenario(scenarioName)
            .exec(caseListChainBuilder)
    }
}