package com.example.micronautguide

import com.example.micronautguide.reactive.utils.NTuple4
import com.example.micronautguide.reactive.utils.then
import com.example.micronautguide.tuples.NTuplesTestPlus
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.zip
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

/**
 * we have a symptom (Symp) a patient (Pat) a doctor (Doc) and a medical report (MedReport) which shadow our real
 * entities, Symptom, Patient, Doctor and MedicalReport.
 * Here we load them into Mono's and then Flux.zip them into a Flux<MedReport>. during the .map{} exercise/step
 * we add a Symptom.
 * We close or block the zipped Publisher via the StepVerifier and print out the entire MedReport
 */

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedZipTest {

    private val log: Logger = LoggerFactory.getLogger(NTuplesTestPlus::class.java)

    @Test
    fun `test Monos with MedReport`() {
        val s1 = Symp(
            "101", "101p", descriptionList = mutableListOf(
                "sneeze", "temp"
            )
        )
        val p1 = Pat("101", "101p", "dumkee", symptoms = mutableListOf(s1))
        val d1 = Doc("101", "101d", "dr john", patients = mutableListOf(p1))
        val m1 = MedReport("101", "101report", d1, d1.patients)

        val s1Mono: Mono<Symp> = Mono.just(s1) //saved Symp
        val p1Mono: Mono<Pat> = Mono.just(p1) //saved Pat or Patient
        val d1Mono: Mono<Doc> = Mono.just(d1) //saved Doc
        val m1Mono: Mono<MedReport> = Mono.just(m1) //saved MedReport

        val flatMapM1 = Mono.empty<MedReport>()
        val zipStuffFlux: Flux<MedReport> = zip(s1Mono, p1Mono, d1Mono, m1Mono)
            .map {
                val s: Symp = it.t1
                val p: Pat = it.t2
                val d: Doc = it.t3
                val m: MedReport = it.t4
                s.descriptionList.add("more stuff here")
                m
            }
        StepVerifier.create(zipStuffFlux)
            .assertNext {
                assertNotNull(it.doctor)
                assertNotNull(it.id)
                assertNotNull(it.medRepId)
                assertNotNull(it.patients)
                log.info("HERE IS ALL IT $it")
            }
            /*            .expectNext()
                        .expectNext()
                        .expectNext()
                        .expectNext()*/
            .verifyComplete()


    }

    @Test
    fun `buildn Tuple with Med objects`() {
        val s1 = Symp(
            "101", "101p", descriptionList = mutableListOf(
                "sneeze", "temp"
            )
        )
        val p1 = Pat("101", "101p", "dumkee", symptoms = mutableListOf(s1))
        val d1 = Doc("101", "101d", "dr john", patients = mutableListOf(p1))
        val m1 = MedReport("101", "101report", d1, d1.patients)

        val ntuple4 = s1 then p1 then d1 then m1
        val nTuple4Flux: Flux<NTuple4<Symp, Pat, Doc, MedReport>> = Flux.just(ntuple4)
        val nTupledMedReport: Flux<MedReport> = nTuple4Flux
            .map {
                val s: Symp = it.t1
                val p: Pat = it.t2
                val d: Doc = it.t3
                val m: MedReport = it.t4
                s.descriptionList.add("NTuple Stuf here")
                m
            }
            .flatMap {
                Mono.just(ntuple4.t1)
                Mono.just(ntuple4.t2)
                Mono.just(ntuple4.t3)
                Mono.just(ntuple4.t4)
            }// save MedReport here


        StepVerifier.create(nTupledMedReport)
            .assertNext {
                assertNotNull(it.doctor)
                assertEquals("dr john", it.doctor.name)
                assertNotNull(it.id)
                assertNotNull(it.medRepId)
                assertNotNull(it.patients)
                log.info("HERE IS ALL IT FROM NTuples $it")
            }
            .verifyComplete()
    }

    @Serdeable
    data class Symp(
        var id: String,
        var sympId: String,
        val descriptionList: MutableList<String> = mutableListOf()
    )

    @Serdeable
    data class Pat(
        var id: String,
        var patId: String,
        val name: String,
        val symptoms: MutableList<Symp> = mutableListOf()
    )

    @Serdeable
    data class Doc(
        var id: String,
        var docId: String,
        val name: String,
        val patients: MutableList<Pat> = mutableListOf()
    )

    @Serdeable
    data class MedReport(
        var id: String,
        var medRepId: String,
        var doctor: Doc,
        val patients: MutableList<Pat> = mutableListOf(),

        )
}