package uk.gov.justice.digital.hmpps.team.maw.jdbc

import io.gatling.javaapi.core.FeederBuilder
import io.gatling.javaapi.jdbc.JdbcDsl
import uk.gov.justice.digital.hmpps.config.DbConfig
import uk.gov.justice.digital.hmpps.team.maw.model.CaseDetailsInSession

class UnallocatedCaseFeeder(
    private val dbConfig: DbConfig = DbConfig()
) {
    fun getJdbcFeeder(nominatedTeamCode: String): FeederBuilder<Any> {
        val feederQuery = """
            SELECT  ${CaseDetailsInSession.NAME.sessionKey}, 
                    ${CaseDetailsInSession.CRN.sessionKey}, 
                    ${CaseDetailsInSession.TIER.sessionKey}, 
                    ${CaseDetailsInSession.CONVICTION_NUMBER.sessionKey}
            FROM unallocated_cases
            where team_code='$nominatedTeamCode'
          """
        return JdbcDsl.jdbcFeeder(
            "jdbc:postgresql://localhost:5432/${dbConfig.dbName}",
            dbConfig.dbUsername,
            dbConfig.dbPassword,
            feederQuery
        ).random()
    }
}
