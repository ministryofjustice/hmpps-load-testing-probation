package uk.gov.justice.digital.hmpps.team.sas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.http.HttpDsl

class CaseListPageOrchestrationService {

    fun hitCaseListPageAndDoChecks() =
        HttpDsl.http("View SAS Case List Page")
            .get("/")
            .check(
                HttpDsl.status().`is`(200),
                CoreDsl.css(".govuk-heading-xl:contains('Accommodation case list')").exists()
            )
}