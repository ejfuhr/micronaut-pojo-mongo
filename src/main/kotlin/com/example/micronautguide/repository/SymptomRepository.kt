package com.example.micronautguide.repository

import com.example.micronautguide.pojo.Symptom
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Mono
@MongoRepository
interface SymptomRepository: ReactorCrudRepository<Symptom, String> {

    fun findBySympId(sympId:String): Mono<Symptom>
}