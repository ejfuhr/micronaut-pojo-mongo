package com.example.micronautguide.client

import com.example.micronautguide.pojo.Manufacturer
import com.example.micronautguide.pojo.Product
import io.micronaut.http.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ManuProductAPI {
    @Get("/sink/maker/{manufacturerId}/product/{productId}")
    fun sinkProductToMaker(
        @PathVariable manufacturerId: String,
        @PathVariable productId: String
    ): Mono<Manufacturer>

    fun doSink(): Flux<String?>

    @Get("/allMakers")
    fun getAllManufacturers(): Flux<Manufacturer>

    @Get("/maker/{manufacturerId}")
    fun findByManufacturerId(@PathVariable manufacturerId: String): Mono<Manufacturer>

    @Get("/maker/name/{name}")
    fun findMakerByName(@PathVariable name: String): Mono<Manufacturer>

    @Post("/maker/{manufacturerId}/{name}")
    open fun saveMaker(
        @PathVariable manufacturerId: String,
        @PathVariable name: String
    ): Mono<Manufacturer>

    @Put("/maker/{manufacturerId}")
    open fun updateManufacturer(
        @PathVariable manufacturerId: String,
        @PathVariable name: String
    ): Mono<Manufacturer>

    @Delete("/maker/delete/{manufacturerId}")
    open fun deleteByManufacturerId(@PathVariable manufacturerId: String): Mono<Long?>

    @Delete("/maker/deleteAll")
    open fun deleteAllManufacturers():Mono<Long>

    @Get("/allProducts")
    fun getAllProducts(): Flux<Product>

    @Get("/product/{productId}")
    fun findByProducetId(@PathVariable productId: String): Mono<Product>

    @Get("/product/name/{name}")
    fun findProductByName(@PathVariable name: String): Mono<Product>

    @Post("/product/{productId}/{name}/{manufacturerId}")
    open fun saveProduct(
        @PathVariable productId: String,
        @PathVariable name: String,
        @PathVariable manufacturerId: String
    ): Mono<Product>

    @Put("/product/{productId}")
    open fun updateProduct(
        @PathVariable productId: String,
        @PathVariable name: String
    ): Mono<Product>

    @Delete("/product/delete/{productId}")
    open fun deleteByProductId(@PathVariable productId: String): Mono<Long?>
    @Delete("/product/deleteAll")
    open fun deleteAllProducts():Mono<Long>
}