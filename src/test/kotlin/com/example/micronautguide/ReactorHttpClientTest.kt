package com.example.micronautguide

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.micronaut.reactor.http.client.ReactorHttpClient
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.awt.print.Book

/**
 * from HttpGetSpec
 * https://github.com/micronaut-projects/micronaut-reactor/blob/master/reactor-http-client/src/test/groovy/io/micronaut/reactor/http/client/HttpGetSpec.groovy
 */

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReactorHttpClientTest {

    private val log: Logger = LoggerFactory.getLogger(ReactorHttpClientTest::class.java)

    //make this this client
    @Inject
    @Client("/")
    lateinit var httpClient: ReactorHttpClient  // regular client

    @Test
    fun `test empty list returns okay`() {
        //val arg = Argument.
        val response =
            httpClient.exchange(HttpRequest.GET<Book>("/get/emptyList/mono"), Argument.listOf(Book::class.java)).block()

        assertEquals(response.status, HttpStatus.OK)
        assertTrue(response.body().isEmpty())
    }

    @Test
    fun `test 2 book list from get request`() {
        val response =
            httpClient.exchange(
                HttpRequest.GET<Book>("/get/twoBookList/flux"),
                Argument.listOf(Book::class.java)
            )
                .block()

        assertEquals(response.status, HttpStatus.OK)
        assertEquals(2, response.body().size)
        log.info("heres body ${response.body()}<<-done")
    }


    @Serdeable
    data class Book(
        var id: String? = null,
        var title: String
    )

    @Controller("/get")
    class BookController {
        @Get("/emptyList/mono")
        fun emptyListMono(): Mono<List<Book>> =
            Mono.just(listOf())

        @Get("/twoBookList/flux")
        fun getTwoBookFlux(): Flux<Book> {
            val bookList = mutableListOf(
                Book("1", "Lenin's Tomb"),
                Book("2", "Khrushchev on Mars")
            )
            return Flux.fromIterable(bookList)
            //return bookList.toFlux()
        }

    }
}