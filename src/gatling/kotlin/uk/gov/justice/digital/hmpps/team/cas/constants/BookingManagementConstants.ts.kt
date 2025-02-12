package uk.gov.justice.digital.hmpps.team.cas.constants

const val noOfBookingManagementUsers = 20

val pauseBeforeStart = 0L to 2L
val pauseOnViewPremisePage = 3L to 6L
val pauseOnViewPlacementPage = 2L to 4L
val pauseOnRecordArrivalPage = 5L to 10L
val pauseAfterRecordArrival = 0L to 2L
val pauseOnEditKeyWorkerPage = 3L to 6L
val pauseAfterKeyWorkerEdited = 1L to 2L
val pauseOnRecordDeparturePage = 5L to 10L
val pauseAfterSubmitRecordDeparture = 0L to 2L
val pauseOnRecordDepartureNotesPage = 3L to 6L
val pauseAfterSubmitRecordDepartureNotes = 1L to 2L

// values for JDBC feed where clause
const val swscMenPremise1QCode = "Q005"
const val robinTorphyStaffCode: String = "N07B481"