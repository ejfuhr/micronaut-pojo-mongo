package com.example.micronautguide.bergamo.math

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream


@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Requires( defaultValue = StringUtils.TRUE, value = StringUtils.TRUE)
class MultiplyArgumentsTest {

    @ParameterizedTest
    @MethodSource("multiplicationArguments")
    fun `given two numbers, should return their product`(a: Int, b: Int, expectedProduct: Int) {
        // When
        val result = multiply(a, b)

        // Then
        assertEquals(expectedProduct, result)
    }

    companion object {
        @JvmStatic
        fun multiplicationArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.of(2, 3, 6),
                Arguments.of(5, 4, 20),
                Arguments.of(0, 8, 0),
                Arguments.of(-3, 2, -6),
                Arguments.of(-3, -3, 9)
            )
    }
}

fun multiply(a: Int, b: Int): Int = a * b