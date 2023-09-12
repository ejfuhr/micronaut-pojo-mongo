package com.example.micronautguide

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ParamCsvDisplayTest {

    //@Disabled
    @DisplayName("Calculates the sum of:")
    @ParameterizedTest(name = "{index} => {0}: ({1}, {2})")
    @CsvSource(
        delimiter = '|', textBlock = """
positive numbers        |   10  |      6    |   16
positive and negative   |   -4  |      2    |   -2
negative numbers        |   -6  |   -100    | -106
whats this              |   1   |   2       | 3
"""
    )
    fun calculatesSum(description: String?, a: Int, b: Int, expectedSum: Int) {
        val calculator:Calculator = Calculator()
        val actual: Int = calculator.sum(a, b)
        assertEquals(expectedSum, actual)
    }

    @ParameterizedTest
    @CsvSource(
        textBlock = """ 
            hello world, 11 
            JUnit 5,      7       
"""
    )
    fun calculatesPhraseLength(phrase: String, expectedLength: Int) {
        assertEquals(expectedLength, phrase.length)
    }
}

@Singleton
private class Calculator {
    fun sum(num: Int, num2:Int): Int {
                return num + num2!!
    }
}
