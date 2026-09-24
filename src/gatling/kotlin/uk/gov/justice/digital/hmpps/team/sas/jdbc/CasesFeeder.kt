package uk.gov.justice.digital.hmpps.team.sas.jdbc

import io.gatling.javaapi.core.FeederBuilder
import io.gatling.javaapi.jdbc.JdbcDsl
import uk.gov.justice.digital.hmpps.config.DbConfig
import uk.gov.justice.digital.hmpps.team.sas.model.CaseListSimulationSession

class CasesFeeder(
    private val dbConfig: DbConfig = DbConfig()
) {
    fun getJdbcFeederForSasCases(): FeederBuilder<Any> {
        val feederQuery = """
            SELECT DISTINCT
                sc.id AS ${CaseListSimulationSession.CASE_ID.sessionKey},
                sci.identifier AS ${CaseListSimulationSession.CRN.sessionKey}
            FROM sas_case sc
            JOIN sas_case_identifier sci ON sc.id = sci.case_id
            WHERE sci.identifier IN ('Y022654')
            AND sci.identifier_type = 'CRN'
        """

        return JdbcDsl.jdbcFeeder(
            "jdbc:postgresql://localhost:${dbConfig.dbPort}/${dbConfig.dbName}",
            dbConfig.dbUsername,
            dbConfig.dbPassword,
            feederQuery
        ).random()
    }
}