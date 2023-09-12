# Using Kotlin, Micronaut, Mongo and Tuples

- This project is a little study of how Kotlin handles Flux and Mono "zipping", or combining two or more objects, exchanging values, and producing a final result.
- Assume we have a list of Doctor, a number of Patients per Doctor, and a whole mess of Symptoms per Patient.
- Oh, add a periodic MedicalReport for a Doctor, Patients with their Symptoms.
- Java can use Reactor's TupleUtils and more, but how can Kotlin combine or zip objects together and product the desired result? The requirements require something more then the Reactor/Kotlin "zipWith" function  
- The MedicalReportController and the accompanying API and Client are very simple. I should add a lot more GETs and PUTs to finish it. 
- But take a look at the cumbersome POSTs attaching a medical Symptom to a Patient to a Doctor to a MedicalReport using Reactor Tuples and Tuples as data classes
## The Kotlin, Reactor and Mongo ideas:
- I'd like to thank Piotr Wolak for more thoroughly teaching me Kotlin, especially high level functions and working with reactive Micronaut. 
- Piotr's writings and classes can be [found here](https://codersee.com/articles).
- Also Dan Lugg and several others wrote how real Tuples can work in Kotlin. It's illustrated an written about pretty extensively on [stackoverflow of all places here](https://stackoverflow.com/questions/46202147/kotlin-quadruple-quintuple-etc-for-destructuring)

 
## Micronaut 4.1.0 Documentation

- [User Guide](https://docs.micronaut.io/4.1.0/guide/index.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Feature test-resources documentation

- [Micronaut Test Resources documentation](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/)

## Feature reactor documentation

- [Micronaut Reactor documentation](https://micronaut-projects.github.io/micronaut-reactor/snapshot/guide/index.html)


## Feature data-mongodb-reactive documentation

- [Micronaut Data MongoDB Reactive documentation](https://micronaut-projects.github.io/micronaut-data/latest/guide/#mongo)

- [https://docs.mongodb.com](https://docs.mongodb.com)


## Feature mongo-reactive documentation

- [Micronaut MongoDB Reactive Driver documentation](https://micronaut-projects.github.io/micronaut-mongodb/latest/guide/index.html)

- [https://docs.mongodb.com](https://docs.mongodb.com)





