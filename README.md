# Sorting Hat API

The Sorting Hart API provides rest services to assign pledges to Hogwarts houses using the "Rules of the Hat".

- GET /roster/{id}
Downloads a roster file including house assignments

- POST /roster
Uploads a roster file to request house assignments

- GET /rosters
Retrieves all rosters submitted so far

The API is consumed by the [Sorting Hat Web App](https://github.com/mpdroid/hat-web/blob/master/README.md).

Demonstrates the use of spring-boot, rest API and drools rule engine.

## Development server

Run `./gradlew clean bootrun` for a dev server. API base url will be `http://localhost:8080/`. 

