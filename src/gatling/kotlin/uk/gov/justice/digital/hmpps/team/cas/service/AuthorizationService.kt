package uk.gov.justice.digital.hmpps.team.cas.service

import io.gatling.javaapi.core.ChainBuilder
import io.gatling.javaapi.core.CoreDsl.exec


class AuthorizationService() {
    fun authorizeUser(): ChainBuilder {
        val jwt = System.getProperty("jwt")
        return exec { session ->
            session.set("accessToken", jwt)
        }
    }
}