package com.example.micronautguide.pairsTriples

import com.example.micronautguide.pojo.Doctor
import com.example.micronautguide.pojo.Patient
import com.example.micronautguide.pojo.Symptom
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime.now

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TriplesTest {

    val log:Logger = LoggerFactory.getLogger(TriplesTest::class.java)

    fun threeValues(): Triple<String, Int, String> = Triple("Ali", 33, "Neka")


    var doc = Doctor("1", null,"n", mutableListOf())
    var pat = Patient("1", null, "p", mutableListOf())
    var sym = Symptom("1", null, mutableListOf("cold"))

    fun docPatientSympEntry():
            Triple<Doctor, Patient, Symptom> = Triple(doc, pat, sym)

    @Test
    fun `test threeValues`() {
        val (name, age, bornOn) = threeValues()
        assertEquals(threeValues().first ,"Ali")
    }

    @Test
    fun `doctor patient symptom`(){
        doc = Doctor("1", null, "numero", mutableListOf())
        pat = Patient("1", null, "patient", mutableListOf())
        sym = Symptom("1", null, mutableListOf( "cold"))
        val (doc, pat, sym) = docPatientSympEntry()
        assertEquals(docPatientSympEntry().first.name, "numero")

        docPatientSympEntry().second.symptoms.add(sym)
        docPatientSympEntry().first.patients.add(pat)

        assertTrue(doc.patients.size ==1)
        assertTrue(pat.symptoms.size == 1)
    }

}

