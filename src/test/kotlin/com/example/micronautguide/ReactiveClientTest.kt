package com.example.micronautguide

import com.example.micronautguide.mockkStuff.MedicalMockTest
import com.example.micronautguide.pojo.Manufacturer

import io.micronaut.core.annotation.Creator
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import io.micronaut.reactor.http.client.*;
import io.micronaut.reactor.http.client.websocket.ReactorWebSocketClient
import io.micronaut.reactor.http.client.proxy.ReactorProxyHttpClient
import io.micronaut.serde.annotation.Serdeable
import jakarta.validation.constraints.NotBlank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import reactor.core.publisher.Flux


@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReactiveClientTest {

    private val log: Logger = LoggerFactory.getLogger(ReactiveClientTest::class.java)

    @Inject
    lateinit var httpClient: ReactorHttpClient  // regular client

    @Inject
    lateinit var sseClient: ReactorSseClient  // server sent events

    @Inject
    @Client("/")
    lateinit var streamingHttpClient: ReactorStreamingHttpClient  // streaming

    @Inject
    lateinit var webSocketClient: ReactorWebSocketClient

    @Inject
    lateinit var proxyHttpClient: ReactorProxyHttpClient


    @Test
    fun `test clients are injected`() {
        assertNotNull(sseClient)
        assertNotNull(streamingHttpClient)
        assertNotNull(httpClient)
        assertNotNull(webSocketClient)
        assertNotNull(proxyHttpClient)
    }

    @Test
    fun `test reactor flux events`() {

/*        val req = HttpRequest.GET<Hello>("/flux/hello/fred")

        val helloFlux1: Flux<MutableMap<String, Any>>? = streamingHttpClient.jsonStream(req)*/

        val helloFlux: Flux<Hello> = streamingHttpClient.jsonStream(
            HttpRequest.GET<Hello>("/flux/hello/fred"),
            Hello::class.java
        )
        val hello = helloFlux.blockFirst()

        assertTrue(
            hello.name == "test1"
        )
        assertTrue(hello.number == 1)
    }

    @Controller("/")
    class HelloController {

        @Produces(MediaType.APPLICATION_JSON_STREAM) // add 'application/stream+json'
        @Get("/flux/hello/{name}")
        fun hello(@NotBlank name: String): Flux<Hello> {

            var list = mutableListOf<Hello>()
            //List<Hello> list = new ArrayList<>()

            list.add(Hello("test1", 1))
            list.add(Hello("test2", 2))

            return Flux.fromIterable(list).doOnComplete { ->
                System.out.println("response should be closed here!")
            }
        }
    }

    @Serdeable
    class Hello(
        var name: String,
        var number: Int
    )
}