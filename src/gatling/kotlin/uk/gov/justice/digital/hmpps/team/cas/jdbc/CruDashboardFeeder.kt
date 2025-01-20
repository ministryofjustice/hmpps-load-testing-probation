package uk.gov.justice.digital.hmpps.team.cas.jdbc

import io.gatling.javaapi.core.FeederBuilder
import io.gatling.javaapi.jdbc.JdbcDsl
import uk.gov.justice.digital.hmpps.config.DbConfig
import uk.gov.justice.digital.hmpps.team.cas.model.CasPlacementRequestSession
import java.util.UUID

class CruDashboardFeeder(
    private val dbConfig: DbConfig = DbConfig()
) {
    fun getJdbcFeeder(status: String, cruManagementAreaId: UUID): FeederBuilder<Any> {
        val feederQuery = """
            SELECT pq.${CasPlacementRequestSession.PLACEMENT_REQUEST_ID.sessionKey}
            from placement_requests pq
            left join approved_premises_applications apa on apa.id = pq.application_id
            where pq.reallocated_at IS NULL
            AND pq.is_withdrawn IS FALSE
            AND (
                CASE
                    WHEN EXISTS (
                        SELECT 1
                        from cancellations c
                        right join bookings booking on c.booking_id = booking.id
                        WHERE
                            booking.id = pq.booking_id
                        AND c.id IS NULL
                        ) THEN 'matched'
                    WHEN EXISTS (
                        SELECT 1 from booking_not_mades bnm
                        WHERE bnm.placement_request_id = pq.id
                    ) THEN 'unableToMatch'
                    WHEN EXISTS (
                        SELECT 1
                        FROM cas1_space_bookings sb
                        WHERE sb.placement_request_id = pq.id AND sb.cancellation_occurred_at IS NULL
                    ) THEN 'matched'
                    ELSE 'notMatched'
                END
            ) = '$status'
            AND apa.cas1_cru_management_area_id = '$cruManagementAreaId'
        """
        return JdbcDsl.jdbcFeeder(
            "jdbc:postgresql://localhost:${dbConfig.dbPort}/${dbConfig.dbName}",
            dbConfig.dbUsername,
            dbConfig.dbPassword,
            feederQuery
        ).queue()
    }
}