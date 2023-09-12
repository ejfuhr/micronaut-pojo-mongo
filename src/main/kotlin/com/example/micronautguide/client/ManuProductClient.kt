package com.example.micronautguide.client

import io.micronaut.http.client.annotation.Client

@Client("/myStuff")
interface ManuProductClient : ManuProductAPI{
}