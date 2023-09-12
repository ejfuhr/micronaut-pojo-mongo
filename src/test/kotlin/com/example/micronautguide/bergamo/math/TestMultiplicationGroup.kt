package com.example.micronautguide.bergamo.math

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestMultiplicationGroup {
    @BeforeAll
    fun init() {
        println("Hello I'm the global initializer for the group test")
    }

    @BeforeEach
    fun initEach() {
        println("I run before each group test :)")
    }

    @AfterAll
    fun cleanupEach() {
        println("Bye now!")
    }

    @Test
    fun `should multiply using stream`() {
        val five = Dollar(5)
        val inputs = arrayListOf(
            listOf(2, 10),
            listOf(3, 15),
            listOf(10, 50)
        )
        assertAll(
            "should provide the expected result",
            inputs
                .stream()
                .map {
                    { assertEquals(Dollar(it[1]).amount, five.times(it[0]).amount) }
                }
        )
    }

    @Test
    fun `should multiply using collection`() {
        val five = Dollar(5)
        val inputs = arrayListOf(
            listOf(2, 10),
            listOf(3, 15),
            listOf(10, 50)
        )
        assertAll(
            "should provide the expected result",
            inputs.map {
                { assertEquals(Dollar(it[1]).amount, five.times(it[0]).amount) }
            }
        )
    }

}
