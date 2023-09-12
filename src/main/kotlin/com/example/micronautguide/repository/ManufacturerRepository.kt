package com.example.micronautguide.repository

import com.example.micronautguide.pojo.Manufacturer
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.mongodb.annotation.MongoFindQuery
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@MongoRepository
interface ManufacturerRepository : ReactorCrudRepository<Manufacturer, String> {

    @NonNull
    fun findByName(name: String?): Mono<Manufacturer>

    fun findByManufacturerId(manufacturertId: String?):Mono<Manufacturer>

    @MongoFindQuery("{ name: { \$regex: :name, '\$options' : 'i'}}")
    fun findByNameLikeCaseInsensitive(name: String): Flux<Manufacturer>

    fun deleteByManufacturerId(manufacturerId: String?): Mono<Long?>

    fun deleteByName(name: String?): Mono<Long?>
}