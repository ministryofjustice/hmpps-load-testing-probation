package uk.gov.justice.digital.hmpps.team.cas.model

enum class BookingMadeSimulationSession(val sessionKey: String) {
    PLACEMENT_REQUEST_ID("placementrequestid"),
    VIEW_SPACES_LINK_HREF_VALUE("viewSpacesLinkHrefValue"),
    CONFIRM_BOOKING_CSRF_TOKEN_VALUE("confirmBookingCsrfTokenValue"),
}

enum class BookingManagementSimulationSession(val sessionKey: String) {
    SPACE_BOOKING_ID("spacebookingid"),
    PREMISE_ID("premiseid"),
    RECORD_ARRIVAL_CSRF_TOKEN_VALUE("recordArrivalCsrfTokenValue"),
}

enum class PremiseManagementSimulationSession(val sessionKey: String) {
    PREMISE_ID("premiseid"),
    PREMISE_NAME("premisename"),
    OCCUPANCY_VIEW_FOR_DAY_LINK_HREF_VALUE("occupancyViewForDayLinkHrefValue"),
}
