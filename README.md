# Sorting Hat API

The Sorting Hat API provides rest services to assign pledges to Hogwarts houses using the "Rules of the Hat". The API is consumed by the Web App ([repo](https://github.com/mpdroid/hat-web/blob/master/README.md)).

## Upload a roster file
### /roster
### POST
Uploads a roster file (in csv format) for sorting, e.g. 
```
First Name, Last Name, Suffix, Gender, Date of Birth, Net Worth, Hair Color, Elves Owned, Dementors Battled
Harry, Potter, , male, 1980-07-30, 1000000, black, 0, 5
Draco, Malfoy, , male, 1980-03-01, 1000000000, silver, 5, 0
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
            "dob": "1980-07-29T00:00:00.000+0000",
            "netWorth": 1000000,
            "hairColor": "black",
            "elvesOwned": 0,
            "dementorsBattled": 5,
            "randomGroup": 2,
            "house": "Gryffindor",
            "rulesFired": [
                "Hair color is Black"
            ]
        }
    ]
}
```


Demonstrates the use of;
- Spring Boot
- Rest API
- Drools rule engine

## Development server

Run `./gradlew clean bootrun` for a dev server. API base url will be `http://localhost:8080/`. 

