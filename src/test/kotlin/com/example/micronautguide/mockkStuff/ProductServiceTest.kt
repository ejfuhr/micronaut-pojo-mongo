package com.example.micronautguide.mockkStuff


import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

interface PriceCalculator {
    fun calculatePrice(productId: Int): Double
}

class ProductService(private val priceCalculator: PriceCalculator) {
    fun getTotalPrice(productIds: List<Int>): Double {
        var totalPrice = 0.0
        for (productId in productIds) {
            val price = priceCalculator.calculatePrice(productId)
            totalPrice += price
        }
        return totalPrice
    }
}

@MicronautTest
class ProductServiceTest {

    private val priceCalculator: PriceCalculator = mockk()
    private val productService = ProductService(priceCalculator)

    @Test
    fun `test get total price`() {
        every { priceCalculator.calculatePrice(1) } returns 100.0
        every { priceCalculator.calculatePrice(2) } returns 200.0
        every { priceCalculator.calculatePrice(3) } returns 300.0

        val productIds = listOf(1, 2, 3)

        val totalPrice = productService.getTotalPrice(productIds)

        assertEquals(600.0, totalPrice)
        verify(atLeast = 2){priceCalculator.calculatePrice(allAny())}
        verify(exactly = 3) { priceCalculator.calculatePrice(any()) }

    }
}

