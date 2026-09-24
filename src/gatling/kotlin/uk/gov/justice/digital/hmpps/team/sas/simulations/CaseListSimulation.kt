package uk.gov.justice.digital.hmpps.team.sas.simulations

import io.gatling.javaapi.core.CoreDsl.atOnceUsers
import uk.gov.justice.digital.hmpps.BaseSimulationFrontEndRoutes
import uk.gov.justice.digital.hmpps.team.sas.service.CaseListScenarioService

class CaseListSimulation(
    caseListScenarioService: CaseListScenarioService = CaseListScenarioService()
) : BaseSimulationFrontEndRoutes() {

    init {
        val caseListScenario = caseListScenarioService.buildScenario(
            scenarioName = "SAS Case List Scenario"
        )

        setUp(
            caseListScenario.injectOpen(
                atOnceUsers(1)
            )
        ).protocols(httpProtocol)
    }
}