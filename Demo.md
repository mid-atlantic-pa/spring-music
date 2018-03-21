# Setup

## Create mysql service and wait until it is completed
cf create-service p.mysql db-small spring-music-db
watch -n 2 cf services

## Build the application locally and push
./gradlew clean build
cf push

# Illustration
https://spring-music.apps.gcp.winterfell.live

# Do It

## Make update to the code
./gradlew clean build -x test

## Push it
cf push

## 

## Make update to the code
./gradlew clean build

## Rename the existing app to venerable
cf rename spring-music spring-music-venerable

## Push the updated app with a alternative route
cf push -n spring-music-candidate
 
## Review the App Manager while the new app is deploying

## Test and demonstrate the new app
https://spring-music-candidate.apps.gcp.winterfell.live

## Add the new app into the router mix
cf map-route spring-music apps.gcp.winterfell.live -n spring-music

## Remove the venerable app from the mix
cf unmap-route spring-music-venerable apps.gcp.winterfell.live -n spring-music

## Scale down the venerable app
cf scale -i 0 spring-music-venerable

## Remove the candidate route
cf unmap-route spring-music apps.gcp.winterfell.live -n spring-music-candidate

# Teardown
cf delete spring-music -r -f
cf delete spring-music-venerable -r -f
cf delete-service spring-music-db