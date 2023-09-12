package com.example.micronautguide.dto

import com.example.micronautguide.pojo.Doctor
import com.example.micronautguide.pojo.Symptom
import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import kotlin.random.Random

@Introspected
@Serdeable.Serializable
@Serdeable.Deserializable
data class PatientRequest (
    var patId: String? = "p-".plus(Random.nextInt(10, 1001)),
    val name: String,
    var symptoms: MutableList<Symptom> = mutableListOf()
)