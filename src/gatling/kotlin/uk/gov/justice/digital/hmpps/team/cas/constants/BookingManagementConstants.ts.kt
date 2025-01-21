package uk.gov.justice.digital.hmpps.team.cas.constants

const val noOfBookingManagementSlowUsers = 2
const val noOfBookingManagementAverageSpeedUsers = 2
const val noOfBookingManagementFastUsers = 6

const val slowBookingManagementPauseOnViewPremisePage = 12L
const val slowBookingManagementPauseOnViewPlacementPage = 8L
const val slowBookingManagementPauseOnRecordArrivalPage = 20L
const val slowBookingManagementPauseAfterRecordArrival = 1L
const val slowBookingManagementPauseOnEditKeyWorkerPage = 12L
const val slowBookingManagementPauseAfterKeyWorkerEdited = 1L
const val slowBookingManagementPauseOnRecordDeparturePage = 20L
const val slowBookingManagementPauseAfterSubmitRecordDeparture = 1L
const val slowBookingManagementPauseOnRecordDepartureNotesPage = 12L
const val slowBookingManagementPauseAfterSubmitRecordDepartureNotes = 1L

const val averageSpeedBookingManagementPauseOnViewPremisePage = 6L
const val averageSpeedBookingManagementPauseOnViewPlacementPage = 4L
const val averageSpeedBookingManagementPauseOnRecordArrivalPage = 10L
const val averageSpeedBookingManagementPauseAfterRecordArrival = 1L
const val averageSpeedBookingManagementPauseOnEditKeyWorkerPage = 6L
const val averageSpeedBookingManagementPauseAfterKeyWorkerEdited = 1L
const val averageSpeedBookingManagementPauseOnRecordDeparturePage = 10L
const val averageSpeedBookingManagementPauseAfterSubmitRecordDeparture = 1L
const val averageSpeedBookingManagementPauseOnRecordDepartureNotesPage = 6L
const val averageSpeedBookingManagementPauseAfterSubmitRecordDepartureNotes = 1L

const val fastBookingManagementPauseOnViewPremisePage = 3L
const val fastBookingManagementPauseOnViewPlacementPage = 2L
const val fastBookingManagementPauseOnRecordArrivalPage = 5L
const val fastBookingManagementPauseAfterRecordArrival = 1L
const val fastBookingManagementPauseOnEditKeyWorkerPage = 3L
const val fastBookingManagementPauseAfterKeyWorkerEdited = 1L
const val fastBookingManagementPauseOnRecordDeparturePage = 5L
const val fastBookingManagementPauseAfterSubmitRecordDeparture = 1L
const val fastBookingManagementPauseOnRecordDepartureNotesPage = 3L
const val fastBookingManagementPauseAfterSubmitRecordDepartureNotes = 1L

// values for JDBC feed where clause

const val swscMenPremise1QCode = "Q005"
const val robinTorphyStaffCode: String = "N07B481"