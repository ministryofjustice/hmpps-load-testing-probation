package uk.gov.justice.digital.hmpps.team.cas.constants

import java.util.UUID

const val noOfBookingMadeUsers = 15

val bookingMadePauseBeforeStart = 0L to 2L
val bookingMadePauseOnCruDashboardPage = 4L to 8L
val bookingMadePauseOnPlacementRequestPage = 2L to 4L
val bookingMadePauseOnFindASpacePage = 4L to 8L
val bookingMadePauseOnOccupancyViewPage = 10L to 20L
val bookingMadePauseOnConfirmBookingPage = 3L to 6L
val bookingMadePauseOnConfirmBookingSubmitPage = 1L to 2L

// values for JDBC feed where clause
const val cruDashboardNotMatchedStatus = "notMatched"
val cruManagementAreaIdForNorthEast: UUID = UUID.fromString("64ad8602-5130-41da-bb2b-1c287b88fd90")