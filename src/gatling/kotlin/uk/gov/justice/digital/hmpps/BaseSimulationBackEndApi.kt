package uk.gov.justice.digital.hmpps

import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import uk.gov.justice.digital.hmpps.config.HttpRequestConfig

open class BaseSimulationBackEndApi(httpRequestConfig: HttpRequestConfig = HttpRequestConfig()) : Simulation() {
    protected val httpProtocol =
        http.baseUrl("${httpRequestConfig.protocol}://${httpRequestConfig.domain}")
            .acceptHeader("*/*")
            .contentTypeHeader("application/json")
            .authorizationHeader("Bearer #{accessToken}")

}