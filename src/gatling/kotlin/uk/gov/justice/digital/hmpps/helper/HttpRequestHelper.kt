package uk.gov.justice.digital.hmpps.helper

import io.gatling.javaapi.http.AddCookie
import io.gatling.javaapi.http.HttpDsl
import uk.gov.justice.digital.hmpps.config.AuthConfig
import uk.gov.justice.digital.hmpps.config.HttpRequestConfig

class HttpRequestHelper(
    authConfig: AuthConfig = AuthConfig(),
    httpRequestConfig: HttpRequestConfig = HttpRequestConfig()
) {
    var connectSidAuthCookie: AddCookie? = null
    init {
        if (authConfig.connectSidCookie != null) {
            connectSidAuthCookie = HttpDsl.Cookie("connect.sid", authConfig.connectSidCookie)
                .withDomain(httpRequestConfig.domain)
                .withPath("/")
                .withSecure(true)
        }
    }

}
