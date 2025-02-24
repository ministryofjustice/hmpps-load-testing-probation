package uk.gov.justice.digital.hmpps.team.cas.simulations

import io.gatling.javaapi.core.CoreDsl.*
import uk.gov.justice.digital.hmpps.BaseSimulationFrontEndRoutes
import uk.gov.justice.digital.hmpps.team.cas.constants.*
import uk.gov.justice.digital.hmpps.team.cas.service.PremiseManagementScenarioService
import kotlin.time.*
import kotlin.time.Duration.Companion.minutes

class PremiseManagementSimulation(premiseManagementScenarioService: PremiseManagementScenarioService = PremiseManagementScenarioService()): BaseSimulationFrontEndRoutes() {
    init {
        val premiseManagementScenario = premiseManagementScenarioService.buildScenario(
            scenarioName = "Premise Management Scenario",
            premiseManagementPauseBeforeStart,
            premiseManagementPauseOnViewPremisePage,
            premiseManagementPauseOnViewSpacesInPremisePage,
            premiseManagementPauseOnOccupancyViewForDayPage
        )
        setUp(
            premiseManagementScenario.injectClosed(
                constantConcurrentUsers(noOfPremiseManagementUsers).during(2.minutes.toJavaDuration())
            )
        ).protocols(httpProtocol)
            .maxDuration(2.minutes.toJavaDuration())
    }
}
