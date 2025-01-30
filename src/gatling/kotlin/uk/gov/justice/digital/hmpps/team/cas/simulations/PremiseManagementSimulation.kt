package uk.gov.justice.digital.hmpps.team.cas.simulations

import io.gatling.javaapi.core.CoreDsl.*
import uk.gov.justice.digital.hmpps.BaseSimulationFrontEndRoutes
import uk.gov.justice.digital.hmpps.team.cas.constants.*
import uk.gov.justice.digital.hmpps.team.cas.service.PremiseManagementScenarioService
import kotlin.time.*
import kotlin.time.Duration.Companion.minutes

class PremiseManagementSimulation(premiseManagementScenarioService: PremiseManagementScenarioService = PremiseManagementScenarioService()): BaseSimulationFrontEndRoutes() {
    init {
        val fastPremiseManagementScenario = premiseManagementScenarioService.buildScenario(
            scenarioName = "Premise Management Scenario - Fast users",
            pauseOnViewPremisePage = fastPremiseManagementPauseOnViewPremisePage,
            pauseOnViewSpacesInPremisePage = fastPremiseManagementPauseOnViewSpacesInPremisePage,
            pauseOnOccupancyViewForDayPage = fastPremiseManagementPauseOnOccupancyViewForDayPage
        )
        val averageSpeedPremiseManagementScenario = premiseManagementScenarioService.buildScenario(
            scenarioName = "Premise Management Scenario - Average speed users",
            pauseOnViewPremisePage = averageSpeedPremiseManagementPauseOnViewPremisePage,
            pauseOnViewSpacesInPremisePage = averageSpeedPremiseManagementPauseOnViewSpacesInPremisePage,
            pauseOnOccupancyViewForDayPage = averageSpeedPremiseManagementPauseOnOccupancyViewForDayPage
        )
        val slowPremiseManagementScenario = premiseManagementScenarioService.buildScenario(
            scenarioName = "Premise Management Scenario - Slower users",
            pauseOnViewPremisePage = slowerPremiseManagementPauseOnViewPremisePage,
            pauseOnViewSpacesInPremisePage = slowerPremiseManagementPauseOnViewSpacesInPremisePage,
            pauseOnOccupancyViewForDayPage = slowerPremiseManagementPauseOnOccupancyViewForDayPage
        )
        setUp(
            fastPremiseManagementScenario.injectClosed(
                constantConcurrentUsers(noOfPremiseManagementFastUsers).during(2.minutes.toJavaDuration())
            ),
            averageSpeedPremiseManagementScenario.injectClosed(
                constantConcurrentUsers(noOfPremiseManagementAverageSpeedUsers).during(2.minutes.toJavaDuration())
            ),
            slowPremiseManagementScenario.injectClosed(
                constantConcurrentUsers(noOfPremiseManagementSlowerUsers).during(2.minutes.toJavaDuration())
            ),
        ).protocols(httpProtocol)
            .maxDuration(2.minutes.toJavaDuration())
    }
}
