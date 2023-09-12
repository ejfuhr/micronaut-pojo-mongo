package com.example.micronautguide.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Introspected
@Serdeable.Serializable
@Serdeable.Deserializable
data class SymptomRequest(
    var sympId: String? = "s-".plus(LocalDateTime.now()),
    val descriptionList: MutableList<String> = mutableListOf()
)
