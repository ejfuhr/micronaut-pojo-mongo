package com.example.micronautguide.repository

import com.example.micronautguide.pojo.Patient
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Mono
@MongoRepository
interface  PatientRepository : ReactorCrudRepository<Patient, String> {

    fun findByPatId(patId:String): Mono<Patient>
}

