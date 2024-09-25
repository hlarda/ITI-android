package com.example.hellouser

fun main() {
    val rows = 5
    for (i in 1..rows) {
        for (j in 1..rows + i) {
            if (j <= i) {
                print("*")
            } else if (j <= rows) {
                print("  ")
            } else {
                print(" *")
            }
        }
        println()
    }
}