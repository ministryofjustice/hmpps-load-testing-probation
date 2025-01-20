package uk.gov.justice.digital.hmpps.team.cas.constants

import java.util.UUID

const val noOfFastUsers = 8
const val noOfAverageSpeedUsers = 4
const val noOfSlowerUsers = 3

const val fastBookingMadePauseOnCruDashboardPage = 4L
const val fastBookingMadePauseOnPlacementRequestPage = 2L
const val fastBookingMadePauseOnFindASpacePage = 4L
const val fastBookingMadePauseOnOccupancyViewPage = 10L
const val fastBookingMadePauseOnConfirmBookingPage = 3L
const val fastBookingMadePauseOnConfirmBookingSubmitPage = 1L

const val averageSpeedBookingMadePauseOnCruDashboardPage = 8L
const val averageSpeedBookingMadePauseOnPlacementRequestPage = 4L
const val averageSpeedBookingMadePauseOnFindASpacePage = 8L
const val averageSpeedBookingMadePauseOnOccupancyViewPage = 20L
const val averageSpeedBookingMadePauseOnConfirmBookingPage = 6L
const val averageSpeedBookingMadePauseOnConfirmBookingSubmitPage = 1L

const val slowerBookingMadePauseOnCruDashboardPage = 16L
const val slowerBookingMadePauseOnPlacementRequestPage = 8L
const val slowerBookingMadePauseOnFindASpacePage = 16L
const val slowerBookingMadePauseOnOccupancyViewPage = 40L
const val slowerBookingMadePauseOnConfirmBookingPage = 12L
const val slowerBookingMadePauseOnConfirmBookingSubmitPage = 1L

// values for JDBC feed where clause
const val cruDashboardNotMatchedStatus = "notMatched"
val apAreaIdForNorthEast: UUID = UUID.fromString("64ad8602-5130-41da-bb2b-1c287b88fd90")
val apAreaIdForSouthWestAndSouthCentral: UUID = UUID.fromString("667cc74b-60f9-4848-822b-2e8f7712cdf1")