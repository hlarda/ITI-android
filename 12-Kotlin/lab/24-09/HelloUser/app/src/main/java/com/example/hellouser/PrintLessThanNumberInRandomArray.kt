package com.example.hellouser

fun main(){
    val numbers = arrayOfNulls<Int>(10)
    print("Generated numbers: ")
    for (i in numbers.indices){
        numbers[i] = (0..100).random()
        print("${numbers[i]} ")
    }
    print("\nNumbers less than 10: ")
    for(number in numbers){
        if (number != null && number < 10) {
            print("$number ")
        }
    }
}