# WorkLog

This file documents my thought process, design decisions and implementation progress in chronological order.

## Technical Disclaimer

While defining [the development guideline](https://github.com/danilo-ambrosio/bookstore/blob/master/README.md), I didn't follow an MVP perspective/compressed architecture approach despite 
the few functional requirements and minor essential complexity. Aware of adding excessive design sophistication in the codebase, 
my idea is to show some important elements of my knowledge background, letting Sporty hiring team know a subset of my main technical skills.
Those concepts have been a solid foundation in my career to build successful solutions, working on high performance teams and meeting modern 
software requirements: scalability, responsiveness, maintainability, etc.

### Step 1: Requirements Analysis

First, I carefully read the functional requirements, spending more time with those would require more business logic (e.g. price/discount calculation).
In this phase, I like to draw class or sequence diagrams. Not much for documentation purpose, but mostly as design process helper.
Here's the first domain modeling draft on my whiteboard:

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/grooming/initial-class-diagram.jpg" alt="drawing" height="500"/>

Manual drawing also helps me to roughly foresee architecture decisions and some required building blocks (entities, value objects, tests, controllers, so on). 

<img src="https://github.com/danilo-ambrosio/bookstore/blob/master/assets/grooming/sub-tasks.jpg" alt="drawing" height="500"/>

By knowing that, I can also estimate the effort for each use case/user story. These bullets become fine-grained tasks that provides a more accurate personal progress indicator. 
Self time management gives peace of mind about not being delayed and is easier to do when the time box for each amount of work to be completed is short. The smaller sub tasks are, 
the better to have an accurate progress indicator.

### Step 2: Initial setup

Created project basic structure and added maven configuration. 

### Step 3: Domain Modeling and Unit Tests

Having in mind the rule of inward dependency sense (Clean Architecture) and the primacy of Domain concerns (DDD), I decided to start this project by working on the domain model, more specifically in, what I called, the inventory domain. 
Before implementing any production code, created some test units for the first aggregate [inventory.Book](https://github.com/danilo-ambrosio/bookstore/blob/master/src/main/java/com/sporty/bookstore/domain/model/inventory/Book.java) which 
allowed me to understand what would be the public methods, arguments, return types, etc...
Next, once the test assertions were already in place, I implemented the code to make tests green. At this early implementation step, following what I drew in [this diagram](https://github.com/danilo-ambrosio/bookstore/blob/master/assets/grooming/initial-class-diagram.jpg), 
I noticed something confusing in my first domain class: the purpose of [inventory.Book](https://github.com/danilo-ambrosio/bookstore/blob/master/src/main/java/com/sporty/bookstore/domain/model/inventory/Book.java) was initially to deal with 
inventory operations, but I ended up adding logic related to financial processes. Result: too much responsibility for a class. Reminded me one of the golden rules of DDD: keep your aggregates smaller as much as possible. From experience, I know
that's also good for scalability and responsiveness.

### Step 4: Rethinking domain model and Integration Tests









