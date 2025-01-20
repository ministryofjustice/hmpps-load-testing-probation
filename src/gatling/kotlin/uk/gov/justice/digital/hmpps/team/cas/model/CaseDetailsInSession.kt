package uk.gov.justice.digital.hmpps.team.cas.model

enum class CasPlacementRequestSession(val sessionKey: String) {
    PLACEMENT_REQUEST_ID("id"),
    VIEW_SPACES_LINK_HREF_VALUE("viewSpacesLinkHrefValue"),
    CSRF_TOKEN_VALUE("csrfTokenValue"),
    APPROVED_PREMISE_ID("approvedPremiseId"),
}
