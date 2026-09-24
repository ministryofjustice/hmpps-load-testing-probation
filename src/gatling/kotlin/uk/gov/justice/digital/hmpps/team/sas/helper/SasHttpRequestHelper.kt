package uk.gov.justice.digital.hmpps.team.sas.helper

import io.gatling.javaapi.http.AddCookie
import io.gatling.javaapi.http.HttpDsl
import uk.gov.justice.digital.hmpps.config.AuthConfig
import uk.gov.justice.digital.hmpps.config.HttpRequestConfig

class SasHttpRequestHelper(
    authConfig: AuthConfig = AuthConfig(),
    httpRequestConfig: HttpRequestConfig = HttpRequestConfig()
) {
    var sessionCookie: AddCookie? = null

    init {
        if (authConfig.connectSidCookie != null) {
            sessionCookie =
                HttpDsl.Cookie(
                    "hmpps-single-accommodation-service-ui.session",
                    authConfig.connectSidCookie
                )
                    .withDomain(httpRequestConfig.domain)
                    .withPath("/")
                    .withSecure(true)
        }
    }
}