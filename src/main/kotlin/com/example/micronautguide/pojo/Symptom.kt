package com.example.micronautguide.pojo

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@MappedEntity
@Serdeable
data class Symptom(
    @Id
    @GeneratedValue
    var id: String? = null,
    var sympId: String? = "s-".plus(LocalDateTime.now()),
    val descriptionList: MutableList<String> = mutableListOf()
)
