package com.example.micronautguide.pojo

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.Relation

@MappedEntity
data class Product(
                   @Id @GeneratedValue
                   var id: String? = null,
                   var productId:String? = "101",
                   var name: String,
                   @Relation(Relation.Kind.MANY_TO_ONE)
                   var manufacturer: Manufacturer?) {

    constructor(productId: String?,name: String,  manufacturer: Manufacturer?) :
            this(null, productId, name, manufacturer)

}