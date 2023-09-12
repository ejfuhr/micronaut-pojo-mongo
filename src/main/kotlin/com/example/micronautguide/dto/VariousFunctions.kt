package com.example.micronautguide.dto

//class VariousFunctions(){}
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1] // 'this' corresponds to the list
    this[index1] = this[index2]
    this[index2] = tmp
}

fun <T> MutableList<T>.mapInPlace(mutator: (T) -> (T)) {
    this.forEachIndexed { i, value ->
        val changedValue = mutator(value)

        if (value != changedValue) {
            this[i] = changedValue
        }
    }
}