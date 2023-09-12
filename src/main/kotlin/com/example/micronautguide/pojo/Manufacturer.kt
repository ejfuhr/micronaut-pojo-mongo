package com.example.micronautguide.pojo

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity


@MappedEntity
data class Manufacturer(
    @Id
    @GeneratedValue
    var id: String?= null,
    var manufacturerId: String?= "m-101",
    val name: String
)