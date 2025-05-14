# hmpps-probation-load-testing

## Environment pre-requisites

Before running load tests, consider your target environment's configuration:

* Should emails be disabled during load testing?
* Should domain events be disabled during load testing?
* Is the environment scaled the same as production, and does it matter? Scaling includes
 * Size of RDS instance
 * Number of pods for API/UI
 * Memory allocated to pods for API/UI

## Prep for running load test
To kick off the load tests you will need to do the following:

1. Port forward to [Access the TEST RDS Database](https://user-guide.cloud-platform.service.justice.gov.uk/documentation/other-topics/rds-external-access.html#accessing-your-rds-database)
2. Copy the `connection.sid` Application cookie from your web browser 
* Login to your web application
* Right-click browser > Inspect
* Go to `Application` tab > `Storage` in left nav > Expand `Cookies`
* Find the `connect.sid` cookie in the list and copy its' value from the `Value` column
* Copy the value for later step

## Running load tests
1. Change directory in a terminal and navigate to this repo's root folder
2. Run a simulation of your choice:
```bash
./gradlew gatlingRun --simulation uk.gov.justice.digital.hmpps.team.*.*Simulation
```
3. You may need to include extra env vars in the command (for secrets)

## Teams
* There is a team package included in the repo: `uk.gov.justice.digital.hmpps.team`
* If you are contributing to this for the first time, add your team package in there  
* Have a look at the code in the pre-existing teams packages where you will see (hopefully still!) a consistent way of doing things which reuses code from other packages (at the same level as the `teams` package)

### Community Accommodation Services (CAS) team
For a full understanding of what was done here please read the [CAS1: Find & Book Load Testing](https://dsdmoj.atlassian.net/wiki/spaces/AP/pages/5442600996/CAS1+Find+Book+Load+Testing) wiki.
* Team package: `uk.gov.justice.digital.hmpps.team.cas`
* Web app to login into: https://approved-premises-test.hmpps.service.justice.gov.uk
* Simulations:
1. BookingManagementSimulation:
```bash
./gradlew gatlingRun --simulation uk.gov.justice.digital.hmpps.team.cas.simulations.BookingManagementSimulation -Dprotocol=https -Ddomain=approved-premises-test.hmpps.service.justice.gov.uk -Ddb_port=5432 -Ddb_name=<secret> -Ddb_username=<secret> -Ddb_password=<secret> -DconnectSidCookieValue=<copied_in_prep_section>
```
2. ApplyJourneySimulation:
```bash
./gradlew gatlingRun --simulation uk.gov.justice.digital.hmpps.team.cas.simulations.ApplyJourneySimulation -Dprotocol=https -Ddomain=approved-premises-api-dev.hmpps.service.justice.gov.uk -DauthBaseUrl=https://sign-in-dev.hmpps.service.justice.gov.uk
```
3. BookingMadeSimulation:
```bash
./gradlew gatlingRun --simulation uk.gov.justice.digital.hmpps.team.cas.simulations.BookingMadeSimulation -Dprotocol=https -Ddomain=approved-premises-test.hmpps.service.justice.gov.uk -Ddb_port=5432 -Ddb_name=<secret> -Ddb_username=<secret> -Ddb_password=<secret> -DconnectSidCookieValue=<copied_in_prep_section>
```
4. PremiseManagementSimulation:
```bash
./gradlew gatlingRun --simulation uk.gov.justice.digital.hmpps.team.cas.simulations.PremiseManagementSimulation -Dprotocol=https -Ddomain=approved-premises-test.hmpps.service.justice.gov.uk -Ddb_port=5432 -Ddb_name=<secret> -Ddb_username=<secret> -Ddb_password=<secret> -DconnectSidCookieValue=<copied_in_prep_section>
```
5. Cas2v2ApplyJourneySimulation:
```bash
./gradlew gatlingRun --simulation uk.gov.justice.digital.hmpps.team.cas.simulations.cas2v2.Cas2v2ApplyJourneySimulation -Dprotocol=https -Ddomain=approved-premises-api-dev.hmpps.service.justice.gov.uk -DauthBaseUrl=https://sign-in-dev.hmpps.service.justice.gov.uk
```

* In the above `gradle` commands, the `DconnectSidCookieValue` env vars needs to be set to the `connection.sid` value copied during the `Prep for running load test` section

#### FYIs
* all simulations hit FE routes against the web application apart from `ApplyJourneySimulation` and `Cas2v2ApplyJourneySimulation`. `ApplyJourneySimulation` is an example that hits BE endpoints direct (this is hanging around from the spike but we left it in as an example encase helpful to other contributors)
* the `BookingManagementSimulation` is probably the gold standard example as it has a combination of `GET` routes and `POST` routes (for submitting forms throughout the simulation). See what we did [here](https://dsdmoj.atlassian.net/wiki/spaces/AP/pages/5501583503/Booking+Management+Simulation)
* AuthorizationService provides the jwt on line 9 : 
* val jwt = System.getProperty("jwt")
* For simple usage you can also hardcode a jwt. 

### Manage-a-workforce (MaW) team
For a full understanding of what was done here please read the [MaW Load Testing](https://dsdmoj.atlassian.net/wiki/spaces/MaS/pages/4578477149/Load+Testing) wiki.
* Team package: `uk.gov.justice.digital.hmpps.team.maw`
* Web app to login into: https://workforce-management-dev.hmpps.service.justice.gov.uk
* Simulations:
1. AllocateCaseSimulation:
   
```bash
./gradlew gatlingRun --simulation uk.gov.justice.digital.hmpps.team.maw.simulations.AllocateCaseSimulation -Dprotocol=https -Ddomain=workforce-management-dev.hmpps.service.justice.gov.uk -Ddb_port=5432 -Ddb_name=<secret> -Ddb_username=<secret> -Ddb_password=<secret> -DconnectSidCookieValue=<copied_in_prep_section>
```