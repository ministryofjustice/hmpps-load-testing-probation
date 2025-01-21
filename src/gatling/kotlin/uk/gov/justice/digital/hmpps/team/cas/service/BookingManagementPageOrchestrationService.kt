package uk.gov.justice.digital.hmpps.team.cas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.http.HttpDsl
import io.gatling.javaapi.http.HttpRequestActionBuilder
import uk.gov.justice.digital.hmpps.team.cas.constants.robinTorphyStaffCode
import uk.gov.justice.digital.hmpps.team.cas.helper.CasSelectorHelper
import uk.gov.justice.digital.hmpps.team.cas.model.BookingManagementSimulationSession

class BookingManagementPageOrchestrationService(
    private val casSelectorHelper: CasSelectorHelper = CasSelectorHelper()
) {
    private val arrivalDay = 1
    private val arrivalMonth = "01"
    private val arrivalYear = "2024"

    fun hitViewPremisePageAndDoChecks() =
        HttpDsl.http("View Premise Page")
            .get { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                "/manage/premises/$premiseId?activeTab=upcoming"
            }
            .check(
                CoreDsl.css(".moj-sub-navigation__link:contains('Upcoming')").exists()
            )

    fun hitViewPlacementPageAndDoChecks() =
        HttpDsl.http("View Placement Page")
            .get { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                val spaceBookingId = session.getString(BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey)
                "/manage/premises/$premiseId/placements/$spaceBookingId"
            }
            .check(
                CoreDsl.css(".moj-sub-navigation__link:contains('Placement details')").exists(),
                CoreDsl.css(".govuk-heading-m:contains('Arrival information')").exists()
            )

    fun hitRecordArrivalPageAndDoChecks() =
        HttpDsl.http("Record Arrival Page")
            .get { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                val spaceBookingId = session.getString(BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey)
                "/manage/premises/$premiseId/placements/$spaceBookingId/arrival"
            }
            .check(
                CoreDsl.css(".govuk-heading-l:contains('Record someone as arrived')").exists(),
                casSelectorHelper.getCsrfHiddenFieldValue(BookingManagementSimulationSession.RECORD_ARRIVAL_CSRF_TOKEN_VALUE.sessionKey)
            )


    fun submitRecordArrivalFormAndDoChecks(): HttpRequestActionBuilder {
        return HttpDsl.http("Record Arrival Page - Form Submitted")
            .post { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                val spaceBookingId = session.getString(BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey)
                "/manage/premises/$premiseId/placements/$spaceBookingId/arrival"
            }
            .header("content-type", "application/x-www-form-urlencoded")
            .formParam("_csrf", "#{${BookingManagementSimulationSession.RECORD_ARRIVAL_CSRF_TOKEN_VALUE.sessionKey}}")
            .formParam("arrivalDateTime-day", arrivalDay.toString())
            .formParam("arrivalDateTime-month", arrivalMonth)
            .formParam("arrivalDateTime-year", arrivalYear)
            .formParam("arrivalTime", "15:15")
            .check(
                HttpDsl.status().`is`(200)
            )
    }

    fun hitEditKeyWorkerPageAndDoChecks() =
        HttpDsl.http("Edit Key-worker Page")
            .get { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                val spaceBookingId = session.getString(BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey)
                "/manage/premises/$premiseId/placements/$spaceBookingId/keyworker"
            }
            .check(
                CoreDsl.css(".govuk-heading-l:contains('Edit keyworker details')").exists()
            )

    fun submitEditKeyWorkerFormAndDoChecks(): HttpRequestActionBuilder {
        return HttpDsl.http("Edit Key-worker Page - Form Submitted")
            .post { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                val spaceBookingId = session.getString(BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey)
                "/manage/premises/$premiseId/placements/$spaceBookingId/keyworker"
            }
            .header("content-type", "application/x-www-form-urlencoded")
            .formParam("_csrf", "#{${BookingManagementSimulationSession.RECORD_ARRIVAL_CSRF_TOKEN_VALUE.sessionKey}}")
            .formParam("staffCode", robinTorphyStaffCode)
            .check(
                HttpDsl.status().`is`(200)
            )
    }

    fun hitRecordDeparturePageAndDoChecks() =
        HttpDsl.http("Record Departure Page")
            .get { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                val spaceBookingId = session.getString(BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey)
                "/manage/premises/$premiseId/placements/$spaceBookingId/departure/new"
            }
            .check(
                CoreDsl.css(".govuk-heading-l:contains('Record a departure')").exists(),
            )


    fun submitRecordDepartureFormAndDoChecks(): HttpRequestActionBuilder {
        return HttpDsl.http("Record Departure Page - Form Submitted")
            .post { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                val spaceBookingId = session.getString(BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey)
                "/manage/premises/$premiseId/placements/$spaceBookingId/departure/new"
            }
            .header("content-type", "application/x-www-form-urlencoded")
            .formParam("_csrf", "#{${BookingManagementSimulationSession.RECORD_ARRIVAL_CSRF_TOKEN_VALUE.sessionKey}}")
            .formParam("departureDate-day", (arrivalDay + 1).toString())
            .formParam("departureDate-month", arrivalMonth)
            .formParam("departureDate-year", arrivalYear)
            .formParam("departureTime", "12:15")
            .formParam("reasonId", "ea6f21d9-e658-487f-9765-d8b09187df93")
            .check(
                HttpDsl.status().`is`(200)
            )
    }

    fun hitRecordDepartureNotesPageAndDoChecks() =
        HttpDsl.http("Record Departure Notes Page")
            .get { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                val spaceBookingId = session.getString(BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey)
                "/manage/premises/$premiseId/placements/$spaceBookingId/departure/notes"
            }
            .check(
                CoreDsl.css(".govuk-heading-l:contains('Record a departure')").exists()
            )

    fun submitRecordDepartureNotesFormAndDoChecks(): HttpRequestActionBuilder {
        return HttpDsl.http("Record Departure Notes Page - Form Submitted")
            .post { session ->
                val premiseId = session.getString(BookingManagementSimulationSession.PREMISE_ID.sessionKey)
                val spaceBookingId = session.getString(BookingManagementSimulationSession.SPACE_BOOKING_ID.sessionKey)
                "/manage/premises/$premiseId/placements/$spaceBookingId/departure/notes"
            }
            .header("content-type", "application/x-www-form-urlencoded")
            .formParam("_csrf", "#{${BookingManagementSimulationSession.RECORD_ARRIVAL_CSRF_TOKEN_VALUE.sessionKey}}")
            .formParam("notes", "test notes")
            .check(
                HttpDsl.status().`is`(200)
            )
    }
}