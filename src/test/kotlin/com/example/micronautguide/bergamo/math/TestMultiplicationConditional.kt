package com.example.micronautguide.bergamo.math

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.*
import org.junit.jupiter.api.condition.EnabledIf
import org.junit.jupiter.api.condition.EnabledOnOs
import org.junit.jupiter.api.condition.OS

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestMultiplicationConditional {
    private val five = Dollar(5)

    @BeforeAll
    fun init() {
        println("Hello I'm the global initializer for the conditional test suite.")
    }

    @BeforeEach
    fun initEach() {
        println("I run before each conditional test :)")
    }

    @AfterAll
    fun cleanupEach() {
        println("Bye now!")
    }

    @EnabledIf("execAlways")
    @Test
    fun `should multiply correctly`() {
        Assertions.assertEquals(10, five.times(2).amount)
    }

    @EnabledIf("execMaybe")
    @Test
    fun `should sometimes multiply correctly`() {
        Assertions.assertEquals(10, five.times(2).amount)
    }

    @EnabledOnOs(OS.MAC, OS.LINUX, OS.WINDOWS)
    @Test
    fun `should multiply correctly on *nix systems`() {
        Assertions.assertEquals(10, five.times(2).amount)
    }


    private fun execAlways(): Boolean {
        return true
    }

    private fun execMaybe(): Boolean {
        return (1..12).shuffled().first() % 2 == 0
    }
}
