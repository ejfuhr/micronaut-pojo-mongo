package com.example.micronautguide.controller

import com.example.micronautguide.client.ManuProductAPI
import com.example.micronautguide.pojo.Manufacturer
import com.example.micronautguide.pojo.Product
import com.example.micronautguide.repository.ManufacturerRepository
import com.example.micronautguide.repository.ProductRepository
import io.micronaut.http.annotation.*
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import java.util.concurrent.atomic.AtomicLong

@Controller("/myStuff")
@ExecuteOn(TaskExecutors.IO)
open class ManuProductController(
    private val manuRepo: ManufacturerRepository,
    private val productRepo: ProductRepository
) : ManuProductAPI {


    @Get("/sink/maker/{manufacturerId}/product/{productId}")
    override fun sinkProductToMaker(@PathVariable manufacturerId: String,
                                    @PathVariable productId: String):Mono<Manufacturer>{


        return Mono.empty()
    }

    override fun doSink():Flux<String?>{
        return Flux.generate(
            { AtomicLong() },
            { state: AtomicLong, sink: SynchronousSink<String?> ->
                val i = state.getAndIncrement()
                sink.next("3 x " + i + " = " + 3 * i)
                if (i == 10L) sink.complete()
                state
            }
        ) { state: AtomicLong -> println("state: $state") }
    }

    @Get("/allMakers")
    override fun getAllManufacturers():Flux<Manufacturer> =
        manuRepo.findAll()

    @Get("/maker/{manufacturerId}")
    override fun findByManufacturerId(@PathVariable manufacturerId: String):Mono<Manufacturer> =
        manuRepo.findByManufacturerId(manufacturerId)

    @Get("/maker/name/{name}")
    override fun findMakerByName(@PathVariable name: String):Mono<Manufacturer> =
        manuRepo.findByName(name)

    @Post("/maker/{manufacturerId}/{name}")
    override fun saveMaker(@PathVariable manufacturerId:String,
                           @PathVariable name:String):Mono<Manufacturer>{
        val m:Manufacturer = Manufacturer(null, manufacturerId, name)
        val makerMono = manuRepo.save(m)
        return makerMono
    }

    @Put("/maker/{manufacturerId}")
    override fun updateManufacturer(@PathVariable manufacturerId:String,
                                    @PathVariable name:String):Mono<Manufacturer>{
        val updatedMaker:Manufacturer = Manufacturer(null, manufacturerId, name)
        return manuRepo.findByManufacturerId(manufacturerId)
            .map { oldMaker ->
                updatedMaker.copy(id = oldMaker.id)
            }
            .flatMap { manuRepo.save(updatedMaker) }
    }

    @Delete("/maker/delete/{manufacturerId}")
    override fun deleteByManufacturerId(@PathVariable manufacturerId:String):Mono<Long?> =
        manuRepo.deleteByManufacturerId(manufacturerId)

    @Delete("/maker/deleteAll")
    override fun deleteAllManufacturers(): Mono<Long> {
        return manuRepo.deleteAll()
    }

    //Products below here

    @Get("/allProducts")
    override fun getAllProducts():Flux<Product> =
        productRepo.findAll()

    @Get("/product/{productId}")
    override fun findByProducetId(@PathVariable productId: String):Mono<Product> =
        productRepo.findByProductId(productId)

    @Get("/product/name/{name}")
    override fun findProductByName(@PathVariable name: String):Mono<Product> =
        productRepo.findByName(name)

    @Post("/product/{productId}/{name}/{manufacturerId}")
    override fun saveProduct(@PathVariable productId:String,
                             @PathVariable name:String,
                             @PathVariable manufacturerId:String
                    ):Mono<Product>{

        var makerMono = manuRepo.findByManufacturerId(manufacturerId)
        //create new Product
        val newProduct:Product = Product(productId, name, null)
        //save it
        var productMono = productRepo.save(newProduct)
        //zip it
        return productMono
            .zipWith(makerMono)
            .map {
                var product = it.t1
                var maker = it.t2
                product = newProduct.copy(id = product.id, manufacturer = maker)
                manuRepo.update(maker)
            }
            .flatMap { productRepo.save(newProduct) }
            //.flatMap {}

        //return newProduct
    }

    @Put("/product/{productId}")
    override fun updateProduct(@PathVariable productId:String,
                               @PathVariable name:String):Mono<Product>{
        val updatedProduct:Product = Product( productId, name, null)
        return productRepo.findByProductId(productId)
            .map { oldProduct ->
                updatedProduct.copy(id = oldProduct.id,
                    manufacturer = oldProduct.manufacturer
                    )
            }
            .flatMap { productRepo.save(updatedProduct) }
    }

    @Delete("/product/delete/{productId}")
    override fun deleteByProductId(@PathVariable productId:String):Mono<Long?> =
        productRepo.deleteByProductId(productId)

    @Delete("/product/deleteAll")
    override fun deleteAllProducts(): Mono<Long> {
        return productRepo.deleteAll()
    }
}