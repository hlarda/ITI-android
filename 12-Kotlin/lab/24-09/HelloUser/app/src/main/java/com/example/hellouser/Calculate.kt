package com.example.hellouser

fun main(){
    print("Enter first number: ")
    val op1: String = readln()
    print("Enter second number: ")
    val op2: String = readln()

    if (op1.isBlank() || op2.isBlank()) {
        println("Invalid input")
    } else if (op1.toDoubleOrNull() == null || op2.toDoubleOrNull() == null) {
        println("Invalid input")
    } else {
        val op1: Double = op1.toDouble()
        val op2: Double = op2.toDouble()
        println("Sum: ${op1 + op2}")
        println("Difference: ${op1 - op2}")
        println("Product: ${op1 * op2}")
        println("Division: ${if (op2 == 0.0) "DIVISION BY ZERO INVALID" else op1 / op2}")
    }
}