package uk.gov.justice.digital.hmpps.team.cas.simulations

import io.gatling.javaapi.core.CoreDsl.constantUsersPerSec
import io.gatling.javaapi.core.CoreDsl.repeat
import io.gatling.javaapi.core.CoreDsl.scenario
import jodd.util.MathUtil.randomInt
import uk.gov.justice.digital.hmpps.BaseSimulationBackEndApi
import uk.gov.justice.digital.hmpps.team.cas.service.ApplyOrchestrationService
import uk.gov.justice.digital.hmpps.team.cas.service.AuthorizationService
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

class ApplyJourneySimulation(
  applyOrchestrationService: ApplyOrchestrationService = ApplyOrchestrationService(),
  authorizationService: AuthorizationService = AuthorizationService(),
) : BaseSimulationBackEndApi() {

  private val createApplication = applyOrchestrationService.createApplication()
    .exitHereIfFailed()
    .pause(1.seconds.toJavaDuration())

  private val updateApplication = repeat({ randomInt(1, 20) }, "n").on(
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
        constantUsersPerSec(2.0).during(5.seconds.toJavaDuration()).randomized()
      ),
    )
      .protocols(httpProtocol)
      .maxDuration(5.seconds.toJavaDuration())
  }
}
