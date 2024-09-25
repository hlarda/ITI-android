package com.example.hellouser

fun main(){
    println("Hello User!")
    println("What is your first name?")
    val firstName: String? = readlnOrNull()
    println("What is your last name?")
    val lastName: String? = readlnOrNull()
    if (firstName.isNullOrBlank() || lastName.isNullOrBlank()) {
        println("Pls Enter First And Last string name!")
    }else if (firstName.any { !it.isDigit() } || lastName.any { !it.isLetter() }) {
        println("Names should contain letters only!")
    } else {
        println("Hello $firstName $lastName!")
    }
}