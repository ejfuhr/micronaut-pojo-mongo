package com.example.micronautguide.client

import io.micronaut.http.client.annotation.Client

@Client("/medical")
interface MedicalReportClient:MedicalReportAPI {
}