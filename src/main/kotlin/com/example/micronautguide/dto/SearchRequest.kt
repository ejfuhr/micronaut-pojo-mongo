package com.example.micronautguide.dto

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


@Introspected
@Serdeable.Deserializable
data class SearchRequest(
    @field:NotBlank
    @field:Size(max = 50)
    val title: String
)