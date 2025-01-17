package uk.gov.justice.digital.hmpps.team.maw.simulations

import io.gatling.javaapi.core.CoreDsl.*
import uk.gov.justice.digital.hmpps.team.maw.constants.noOfBasicCaseUsers
import uk.gov.justice.digital.hmpps.team.maw.constants.noOfComplexCaseUsers
import uk.gov.justice.digital.hmpps.team.maw.constants.noOfNormalCaseUsers
import uk.gov.justice.digital.hmpps.team.maw.service.*
import kotlin.time.*
import kotlin.time.Duration.Companion.minutes

class AllocateCaseSimulation(allocateCaseScenarioService: AllocateCaseScenarioService = AllocateCaseScenarioService()): MawBaseSimulation() {
    init {
        val (
            basicCasesAllocationScenario,
            normalCasesAllocationScenario,
            complexCasesAllocationScenario
        ) = allocateCaseScenarioService.buildCaseAllocationScenarios(
            pduCode = nominatedPduCodeOne,
            pduName = nominatedPduNameOne,
            teamCode = nominatedTeamCodeOne,
            teamName = nominatedTeamNameOne
        )

        setUp(
            basicCasesAllocationScenario.injectClosed(
                constantConcurrentUsers(noOfBasicCaseUsers).during(2.minutes.toJavaDuration())
            ),
            normalCasesAllocationScenario.injectClosed(
                constantConcurrentUsers(noOfNormalCaseUsers).during(2.minutes.toJavaDuration())
            ),
            complexCasesAllocationScenario.injectClosed(
                constantConcurrentUsers(noOfComplexCaseUsers).during(2.minutes.toJavaDuration())
            )
        )
            .protocols(httpProtocol)
            .maxDuration(2.minutes.toJavaDuration())
    }
}
