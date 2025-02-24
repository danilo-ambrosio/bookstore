

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

## Exploring Book Store features via Postman

This app can be tested by using [this API collection](https://github.com/danilo-ambrosio/bookstore/blob/2f4d427e2ec24ac8bd79cd0c6689c723f4227826/assets/postman/book-store.postman_collection.json) which can be directly imported to Postman. Further info on API schemas can be found in this [Open API file](https://editor.swagger.io/?raw=https://raw.githubusercontent.com/danilo-ambrosio/bookstore/refs/heads/master/assets/api_doc.json).  

### Registration/Authentication

Having the collection in your workspace, the first step is to edit user's credentials (username and password) in the collection variables panel:

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/01-step-credentials.png" alt="credentials" />

There's no validation rule for username and password, any text is acceptable. So once it's set, a new user can be registered and authenticated as follows:

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/02-step-registration.png" alt="registration" />

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/03-step-authorization.png" alt="authentication" />

> The current version of the app actually emulates an authentication process just by ensuring userId is in the header 

Changing the payload/request param is not needed as Postman will inject `username` and `password` variables. After the user is successfully logged in, the `userId` header param is
automatically set by a post-response script in Postman.

### Book Inventory

One of the assumptions to perform any operation in the Book Store is to have inventoried books. It's recommended to add at least three books with a reasonable stock quantity as shown below so, later, there will be enough data to test different payment methods, discount policies and loyalty points accumulation.

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/04-step-inventory-books.png" alt="inventory-books" />

To speed up the process of saving/referencing the added books, let's also consume the `inventoried books` list endpoint. By doing that, Postman will automatically save every bookId in the collection variables storage.

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/05-step-inventoried-books-list.png" alt="inventoried-books" />

> Available inventoried books (i.e. stock quantity greater than 0) can be filtered by setting a request param `?available=true`
 
### Book Pricing

Next, retail price has to be defined along with a discount policy/book type (`NEW_RELEASE`, `OLD_EDITION` and `REGULAR_EDITION`). The pricing process is made for each book so make sure you will update the collection variable (e.g `bookId1`, `bookId2`, so on) in the params before submitting the request.

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/06-step-book-pricing.png" alt="book-pricing" />

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/07-step-book-pricing-payload.png" alt="book-pricing-payload" />

Once pricing is done, you can optionally call the discount calculation endpoint, setting `quantity` and `useLoyaltyPoints` request parameters, just to see how the policy affects the price.

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/08-step-book-discount-calculation.png" alt="book-discount" />

> This is a read-only endpoint to demonstrate how discount policies are applied. Retail price won't be changed after the discount calculation.

### Purchase

Assuming there are at least 3 inventoried/priced books, a purchase request only demands payment method and quantity. The side effect of this operation is to accumulate loyalty points for the logged `userId`.
This field will be retrieved on the backend as Postman automatically saved its value in the authentication step and also sends in the request header.

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/09-step-purchase.png" alt="book-discount" />

> Other consequence of purchasing books is to decrease stock quantity in the book inventory. 

### Loyalty Points

Purchased books mean loyalty points. If the previous step succeeded, let's have a look at how many loyalty points were added:

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/postman/10-step-loyalty.png" alt="book-discount" />