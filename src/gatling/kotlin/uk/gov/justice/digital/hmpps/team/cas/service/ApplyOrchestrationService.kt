package uk.gov.justice.digital.hmpps.team.cas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.core.CoreDsl.StringBody
import io.gatling.javaapi.core.CoreDsl.jsonPath
import io.gatling.javaapi.http.HttpDsl

class ApplyOrchestrationService(
) {

    private val sessionKeyForApplicationId = "application_id"

    private val createApplicationPayload = """
        {
          "crn": "X371199",
          "convictionId": 1502724704,
          "deliusEventNumber": "7",
          "offenceId": "M1502750438"
        }
    """

    private val updateApplicationPayload = """
        {
           "type":"CAS1",
           "data":{
              "basic-information":{
                 "is-exceptional-case":{
                    "isExceptionalCase":"yes"
                 }
              }
           }
        }
    """

    fun createApplication() = CoreDsl.exec(
        HttpDsl.http("Create Application")
            .post("/applications")
            .header("X-Service-Name", "approved-premise")
            .body(StringBody(createApplicationPayload))
            .check(
                HttpDsl.status().`is`(201),
                jsonPath("$.id").saveAs(sessionKeyForApplicationId)
            )
    )

    fun updateApplication(
    ) = CoreDsl.exec(
        HttpDsl.http("Update Application")
            .put { session ->
                val applicationId = session.getString(sessionKeyForApplicationId)
                "/applications/$applicationId"
            }
            .header("X-Service-Name", "approved-premise")
            .body(StringBody(updateApplicationPayload)),
    )
}
