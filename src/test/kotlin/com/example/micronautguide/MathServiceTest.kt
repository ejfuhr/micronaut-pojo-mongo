package com.example.micronautguide

import io.micronaut.context.annotation.Requires
import io.micronaut.core.util.StringUtils
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*


@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Requires( defaultValue = StringUtils.TRUE, value = StringUtils.TRUE)
class MathServiceTest {

    @Inject
    var mathService: MathService? = null


    @ParameterizedTest(name="do stuff")
    @CsvSource("2,8", "3,12")
    fun dothis(num:Int, square:Int){
        val result = mathService?.compute(num)
        assertEquals(
            square, result
        )
    }


}

interface MathService{
    fun compute(num: Int?): Int?
}

@Singleton
class MathServiceImpl : MathService {
    override fun compute(num: Int?): Int? {
        return num!! * 4
    }
}
