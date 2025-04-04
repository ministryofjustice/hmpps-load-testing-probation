package uk.gov.justice.digital.hmpps.team.cas.simulations.cas2v2

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.CoreDsl.constantUsersPerSec
import io.gatling.javaapi.core.CoreDsl.scenario
import jodd.util.MathUtil.randomInt
import uk.gov.justice.digital.hmpps.BaseSimulationBackEndApi
import uk.gov.justice.digital.hmpps.team.cas.service.AuthorizationService
import uk.gov.justice.digital.hmpps.team.cas.service.cas2v2.Cas2v2ApplyOrchestrationService
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class Cas2v2ApplyJourneySimulation(
    applyOrchestrationService: Cas2v2ApplyOrchestrationService = Cas2v2ApplyOrchestrationService(),
    authorizationService: AuthorizationService = AuthorizationService(),
) : BaseSimulationBackEndApi() {

    private val createApplication = applyOrchestrationService.createApplication()
        .exitHereIfFailed()
        .pause(1.seconds.toJavaDuration())

    private val updateApplication = CoreDsl.repeat({ randomInt(1, 10) }, "n").on(
        applyOrchestrationService.updateApplication()
            .pause(5.seconds.toJavaDuration()),
    )

    private val approvedPremisesApplyJourney = scenario("AP Apply journey")
        .exec(
            authorizationService.authorizeUser(),
            createApplication,
            updateApplication
        )

    init {
        setUp(
            approvedPremisesApplyJourney.injectOpen(
                constantUsersPerSec(200.0).during(5.seconds.toJavaDuration()).randomized()
            ),
        )
            .protocols(httpProtocol)
            .maxDuration(5.seconds.toJavaDuration())
    }
}