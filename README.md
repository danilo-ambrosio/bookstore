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
Further info on this guideline [here](https://github.com/danilo-ambrosio/bookstore/blob/master/worklog.md).

### Framework and tools
- [Spring Boot](https://spring.io/projects/spring-boot/) (data, core, web)
- [Mongo DB](https://www.mongodb.com/)
- [Maven](https://maven.apache.org) 
- [Docker](https://www.docker.com/)

### Infrastructure Requirements

- **This application requires a Mongo DB instance**. Therefore, it's necessary to create an environment variable named *BOOK_STORE_DATABASE_URI* through which the application can connect to the Mongo DB.
- **Integration tests rely on a container-based MongoDB instance**, so make sure Docker is up and running before executing tests.

## Out of Scope
