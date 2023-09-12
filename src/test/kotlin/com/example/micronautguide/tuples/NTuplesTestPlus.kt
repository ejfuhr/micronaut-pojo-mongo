package com.example.micronautguide.tuples

import com.example.micronautguide.reactive.utils.NTuple4
import com.example.micronautguide.reactive.utils.then
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.core.ValueClassSupport.boxedValue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.*
import reactor.kotlin.test.test

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NTuplesTestPlus {

    private val log: Logger = LoggerFactory.getLogger(NTuplesTestPlus::class.java)

    @Test
    fun `test ntuples one`(){
        val nTuple4 = 1 then 2 then "foo" then "bar"
        val boxed = nTuple4.boxedValue
        assertNotNull(boxed)
        boxed.then(22)
        assertFalse(nTuple4.t2 == 22)

        log.info("boxed, the is ${nTuple4.t2} or ${boxed.then(22)}")
    }

    @Test
    fun `test NTuple Stuff with toMono`(){
        val nTuple4 = 1 then 2 then "foo" then "bar"
        log.debug("nTuple4-1 is ${nTuple4.t1} and nTuple4-4 is ${nTuple4.t4}")
        assertEquals("bar", nTuple4.t4)
        val nTuple4mono: Mono<NTuple4<Int, Int, String, String>> = nTuple4.toMono()

        nTuple4mono
            .test()
            .assertNext { n ->
                assertTrue(n.t1 ==1)
                assertTrue(n.t2 == 2)
            }
            .verifyComplete()

    }


}