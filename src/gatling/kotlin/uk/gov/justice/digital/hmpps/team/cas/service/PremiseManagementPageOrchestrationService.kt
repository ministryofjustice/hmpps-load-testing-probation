package uk.gov.justice.digital.hmpps.team.cas.service

import io.gatling.javaapi.core.CoreDsl
import io.gatling.javaapi.http.HttpDsl
import uk.gov.justice.digital.hmpps.team.cas.helper.CasSelectorHelper
import uk.gov.justice.digital.hmpps.team.cas.model.PremiseManagementSimulationSession

class PremiseManagementPageOrchestrationService(
    private val casSelectorHelper: CasSelectorHelper = CasSelectorHelper()
) {

    fun hitViewPremisePageAndDoChecks() =
        HttpDsl.http("View Premise Page")
            .get { session ->
                val premiseId = session.getString(PremiseManagementSimulationSession.PREMISE_ID.sessionKey)
                "/manage/premises/$premiseId?activeTab=upcoming"
            }
            .check(
                CoreDsl.css(".moj-identity-bar__title:contains('#{${PremiseManagementSimulationSession.PREMISE_NAME.sessionKey}}')").exists(),
            )

    fun hitViewSpacesInPremisePageAndDoChecks() =
        HttpDsl.http("View Day Calendar for spaces Page")
            .get { session ->
                val premiseId = session.getString(PremiseManagementSimulationSession.PREMISE_ID.sessionKey)
                "/manage/premises/$premiseId/occupancy"
            }
            .check(
                CoreDsl.css(".govuk-heading-l:contains('View spaces in')").exists(),
                casSelectorHelper.getCalendarLinkHrefValueAndStoreInSession(PremiseManagementSimulationSession.OCCUPANCY_VIEW_FOR_DAY_LINK_HREF_VALUE.sessionKey)
            )

    fun hitOccupancyViewForDayPageAndDoChecks() =
        HttpDsl.http("View Occupancy View for Day Page")
            .get { session ->
                session.getString(PremiseManagementSimulationSession.OCCUPANCY_VIEW_FOR_DAY_LINK_HREF_VALUE.sessionKey)
            }
            .check(
                CoreDsl.css(".govuk-pagination__link-title:contains('Previous day')").exists()
            )

}