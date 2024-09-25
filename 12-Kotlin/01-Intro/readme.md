# Introduction To Kotlin

## Table of Contents

- [Introduction To Kotlin](#introduction-to-kotlin)
  - [Table of Contents](#table-of-contents)
  - [Introduction](#introduction)
  - [Hello World Program](#hello-world-program)
  - [Variables](#variables)
  - [Keywords](#keywords)
  - [Type Inference](#type-inference)
  - [Bitwise Operators](#bitwise-operators)
  - [Equality Operators](#equality-operators)
  - [Operators](#operators)
  - [Control Flow](#control-flow)
    - [IF Expression](#if-expression)
  - [WHEN Expression](#when-expression)
  - [FOR Loop](#for-loop)
  - [repeat Loop](#repeat-loop)
  - [Continue and Break](#continue-and-break)
  - [Expression, Statement, Block](#expression-statement-block)
  - [String](#string)
    - [String Pool](#string-pool)
    - [String Interpolation](#string-interpolation)
  - [Null Safety](#null-safety)

## Introduction

- JVM based language produces Java byte code.
- Kotlin is a statically typed language.
- Supports OOP, Functional Programming.
- Null safety: Null pointer exception is a common problem in Java, Kotlin has null safety as compiler tries to prevent null pointer exceptions.
- Coroutines: Kotlin has built-in support for coroutines. Coroutines are light-weight threads.

## Hello World Program

```kotlin
fun main() {
    println("Hello, World!")
}
```

## Variables

- Variables are declared using `var` and `val` keywords.
- `var` is mutable, `val` is immutable.
- Data type in Kotlin is referenced types and can be inferred by the compiler. We have no primitive data types in Kotlin(like int, float, double, etc).
- Kotlin has `Int`, `Float`, `Double`, `Boolean`, `Char`, `String`, `Number` classes.

```kotlin
fun main() {
    var name : String  = "John"
    val age :  Integer :  = 25
    println("Name: $name, Age: $age")
    name = "Doe"
    println("Name: $name, Age: $age")
}
```

## Keywords

1. Hard Keywords: These are reserved words and cannot be used as identifiers.
2. Soft Keywords: These are not reserved words and can be used as identifiers.
| Hard Keywords | Soft Keywords |
| --- | --- |
| as, is, in, object, out, typealias, var | by, catch, constructor, delegate, dynamic, field, file, finally, get, import, init, param, property, receiver, set, setparam, where |

## Type Inference

- Kotlin has a feature called `type inference`. The compiler can infer the type of the variable based on the value assigned to it.

```kotlin
fun main() {
    var name = "John"
    val age = 25
    println("Name: $name, Age: $age")
}
```

## Bitwise Operators

shl(bits) – signed shift left
shr(bits) – signed shift right
ushr(bits) – unsigned shift right
and(bits) – bitwise and
or(bits) – bitwise or
xor(bits) – bitwise xor
inv() – bitwise inversion

```kotlin
fun main() {
    val a = 5
    val b = 3
    println("a and b: ${a and b}")
    println("a or b: ${a or b}")
    println("a xor b: ${a xor b}")
    println("a shl b: ${a shl b}")
    println("a shr b: ${a shr b}")
    println("a ushr b: ${a ushr b}")
    println("a.inv(): ${a.inv()}")
}
```

## Equality Operators

- `==` is used to compare the values of two objects.
- `===` is used to compare the references of two objects.

```kotlin
fun main() {
    val a = 5
    val b = 5
    val c = 10
    println("a == b: ${a == b}")
    println("a == c: ${a == c}")
    println("a === b: ${a === b}")
    println("a === c: ${a === c}")
}
```

## Operators

| Operator | Description |
| --- | --- |
| !! | Not-null assertion operator |
| ?: | Elvis operator |
| ?. | Safe call operator |
| :: | Reference operator |
| .. | Range operator |
| _ | Placeholder |
| ? | Nullable operator |
| $ | String template |

## Control Flow

### IF Expression

- The usual `if`, `else if`, `else` constructs are available in Kotlin with additional features like using `if` as an expression like ternary operator.
- When using `if` as an expression, the `else` part is mandatory.
- The return statement must be the last statement in the block.

```kotlin
fun main() {
    val a = 5
    val b = 10
    val max = if (a > b) a else b
    /* else part is mandatory */
    val min = if (a < b){
        a
    }else{
        b
    }
    /* return must be the last statement in the block */
    val result = if (a > b){
        println("a is greater than b")
        a
    }else{
        println("b is greater than a")
        b
    }

    if(a in 1..10){
        println("a is between 1 and 10")
    }

    println("Max: $max")
}
```

## WHEN Expression

- `when` works like a switch statement but more powerful.
- `when` used arbitrary expressions in cases not only constants.

```kotlin
fun main() {
    val x = 5
    when(x){
        1    -> println("x is 1")
        2    -> println("x is 2")
        3    -> println("x is 3")
        else -> println("x is not 1, 2, or 3")
    }
    val y = 10
    when(y){
        in 1..5  -> println("y is between 1 and 5")
        in 6..10 -> println("y is between 6 and 10")
        else     -> println("y is not between 1 and 10")
    }
    var mealSize = "L"
    when(mealSize){
        "S" , "s" -> println("Small")
        "M" , "m" -> println("Medium")
        "L" , "l" -> println("Large")
        else      -> println("Unknown size")
    }
    var temperature = 25
    var humidity = 80
    when{
        temperature < 0                     ->  println("Freezing")
        temperature in 0..10                ->  println("Very Cold")
        temperature in 11..20               ->  println("Cold")
        temperature in 21..30               ->  println("Normal")
        temperature in 31..40               ->  println("Hot")
        temperature > 40                    -> println("Very Hot")
        temperature > 50 && humidity > 90   -> println("Extreme")

        else -> println("Unknown")
    }
}
```

## FOR Loop

- `for` loop is used to iterate over a range, array, or a collection.
- `for` loop can be used as an expression.

```kotlin
fun main() {
    for (i in 1..5){
        println(i)
    }
    val names = arrayOf("John", "Doe", "Jane")
    for (name in names){
        println(name)
    }
    val numbers = intArrayOf(1, 2, 3, 4, 5)
    for (number in numbers){
        println(number)
    }
    val result = for (i in 1..5){
        i * i
    }
    println(result)
}
```

- Range supports performing operations like `step`, `downTo`, `until`.
  - `step` is used to increment the value by a specified step.
  - `downTo` is used to iterate in reverse order.
  - `until` is used to iterate until the specified value.

```kotlin
fun main() {
    for (i in 1..10 step 2){
        println(i)
    }
    for (i in 10 downTo 1){
        println(i)
    }
    for (i in 1 until 10){
        println(i)
    }
    for (i in 10 downTo 1 step 2){
        println(i)
    }
    for (i in 1 until 10 step 2){
        println(i)
    }
}
```

## repeat Loop

- `repeat` loop is used to repeat the block of code a specified number of times.

```kotlin
fun main() {
    repeat(5){
        println("Hello")
    }
}
```

## Continue and Break

- Labels are used to break or continue the outer loop.

```kotlin
fun main() {
    outer@ for (i in 1..5){
        for (j in 1..5){
            if (i == 3 && j == 3){
                break@outer
            }
            println("i: $i, j: $j")
        }
    }
}
```

## Expression, Statement, Block

- Expression: Produces a value.

  ```kotlin
    val a = c + d
    val b = if (a > 10) 1 else 2
    ```

- Statement: Performs an action.

    ```kotlin
     println("Hello")
     var a = 10
    ```

- Block: A group of statements enclosed in curly braces.

    ```kotlin
    {
        println("Hello")
        var a = 10
    }
    ```

## Array

- Array size is fixed.
- `arrayOfNulls<Type>(size)` is used to create an array of nulls.
- `arrayOf(1, 2, 3)` is used to create an array of elements.
- Basic types are `IntArray`, `FloatArray`, `DoubleArray`, `BooleanArray`, `CharArray`, `StringArray`.

```kotlin
fun main() {
    val numbers = intArrayOf(1, 2, 3, 4, 5)
    for (number in numbers){
        println(number)
    }
    val names = arrayOf("John", "Doe", "Jane")
    for (name in names){
        println(name)
    }
    val nulls = arrayOfNulls<Int>(5)
    for (nullValue in nulls){
        println(nullValue)
    }
}
```

- `arr.set(index, value)` is used to set the value at the specified index.
- `arr.get(index)` and `arr[0]`is used to get the value at the specified index.
- `arr.size` is used to get the size of the array.
- `arr.indices` is used to get the range of indices.

```kotlin
fun main() {
    val numbers = intArrayOf(1, 2, 3, 4, 5)
    numbers.set(0, 10)
    println(numbers.get(0))
    println(numbers[0])
    println(numbers.size)
    for (i in numbers.indices){
        println(numbers[i])
    }
    val names : StringArray = arrayOf("John", "Doe", "Jane")
}
```

- Operation on arrays like `sum()`, `average()`, `max()`, `min()`, `count()`, `contains()`, `indexOf()`, `lastIndexOf()`, `first()`, `last()`, `reversedArray()`.

```kotlin
fun main() {
    val numbers = intArrayOf(1, 2, 3, 4, 5)
    println(numbers.sum())
    println(numbers.average())
    println(numbers.max())
    println(numbers.min())
    println(numbers.count())
    println(numbers.contains(3))
    println(numbers.indexOf(3))
    println(numbers.lastIndexOf(3))
    println(numbers.first())
    println(numbers.last())
    println(numbers.reversedArray().contentToString())
}
```

## String

- String Literals:
  - Raw String: `"""` is used to create a multi-line string.
  - Escaped String: `"` is used to create a single-line string.

```kotlin
fun main() {
    val rawString = """
        Hello
        World
    """.trimIndent()
    println(rawString)
    val escapedString = "Hello\nWorld"
    println(escapedString)
}
```

### String Pool

- String pool is a memory area in the heap where all the string literals are stored.
- Strings created using "", """ are stored in the string pool.
- Strings created using `String()` are not stored in the string pool.

> try to paly with == and === operators with strings.

### String Interpolation

- String interpolation is used to embed expressions in a string using `$` or `${}`.

## Null Safety

- Null safety is a feature in Kotlin to prevent null pointer exceptions.
- Nullable types are declared using `?` operator after the type as types are non-nullable by default in Kotlin.
- To access the value of a nullable type
  - we need to use the safe call operator `?.`
  - the not-null assertion operator `!!`
  - the Elvis operator `?:`
  - the safe cast operator `as?`
  - if-else expression

```kotlin
fun main() {
    var name : String? = null
    println(name?.length)
    println(name?.length ?: "Name is null")
    println(name!!.length)
    println(name as? Int)
    name = "John"
    println(name?.length)
    if (name != null){
        println(name.length)
    }
}
```
