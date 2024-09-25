package com.example.hellouser

fun main(){
    println("Hello User!")
    println("What is your name?")
    val name: String? = readlnOrNull()
    println("Hello ${if (name.isNullOrBlank()) "Anonymous" else name}!")
}