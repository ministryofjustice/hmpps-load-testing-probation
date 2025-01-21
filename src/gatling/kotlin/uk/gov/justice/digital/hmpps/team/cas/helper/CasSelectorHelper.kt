package uk.gov.justice.digital.hmpps.team.cas.helper

import io.gatling.javaapi.core.CheckBuilder.Final
import io.gatling.javaapi.core.CoreDsl
import uk.gov.justice.digital.hmpps.helper.SelectorHelper

class CasSelectorHelper: SelectorHelper() {

    fun getViewSpacesLinkHrefValueAndStoreInSession(sessionKey: String): Final =
        CoreDsl.css(".govuk-summary-card__actions a", "href")
            .findAll()
            .transformWithSession { valuesFound, _ ->
                valuesFound.firstOrNull {
                    it.contains("/space-search/occupancy/")
                }
            }.notNull().saveAs(sessionKey)


    fun getCsrfHiddenFieldValue(sessionKey: String): Final =
        CoreDsl.css("input", "value")
            .findAll()
            .transformWithSession { valuesFound, _ ->
                valuesFound[0]
            }.notNull().saveAs(sessionKey)


    fun getCalendarLinkHrefValueAndStoreInSession(sessionKey: String): Final =
        CoreDsl.css(".calendar__link", "href")
            .findAll()
            .transformWithSession { valuesFound, _ ->
                valuesFound
                    .filter {  it.contains("/occupancy/day/") }
                    .randomOrNull()
            }.notNull().saveAs(sessionKey)
}
