

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

### API

Schemas and endpoints are documented in this [Open API file](https://editor.swagger.io/?raw=https://raw.githubusercontent.com/danilo-ambrosio/bookstore/refs/heads/master/assets/api_doc.json).

### Exploring Book Store via Postman

This app can be tested by using [this API collection](https://github.com/danilo-ambrosio/bookstore/blob/2f4d427e2ec24ac8bd79cd0c6689c723f4227826/assets/postman/book-store.postman_collection.json) which can be directly imported to Postman. 

Having the collection in your workspace, the first step is to edit user's credentials in the collection variables panel:

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/01-step-credentials" alt="drawing" />
