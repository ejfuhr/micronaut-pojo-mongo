package com.example.micronautguide.mockkStuff

import com.example.micronautguide.pojo.Doctor
import com.example.micronautguide.pojo.Patient
import com.example.micronautguide.pojo.Symptom
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDateTime.now

@MicronautTest(transactional = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MedicalMockTest {

    private val log: Logger = LoggerFactory.getLogger(MedicalMockTest::class.java)

    var docMok = mockk<Doctor>()
    var patMok = mockk<Patient>()
    var symMok = mockk<Symptom>()

    val nameSlot = slot<String>()

    class DocExt{
        fun Doctor.extensionFunc() = name + "p"
        fun Doctor.addPatientSymptom(dname: String, pname:String, symDescript:String): Doctor {
            val symp = Symptom(null, null, mutableListOf(symDescript))
            val pat = Patient(null, null, pname, mutableListOf(symp))
            val doc = Doctor(null, null, dname, mutableListOf(pat))
            return doc
        }
    }

    data class Obj(val value: Int)

    class Ext {
        fun Obj.extensionFunc() = value + 5
    }

    @Test
    fun `test doctExt doctor addPatient`(){

        var doc1: Doctor = Doctor(null, null, "101name", mutableListOf())

        with(mockk<DocExt>()){
            every {
                 Doctor(null, null,"snod", mutableListOf())
                    .addPatientSymptom("snod", "pat-1", "sneeze")
                //docMok.name="charlie"
            }returns doc1
            assertNotNull(doc1, "mok is null")
            log.info("here is docMok  ${doc1}")
            //assertEquals("snod", docMok.name)
            //assertEquals("pat-1", docMok.patients[0].id)
            //why is verify screwed up
/*            verify {
                Doctor(null, "snod", mutableListOf())
                    .addPatientSymptom("snod", "pat-1", "sneeze")
            }*/

        }
    }

    @Test
    fun `test docExt extensionFunc`() {
        with(mockk<DocExt>()) {
            every {
                Doctor("1", null, "snod", mutableListOf()).extensionFunc()
            } returns "snodp"
                    //Doctor("1", "snodp", mutableListOf())

        assertEquals("snodp", Doctor("1", null, "snod", mutableListOf()).extensionFunc())
            verify {
                Doctor("1", null, "snod", mutableListOf()).extensionFunc()
            }
        }
    }

    @Test
    fun testExample(){
        with(mockk<Ext>()) {
            every {
                Obj(5).extensionFunc()
            } returns 11

            assertEquals(11, Obj(5).extensionFunc())

            verify {
                Obj(5).extensionFunc()
            }
        }
    }
}