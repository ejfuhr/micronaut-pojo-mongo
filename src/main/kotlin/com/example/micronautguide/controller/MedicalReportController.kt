package com.example.micronautguide.controller

import com.example.micronautguide.client.MedicalReportAPI
import com.example.micronautguide.pojo.Doctor
import com.example.micronautguide.pojo.MedicalReport
import com.example.micronautguide.pojo.Patient
import com.example.micronautguide.pojo.Symptom
import com.example.micronautguide.reactive.utils.NTuple4
import com.example.micronautguide.reactive.utils.then
import com.example.micronautguide.repository.DoctorRepository
import com.example.micronautguide.repository.MedicalReportRepository
import com.example.micronautguide.repository.PatientRepository
import com.example.micronautguide.repository.SymptomRepository
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.zip
import reactor.core.publisher.Mono

@Controller("/medical")
@ExecuteOn(TaskExecutors.IO)
open class MedicalReportController(
    private val doctorRepo: DoctorRepository,
    private val patientRepo: PatientRepository,
    private val symptomRepo: SymptomRepository,
    private val medReportRepo: MedicalReportRepository
) : MedicalReportAPI {

    private val docList = mutableListOf<Doctor>(

        Doctor(null, "d101", "Dr Charlie", mutableListOf()),
        Doctor(null, "d102", "Dr Dan", mutableListOf()),
        Doctor(null, "d103", "Dr Edward", mutableListOf()),
        Doctor(null, "d104", "Dr Frank", mutableListOf()),
        Doctor(null, "d105", "Dr Gary", mutableListOf())
    )

    private val patList = mutableListOf(
        Patient(null, "p101", "Adam", mutableListOf()),
        Patient(null, "p102", "Barb", mutableListOf()),
        Patient(null, "p103", "Chuckie", mutableListOf()),
        Patient(null, "p104", "Darlene", mutableListOf()),
        Patient(null, "p105", "Edgar", mutableListOf())
    )

    @Post("/save5Doctors")
    override fun save5Doctors(): Flux<Doctor> {
        return doctorRepo.saveAll(docList)
    }

    @Post("/save5Patients")
    override fun save5Paients(): Flux<Patient> {
        return patientRepo.saveAll(patList)
    }

    @Delete("/delete5Doctors")
    override fun delete5Doctors(): Mono<Long> {
        return doctorRepo.deleteAll(docList)
    }

    @Delete("/delete5Patients")
    override fun delete5Patients(): Mono<Long> {
        return patientRepo.deleteAll(patList)
    }

    @Get("/allDoctors")
    override fun getAllDoctors(): Flux<Doctor> =
        doctorRepo.findAll()

    @Get("/allPatients")
    override fun getAllPatients(): Flux<Patient> =
        patientRepo.findAll()

    @Post("/newPatientIdAndName/{patientId}/{name}/newSymptomId/{sympId}/{symptomsArray}/doctorId/{docId}")
    override fun saveNewPatientWithNewDoctor(
        @PathVariable patientId:String,
        @PathVariable name:String,
        @PathVariable sympId:String,
        @PathVariable symptomsArray:Array<String>,
    @PathVariable docId:String):Flux<Doctor> {
        // build symptoms, patient, doctor
        // save the symptoms patient doctor
        val symp:Symptom = Symptom(null, sympId, symptomsArray.toMutableList())
        val patient:Patient = Patient(null, patientId, name, symptoms = mutableListOf(symp) )
        val sympMono:Mono<Symptom> = symptomRepo.save(symp)
        val patientMono:Mono<Patient> = patientRepo.save(patient)
        val doctorMono:Mono<Doctor> = doctorRepo.findByDocId(docId)

        //zip up
        val zipDoctor:Flux<Doctor> = zip(sympMono, patientMono, doctorMono)
            .map {
                val s = it.t1
                val p = it.t2
                val d = it.t3
                s.descriptionList.add("IT DEPT adding a symptom") // hack in a symptom
                p.symptoms.add(s) // add symptom to patient
                d.patients.add(p) // add patient to doctor
                d
            }
        return zipDoctor

    }

    @Post("/newDocIdName/{docId}/{doctorName}/patientIdName/{patientId}/{name}/newSymptomId/{sympId}/{symptomsArray}/" +
            "medReportId/{medReportId}")
    override fun saveAllWithMedicalReport(
        @PathVariable docId:String,
        @PathVariable doctorName:String,
        @PathVariable patientId:String,
        @PathVariable name:String,
        @PathVariable sympId:String,
        @PathVariable symptomsArray:Array<String>,
        @PathVariable medReportId:String
        ):Flux<MedicalReport> {

        val symp:Symptom = Symptom(null, sympId, symptomsArray.toMutableList())
        val patient:Patient = Patient(null, patientId, name, symptoms = mutableListOf(symp) )
        val doctor:Doctor = Doctor(null, docId, doctorName, patients = mutableListOf(patient))
        val medReport:MedicalReport = MedicalReport(null, medReportId, doctor, patients = doctor.patients)
        val ntuple4 = symp then patient then doctor then medReport

        val ntuple4Flux:Flux<NTuple4<Symptom, Patient, Doctor, MedicalReport>> = Flux.just(ntuple4)

        return ntuple4Flux
            .map {
                val s: Symptom = it.t1
                val p: Patient = it.t2
                val d: Doctor = it.t3
                val m: MedicalReport = it.t4
                s.descriptionList.add("IT TEAM adding an NTuple symptom here")
                m
            }
            .flatMap {  symptomRepo.save(ntuple4.t1)      }
            .flatMap { patientRepo.save(ntuple4.t2) }
            .flatMap { doctorRepo.save(ntuple4.t3) }
            .flatMap { medReportRepo.save(ntuple4.t4)}


    //return Flux.empty()
    }
}

