package uk.gov.justice.digital.hmpps.team.maw.helper

import uk.gov.justice.digital.hmpps.helper.SelectorHelper
import uk.gov.justice.digital.hmpps.team.maw.model.CaseDetailsInSession

class MawSelectorHelper: SelectorHelper() {
    fun checkCaseDetailsAreInPageHeader() =
        listOf(
            super.checkSessionValueExistsInH1(
                sessionKey = CaseDetailsInSession.NAME.sessionKey
            ),
            super.checkSessionValueExistsInH1(
                sessionKey = CaseDetailsInSession.TIER.sessionKey
            ),
            super.checkSessionValueExistsInH1(
                sessionKey = CaseDetailsInSession.CRN.sessionKey
            )
        )

    fun checkCaseViewTabsArePresent() = listOf(
        super.checkTabExists(tabName = "Summary"),
        super.checkTabExists(tabName = "Probation record"),
        super.checkTabExists(tabName = "Risk"),
        super.checkTabExists(tabName = "Documents")
    )
}
