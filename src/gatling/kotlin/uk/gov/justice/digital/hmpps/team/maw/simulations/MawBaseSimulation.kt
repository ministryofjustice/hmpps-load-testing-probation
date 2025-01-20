package uk.gov.justice.digital.hmpps.team.maw.simulations

import uk.gov.justice.digital.hmpps.BaseSimulationFrontEndRoutes

abstract class MawBaseSimulation : BaseSimulationFrontEndRoutes() {
    val nominatedPduNameOne: String = "North Wales"
    val nominatedPduCodeOne: String = "WPTNWS"
    val nominatedTeamNameOne: String = "Wrexham - Team 1"
    val nominatedTeamCodeOne: String = "N03F01"
}
