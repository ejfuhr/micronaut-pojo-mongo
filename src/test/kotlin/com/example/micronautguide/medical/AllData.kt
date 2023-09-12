package com.example.micronautguide.medical

import com.example.micronautguide.pojo.Patient
import kotlin.random.Random

data class FakeDoctor(var id:String? = "d-".plus(Random.nextInt(10, 1001)),
                  val name:String,
                  var patients:MutableList<Patient>)



