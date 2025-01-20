package uk.gov.justice.digital.hmpps.config

data class AuthConfig(
    val authBaseUrl: String? = System.getProperty("authBaseUrl") ?: null,
    val connectSidCookie: String? = System.getProperty("connectSidCookieValue") ?: null
)
