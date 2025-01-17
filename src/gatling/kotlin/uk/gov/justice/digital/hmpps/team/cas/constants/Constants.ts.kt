package uk.gov.justice.digital.hmpps.team.cas.constants

import java.util.UUID

const val noOfFastUsers = 6
const val noOfSlowerUsers = 4

const val fastBookingMadePauseOnCruDashboardPage = 4L
const val fastBookingMadePauseOnPlacementRequestPage = 2L
const val fastBookingMadePauseOnFindASpacePage = 4L
const val fastBookingMadePauseOnOccupancyViewPage = 10L
const val fastBookingMadePauseOnConfirmBookingPage = 3L
const val fastBookingMadePauseOnConfirmBookingSubmitPage = 1L

const val slowerBookingMadePauseOnCruDashboardPage = 8L
const val slowerBookingMadePauseOnPlacementRequestPage = 4L
const val slowerBookingMadePauseOnFindASpacePage = 8L
const val slowerBookingMadePauseOnOccupancyViewPage = 20L
const val slowerBookingMadePauseOnConfirmBookingPage = 6L
const val slowerBookingMadePauseOnConfirmBookingSubmitPage = 2L

// values for JDBC feed where clause
const val cruDashboardNotMatchedStatus = "notMatched"
val apAreaIdForNorthEast: UUID = UUID.fromString("64ad8602-5130-41da-bb2b-1c287b88fd90")
val apAreaIdForSouthWestAndSouthCentral: UUID = UUID.fromString("667cc74b-60f9-4848-822b-2e8f7712cdf1")