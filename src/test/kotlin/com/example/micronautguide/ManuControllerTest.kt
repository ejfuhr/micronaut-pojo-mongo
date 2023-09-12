package com.example.micronautguide

import com.example.micronautguide.client.ManuProductClient
import com.example.micronautguide.pojo.Manufacturer
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import reactor.test.StepVerifier

//environments = ["mock"],
@MicronautTest( transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ManuControllerTest {

    private val log: Logger = LoggerFactory.getLogger(ManuControllerTest::class.java)

    @Inject
    lateinit var prodClient: ManuProductClient

    private fun fiveManufacturers(): MutableList<Manufacturer> {
        return mutableListOf(
            Manufacturer(null, "a101", "abey-works"),
            Manufacturer(null, "b101", "buzz-works"),
            Manufacturer(null, "c101", "crud-works"),
            Manufacturer(null, "d101", "dud-works"),
            Manufacturer(null, "e101", "egg-works")
        )
    }

    @BeforeAll
    fun startup(){

        StepVerifier.create(prodClient.deleteAllManufacturers())
            .assertNext { 1 > 0 }

            .verifyComplete()

        var num = 0
        val makers = fiveManufacturers()
        var monoSave = Mono.empty<Manufacturer>()
        var fluxSave = Flux.empty<Manufacturer>()
        for(manu in makers) {
            //fluxSave = monoSave.concatWith(prodClient.saveMaker(manu.manufacturerId!!, manu.name))
            //fluxSave.concatWith(prodClient.saveMaker(manu.manufacturerId!!, manu.name))
            monoSave = prodClient.saveMaker(manu.manufacturerId!!, manu.name)
            testMonoManu(monoSave, num)
            num++
        }
        println("NUMBER $num")
//        fluxSave
//            .test()
//            .expectNext(fiveManufacturers().get(0))
//            .verifyComplete()
    }

    fun testMonoManu(m:Mono<Manufacturer>, n:Int){
        m.test()
            .assertNext { m -> assertNotNull(m.id) }
            .verifyComplete()
        return
    }

    @Test
    fun testNullness(){
        assertNotNull("b")
    }
    //@Disabled
    @Test
    fun `test findByManufacturerId`(){


        val manuMono = prodClient
            .findByManufacturerId("a101")

        StepVerifier.create(manuMono)
            .assertNext { m ->
                assertNotNull(m.id)
                assertTrue(m.manufacturerId == "a101")
            }
            .verifyComplete()

    }
}