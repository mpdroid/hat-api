# Sorting Hat API

The Sorting Hart API provides rest services to assign pledges to Hogwarts houses using the "Rules of the Hat".

## Upload a roster file
### /roster
### POST
Uploads a roster file for sorting, e.g. 
```
First Name, Last Name, Suffix, Gender, Date of Birth, Net Worth, Hair Color
Harry, Potter, , male, 1980-07-30, 1000000, black
Draco, Malfoy, , male, 1980-03-01, 1000000000, silver
```

## Get a roster
### /rosters/:id
### GET
### Required:
id=[long]
Retrieves a roster given roster id e.g.
```
{
    "id": 1539460047712,
    "submitDate": "2018-10-13T19:47:27.712+0000",
    "hasTheHatDecided": true,
    "students": [
        {
            "id": 1,
            "firstName": "Harry",
            "lastName": "Potter",
            "nameSuffix": "",
            "gender": "male",
            "dob": "1980-07-30T05:00:00.000+0000",
            "netWorth": 1000000,
            "hairColor": "black",
            "house": "Gryffindor",
            "howAssigned": "rule",
            "randomGroup": 0
        }
    ]
}
```

The API is consumed by the [Sorting Hat Web App](https://github.com/mpdroid/hat-web/blob/master/README.md).

Demonstrates the use of;
- Spring-Boot
- Rest API
- Drools rule engine

## Development server

Run `./gradlew clean bootrun` for a dev server. API base url will be `http://localhost:8080/`. 

