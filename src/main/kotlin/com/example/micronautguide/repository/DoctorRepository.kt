package com.example.micronautguide.repository

import com.example.micronautguide.pojo.Doctor
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Mono
@MongoRepository
interface DoctorRepository : ReactorCrudRepository<Doctor, String> {
    fun findByDocId(docId:String): Mono<Doctor>
}