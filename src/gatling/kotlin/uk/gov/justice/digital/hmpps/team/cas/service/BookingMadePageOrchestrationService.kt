package uk.gov.justice.digital.hmpps.team.cas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.http.HttpDsl
import io.gatling.javaapi.http.HttpRequestActionBuilder
import uk.gov.justice.digital.hmpps.team.cas.helper.CasSelectorHelper
import uk.gov.justice.digital.hmpps.team.cas.model.BookingMadeSimulationSession

class BookingMadePageOrchestrationService(
    private val casSelectorHelper: CasSelectorHelper = CasSelectorHelper()
) {
    fun hitCruDashboardPageAndDoChecks() =
        HttpDsl.http("CRU Dashboard Page")
            .get("/admin/cru-dashboard")
            .check(
                CoreDsl.css(".govuk-heading-l:contains('CRU Dashboard')").exists(),
            )


    fun hitPlacementRequestPageAndDoChecks() =
        HttpDsl.http("Placement Request Page")
            .get { session ->
                val placementRequestId = session.getString(BookingMadeSimulationSession.PLACEMENT_REQUEST_ID.sessionKey)
                "/admin/placement-requests/$placementRequestId"
            }
            .check(
                CoreDsl.css(".moj-identity-bar__title:contains('Placement request')").exists()
            )

    fun hitFindASpacePageAndDoChecks() =
        HttpDsl.http("Find A Space Page")
            .get { session ->
                val placementRequestId = session.getString(BookingMadeSimulationSession.PLACEMENT_REQUEST_ID.sessionKey)
                "/match/placement-requests/$placementRequestId/space-search/new"
            }
            .check(
                CoreDsl.css(".govuk-heading-l:contains('Find a space')").exists(),
                casSelectorHelper.getViewSpacesLinkHrefValueAndStoreInSession(BookingMadeSimulationSession.VIEW_SPACES_LINK_HREF_VALUE.sessionKey)
            )


    fun hitOccupancyViewPageAndDoChecks() =
        HttpDsl.http("Occupancy View Page")
            .get { session ->
                session.getString(BookingMadeSimulationSession.VIEW_SPACES_LINK_HREF_VALUE.sessionKey)
            }
            .check(
                CoreDsl.css(".govuk-heading-l:contains('View spaces in')").exists()
            )

    fun hitConfirmBookingPageAndDoChecks(): HttpRequestActionBuilder {
        return HttpDsl.http("Confirm Booking Page")
            .get { session ->
                val placementRequestId = session.getString(BookingMadeSimulationSession.PLACEMENT_REQUEST_ID.sessionKey)
                val viewSpacesLink = session.getString(BookingMadeSimulationSession.VIEW_SPACES_LINK_HREF_VALUE.sessionKey)
                val approvedPremiseId = extractApIdFromViewSpacesLinkHref(viewSpacesLink)
                val criteria = extractCriteriaFromViewSpacesLinkHref(viewSpacesLink)
                val arrivalDate = "2027-01-01"
                val departureDate = "2027-12-01"
                var submitConfirmBookPath = "/match/placement-requests/$placementRequestId/space-bookings/$approvedPremiseId/new?arrivalDate=$arrivalDate&departureDate=$departureDate"
                if (criteria != null) {
                    submitConfirmBookPath += "&criteria = $criteria"
                }
                submitConfirmBookPath
            }
            .check(
                CoreDsl.css(".govuk-heading-l:contains('Confirm booking')").exists(),
                casSelectorHelper.getCsrfHiddenFieldValue(BookingMadeSimulationSession.CONFIRM_BOOKING_CSRF_TOKEN_VALUE.sessionKey)
            )
    }

    fun submitConfirmBookingFormAndDoChecks(): HttpRequestActionBuilder {
        return HttpDsl.http("Confirm Booking Page - Form Submitted")
            .post { session ->
                val placementRequestId = session.getString(BookingMadeSimulationSession.PLACEMENT_REQUEST_ID.sessionKey)
                val viewSpacesLink = session.getString(BookingMadeSimulationSession.VIEW_SPACES_LINK_HREF_VALUE.sessionKey)
                val approvedPremiseId = extractApIdFromViewSpacesLinkHref(viewSpacesLink)
                val criteria = extractCriteriaFromViewSpacesLinkHref(viewSpacesLink)
                val arrivalDate = "2027-01-01"
                val departureDate = "2027-12-01"
                var submitConfirmBookPath = "/match/placement-requests/$placementRequestId/space-bookings/$approvedPremiseId?arrivalDate=$arrivalDate&departureDate=$departureDate"
                if (criteria != null) {
                    submitConfirmBookPath += "&criteria = $criteria"
                }
                submitConfirmBookPath
            }
            .header("content-type", "application/x-www-form-urlencoded")
            .formParam ("_csrf", "#{${BookingMadeSimulationSession.CONFIRM_BOOKING_CSRF_TOKEN_VALUE.sessionKey}}")
            .formParam ("arrivalDate", "2027-01-01")
            .formParam ("departureDate", "2027-12-01")
            .formParam ("criteria", "acceptsNonSexualChildOffenders,isCatered,hasEnSuite")
            .check(
                HttpDsl.status().`is`(200)
            )
    }

    private fun extractApIdFromViewSpacesLinkHref(viewSpacesLinkHref: String?): String {
        val fromOccupancy = viewSpacesLinkHref?.substring(viewSpacesLinkHref.indexOf("/occupancy/"))
        val fromAfterOccupancy = fromOccupancy?.replace("/occupancy/", "")
        val splitApIdFromParams: List<String> = fromAfterOccupancy?.split("?")!!
        return splitApIdFromParams[0]
    }

    private fun extractCriteriaFromViewSpacesLinkHref(viewSpacesLinkHref: String?): String? {
        val indexOfCriteria = viewSpacesLinkHref?.indexOf("criteria")
        if (indexOfCriteria != null && indexOfCriteria > -1) {
            val fromCriteria = viewSpacesLinkHref.substring(indexOfCriteria)
            val fromAfterCriteria = fromCriteria.substring(9)
            return fromAfterCriteria
        }
        return null
    }
}
