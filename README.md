

# Online Book Store
![ci workflow](https://github.com/danilo-ambrosio/bookstore/actions/workflows/maven.yml/badge.svg)
![test coverage](https://raw.githubusercontent.com/danilo-ambrosio/bookstore/refs/heads/master/.github/badges/jacoco.svg)

Backend for an online book store app supporting features as inventory management, purchase/pricing and loyalty program.

### Development guideline
Here's a list of software techniques and principles adopted in this backend implementation:
- DDD (Strategic/Tactical patterns): Ubiquitous Language (tried to use nouns and verbs found in the home assignment doc), Aggregate, Value Objects, Domain Events
- Object-oriented Design: encapsulation, inheritance, abstraction, high cohesion/low coupling...
- Clean architecture: separation of concerns, inward dependency sense only, framework independence
- Rest API: stateless, client/server architecture, unique resource identification...
- Asynchronous operations: non-blocking propagation of domain events, supporting eventual consistency and better definition of transaction boundaries for aggregates
- TDD: mostly implemented using the test-first approach

## Worklog

This is a [worklog](https://github.com/danilo-ambrosio/bookstore/blob/master/worklog.md) containing detailed information on my thought process during the project implementation. 

## Requirements

All infrastructure dependencies are managed by Docker, so it's mandatory to install it and keep it up and running. Integration tests also rely on containers.

###  Framework and tools
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot/) (data, core, web)
- [Mongo DB](https://www.mongodb.com/)
- [Maven](https://maven.apache.org)
- [Docker](https://www.docker.com/)

## How to run the app

The following command will compile, package and run the app on *localhost:8080*, while MongoDB will also be up on port 27017:

```
docker compose up --build
```

If the app initialization succeeds, the following messages will be shown in the logs:

```
app-1      | 2025-02-24T17:11:36.345Z  INFO 1 --- [bookstore] [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
app-1      | 2025-02-24T17:11:36.347Z  INFO 1 --- [bookstore] [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1398 ms
mongodb-1  | {"t":{"$date":"2025-02-24T17:11:36.602+00:00"},"s":"I",  "c":"NETWORK",  "id":22943,   "ctx":"listener","msg":"Connection accepted","attr":{"remote":"172.18.0.3:48832","uuid":{"uuid":{"$uuid":"c5557c00-2df4-4dfb-abb6-8b97440d6989"}},"connectionId":1,"connectionCount":1}}
```

## Exploring Book Store via Postman

This app can be tested by using [this API collection](https://github.com/danilo-ambrosio/bookstore/blob/2f4d427e2ec24ac8bd79cd0c6689c723f4227826/assets/postman/book-store.postman_collection.json) which can be directly imported to Postman. Further info on API schemas can be found in this [Open API file](https://editor.swagger.io/?raw=https://raw.githubusercontent.com/danilo-ambrosio/bookstore/refs/heads/master/assets/api_doc.json).  

### Registration/Authentication

Having the collection in your workspace, the first step is to edit user's credentials (username and password) in the collection variables panel:

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/01-step-credentials.png" alt="drawing" />

There's no validation rule for username and password, any text is acceptable. So once it's set, a new user can be registered as follows:

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/02-step-registration.png" alt="drawing" />

Changing the payload is not needed as Postman will inject `username` and `password` variables. After signing up, it's required to authenticate as 
some endpoints requires a logged user.

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/03-step-authorization.png" style="margin: -40px 0px 0px -120px;" alt="drawing" />

> The current version of the app actually emulates an authentication process just by getting userId in the header 

Once more, 