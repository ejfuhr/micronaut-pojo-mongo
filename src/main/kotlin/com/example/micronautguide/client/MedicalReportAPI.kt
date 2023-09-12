package com.example.micronautguide.client

import com.example.micronautguide.pojo.Doctor
import com.example.micronautguide.pojo.MedicalReport
import com.example.micronautguide.pojo.Patient
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface MedicalReportAPI {
    @Post("/save5Doctors")
    open fun save5Doctors(): Flux<Doctor>

    @Post("/save5Patients")
    open fun save5Paients(): Flux<Patient>

    @Delete("/delete5Doctors")
    open fun delete5Doctors(): Mono<Long>

    @Delete("/delete5Patients")
    open fun delete5Patients(): Mono<Long>

    @Get("/allDoctors")
    fun getAllDoctors(): Flux<Doctor>

    @Get("/allPatients")
    fun getAllPatients(): Flux<Patient>

    @Post("/newPatientIdAndName/{patientId}/{name}/newSymptomId/{sympId}/{symptomsArray}/doctorId/{docId}")
    open fun saveNewPatientWithNewDoctor(
        @PathVariable patientId: String,
        @PathVariable name: String,
        @PathVariable sympId: String,
        @PathVariable symptomsArray: Array<String>,
        @PathVariable docId: String
    ): Flux<Doctor>

    @Post(
        "/newDocIdName/{docId}/{doctorName}/patientIdName/{patientId}/{name}/newSymptomId/{sympId}/{symptomsArray}/" +
                "medReportId/{medReportId}"
    )
    open fun saveAllWithMedicalReport(
        @PathVariable docId: String,
        @PathVariable doctorName: String,
        @PathVariable patientId: String,
        @PathVariable name: String,
        @PathVariable sympId: String,
        @PathVariable symptomsArray: Array<String>,
        @PathVariable medReportId: String
    ): Flux<MedicalReport>
}