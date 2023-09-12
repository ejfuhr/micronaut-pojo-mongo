package com.example.micronautguide.repository

import com.example.micronautguide.pojo.Doctor
import com.example.micronautguide.pojo.MedicalReport
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
@MongoRepository
interface MedicalReportRepository: ReactorCrudRepository<MedicalReport, String> {

    fun findByMedRepId(medRepId:String):Mono<MedicalReport>
    fun findByDoctor(doctor: Doctor): Flux<MedicalReport>
}