package com.example.micronautguide.bergamo.math

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.*
import java.util.stream.Stream

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestMultiplicationParameterized {
    @BeforeAll
    fun init() {
        println("Hello I'm the global initializer for the parameterized tests")
    }

    @BeforeEach
    fun initEach() {
        println("I run before each parameterized test :)")
    }

    @AfterAll
    fun cleanupEach() {
        println("Bye now!")
    }

    @ParameterizedTest(name = "should multiply by {0}")
    @ValueSource(ints = [3, 5, 15])
    fun `should multiply with Value Source`(multiplier: Int) {
        assertNotNull(Dollar(5).times(multiplier))
    }

    @ParameterizedTest(name = "multiply {0} by 5 should return {1}")
    @CsvSource(
        "2, 10",
        "3, 15",
        "10, 50",
    )
    fun `should multiply with CSV Source`(multiplier: Int, expected: Int) {
        val five = Dollar(5)
        assertEquals(Dollar(expected).amount, five.times(multiplier).amount)
    }

    @ParameterizedTest(name = "multiply {0} by 5 should return {1}")
    @CsvFileSource(resources = ["/multipliers.csv"], numLinesToSkip = 1, useHeadersInDisplayName = false)
    fun `should multiply with CSV File Source`(multiplier: Int, expected: Int) {
        val five = Dollar(5)
        assertEquals(Dollar(expected).amount, five.times(multiplier).amount)
    }

    @ParameterizedTest(name = "Enum value: {0}")
    @EnumSource(ChronoUnit::class)
    fun `should multiply using ENUM source correctly`(unit: TemporalUnit?) {
        assertNotNull(unit)
    }


    @ParameterizedTest(name = "Enum value: {0}")
    @EnumSource(names = ["DAYS", "HOURS"])
    fun `should multiply using filtered ENUM source correctly`(unit: ChronoUnit) {
        assertTrue(EnumSet.of(ChronoUnit.DAYS, ChronoUnit.HOURS).contains(unit))
    }

    private fun testSetGenerator(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(TestSet(2, 10)),
            Arguments.of(TestSet(3, 15)),
            Arguments.of(TestSet(10, 50)),
        )
    }

    @ParameterizedTest(name = "should multiply {0} and {1} correctly")
    @MethodSource("testSetGenerator")
    fun `should multiply using method source correctly`(testSet: TestSet) {
        val five = Dollar(5)
        assertEquals(Dollar(testSet.expected).amount, five.times(testSet.multiplier).amount)
    }

    data class TestSet(val multiplier: Int, val expected: Int)

    @ParameterizedTest(name = "Steph builds Argument provider ya quoi! {0}")
    @ArgumentsSource(DollarProvider::class)
    fun `should multiply using argument source correctly`(argument: Dollar?) {
        assertNotNull(argument)
    }

    class DollarProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(Dollar(1), Dollar(2), Dollar(3)).map(Arguments::of)
        }
    }


}
