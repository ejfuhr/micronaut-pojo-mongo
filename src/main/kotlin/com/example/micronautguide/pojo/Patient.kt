package com.example.micronautguide.pojo

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import kotlin.random.Random

@MappedEntity
@Serdeable
data class Patient(
    @Id
    @GeneratedValue
    var id: String? = null,
    var patId: String? = "p-".plus(Random.nextInt(10, 1001)),
    val name: String,
    var symptoms: MutableList<Symptom> = mutableListOf()
)
