package com.example.micronautguide.dto

import com.example.micronautguide.pojo.Patient
import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.Nullable
import io.micronaut.serde.annotation.Serdeable
import kotlin.random.Random

@Introspected
@Serdeable.Serializable
@Serdeable.Deserializable
data class DoctorRequest (
    var docId:String? = "d-".plus(Random.nextInt(10, 1001)),
    val name: String,
    var patients: MutableList<Patient>  = mutableListOf()
)