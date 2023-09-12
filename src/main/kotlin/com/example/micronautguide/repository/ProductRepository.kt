package com.example.micronautguide.repository

import com.example.micronautguide.pojo.Product
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.annotation.Join
import io.micronaut.data.mongodb.annotation.MongoFindQuery
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.reactive.ReactorCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@MongoRepository
interface ProductRepository: ReactorCrudRepository<Product, String> {

    @Join("manufacturer") // (1)
    fun list(): Flux<Product>

    @NonNull
    fun findByName(name: String?): Mono<Product>

    fun findByProductId(productId: String?):Mono<Product>

    @MongoFindQuery("{ name: { \$regex: :name, '\$options' : 'i'}}")
    fun findByNameLikeCaseInsensitive(name: String): Flux<Product>

    fun deleteByProductId(productId: String?): Mono<Long?>

    fun deleteByName(name: String?): Mono<Long?>
}