# Online Book Store

Backend for an online book store app supporting features as inventory management, purchase/pricing and loyalty program.

### Development guideline
Here's a list of software techniques and principles adopted in this backend implementation:
- DDD (Strategic/Tactical patterns): Ubiquitous Language (tried to use nouns and verbs found in the home assignment doc), Aggregate, Value Objects, Domain Events
- Object-oriented Design: encapsulation, inheritance, abstraction, high cohesion/low coupling...
- Clean architecture: separation of concerns, inward dependency sense only, framework independence
- Rest API: stateless, client/server architecture, unique resource identification...
- Asynchronous operations: non-blocking propagation of domain events, supporting eventual consistency and better definition of transaction boundaries for aggregates
- TDD: mostly implemented using the test-first approach

### Worklog

[worklong](https://github.com/danilo-ambrosio/bookstore/blob/master/worklog.md) contains detailed information on my thought process during the project implementation. 

### Framework and tools
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot/) (data, core, web)
- [Mongo DB](https://www.mongodb.com/)
- [Maven](https://maven.apache.org) 
- [Docker](https://www.docker.com/)

### Requirements

- All infrastructure dependencies are managed by Docker, so it's mandatory to install such tool and keep it up and running. Integration tests also rely on containers.

### Out of Scope

Just a short list of things I couldn't implement due to time constraints:
- Thorough and more secure authentication/authorization
- Monitoring (logging, meaningful exception messages) 
- Better management of asynchronous components, eventual consistency (DomainEvents pub/sub)
- Database optimization

### How to run the app

The following command will compile, package and run the app on *localhost:8080*:

```
docker compose up --build
```

In addition, a container for MongoDB will also be up on port 27017.

