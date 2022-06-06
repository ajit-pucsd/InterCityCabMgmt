# Intercity Cab Management System

Build an application for intercity Cab Management System which will be able to registerCab,AddCity, bookCab etc.

### Requirements

This portal should be able to :
```
1. Register cabs.
2. Onboard various cities where cab services are provided.
3. Change current city (location) of any cab.
4. Change state of any cab. For this you will have to define a state machine for the cab ex:
a cab must have at least these two basic states; IDLE and ON_TRIP
5. Book cabs based on their availability at a certain location. In case more than one cab are
available , use the following strategy;
a. Find out which cab has remained idle the most and assign it.
b. In case of clash above, randomly assign any cab
Assumption : a cab once assigned a trip cannot cancel/reject it
```

### API Provided

1. Register a Cab

```
Method : POST
url : http://<Domain_name>:8080/cab
RequestBody:
{"number":<car_number>,"cityId":<city_id>}
```

2. Add a city

```
Method : POST
url : http://<Domain_name>:8080/city
RequestBody:
{"name":<city_name>}
```

3. Book a cab

```
Method : POST
url : http://<Domain_name>:8080/book
RequestBody:
{"sourceCityId":<source_city_id>,"destinationCityId":<dest_city_id>}
```

3. Book a cab

```
Method : POST
url : http://<Domain_name>:8080/book
RequestBody:
{"sourceCityId":<source_city_id>,"destinationCityId":<dest_city_id>}
```

4. Change state of any cab

```
Method : PUT
url : http://<Domain_name>:8080/cab/{cab_id}/changeState
RequestBody:
{"currentState":"ON_TRIP","nextState":"ON_TRIP","cabId":"1"}
```

5. Update cab status by uploading cab snapshot

```
Method : POST
url : http://<Domain_name>:8080/cab/uploadSnapshot
RequestBody:
[{"cabId":<cab_id>,"cityId":<city_id>,"cabState":<cab_state>}]
```

```
And few more REST API to get details
```


## Running the tests
Added Unit test cases using Junit and Mockito for
   - Register a cab
   - Book a Cab

## Deployment

Its a SpringBootApplication!!!

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management
