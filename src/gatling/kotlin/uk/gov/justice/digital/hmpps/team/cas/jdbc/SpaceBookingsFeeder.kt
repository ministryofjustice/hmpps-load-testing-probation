package uk.gov.justice.digital.hmpps.team.cas.jdbc

import io.gatling.javaapi.core.FeederBuilder
import io.gatling.javaapi.jdbc.JdbcDsl
import uk.gov.justice.digital.hmpps.config.DbConfig
import uk.gov.justice.digital.hmpps.team.cas.model.BookingManagementSimulationSession

class SpaceBookingsFeeder(
    private val dbConfig: DbConfig = DbConfig()
) {
    fun getJdbcFeederForUpcomingSpaceBookings(premiseQCode: String): FeederBuilder<Any> {
        val feederQuery = """
          SELECT Cast(b.id as varchar) AS ${BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey},
          Cast(a.premises_id as varchar) AS ${BookingManagementSimulationSession.PREMISE_ID.sessionKey}
          FROM cas1_space_bookings b
          LEFT JOIN approved_premises a ON b.premises_id = a.premises_id
          WHERE a.q_code = '$premiseQCode' 
          AND b.cancellation_occurred_at IS NULL 
          AND (
            b.actual_arrival_date IS NULL AND 
            b.non_arrival_confirmed_at IS NULL AND
            b.expected_departure_date >= '2024-06-01'
          )
        """
        return JdbcDsl.jdbcFeeder(
            "jdbc:postgresql://localhost:${dbConfig.dbPort}/${dbConfig.dbName}",
            dbConfig.dbUsername,
            dbConfig.dbPassword,
            feederQuery
        ).queue()
    }
}