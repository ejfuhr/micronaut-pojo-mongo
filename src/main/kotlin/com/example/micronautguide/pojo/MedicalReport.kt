package com.example.micronautguide.pojo

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import kotlin.random.Random

@MappedEntity
@Serdeable
data class MedicalReport(
    @Id
    @GeneratedValue
    var id: String?,
    var medRepId: String? = "med-".plus(Random.nextInt(10, 1001)),
    var doctor: Doctor,
    val patients: MutableList<Patient> = mutableListOf(),
)
