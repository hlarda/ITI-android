# OOP In Java CONTINUED

## Table of Contents

- [OOP In Java CONTINUED](#oop-in-java-continued)
  - [Table of Contents](#table-of-contents)
  - [Association, Aggregation, and Composition](#association-aggregation-and-composition)
  - [Naming Rules](#naming-rules)
  - [Data Types in Java](#data-types-in-java)
    - [1. Primitive Data Types](#1-primitive-data-types)
      - [Important Notes](#important-notes)
    - [2. Non-Primitive Data Types](#2-non-primitive-data-types)
      - [Wrapper Classes](#wrapper-classes)
        - [Key Wrapper Classes](#key-wrapper-classes)
        - [Features of Wrapper Classes](#features-of-wrapper-classes)
    - [Summary](#summary)
  - [Object Class](#object-class)
  - [Relational Operators](#relational-operators)
  - [Difference Between `&&` and `&` (Performance Wise)](#difference-between--and--performance-wise)

## Association, Aggregation, and Composition

| **Aspect**|**Association**|**Aggregation**|**Composition**|
|---|---|---|---|
| **Definition**        | A relationship between two classes without ownership.         | A relationship where one class owns another, but the owned class can still exist independently.  | A relationship where one class owns another, and the owned object cannot exist independently. |
| **Lifetime Relation** | Independent lifetimes; neither class controls the other.      | The lifetime of the owned object is independent of the owner; the owned object can exist even if the owner is destroyed. | The lifetime of the owned object is dependent on the owner; if the owner is destroyed, the owned object is also destroyed. |
| **Ownership**         | No ownership; just a relationship.                            | Ownership exists, but it is weak; the owner does not strictly control the lifecycle of the owned object. | Strong ownership; the owner strictly controls the lifecycle of the owned object. |
| **Example**           | A teacher associated with multiple students.                  | A university and its departments; departments can exist without the university.                   | A house and its rooms; if the house is destroyed, the rooms cease to exist. |

## Naming Rules

starts with letter, underscore, or dollar sign.

## Data Types in Java

Data types in Java are classified into two main categories: **Primitive Data Types** and **Non-Primitive Data Types**. Each type serves a specific purpose and has different characteristics.

### 1. Primitive Data Types

Primitive data types are the fundamental types built into the Java language. They represent single values and are not objects. These types are efficient and directly supported by the Java Virtual Machine (JVM).

| **Type**          | **Specific Types**        | **Capacity**        | **Example Values**                          |
|-------------------|---------------------------|---------------------|---------------------------------------------|
| **Boolean**       | -                         | 1 bit               | `true`, `false`                             |
| **Integers**      | `byte`, `short`, `int`, `long` | 1, 2, 4, 8 bytes | `5`, `5L` (long), `05` (octal), `0x5` (hex) |
| **Floating Point**| `float`, `double`         | 4, 8 bytes          | `5.0`, `5.0F` (float)                       |
| **Characters**    | `char` (unsigned Unicode chars) | 2 bytes        | `'a'-'z'`, `'A'-'Z'`, `'0'-'9'`, `\u0000-\uFFFF`, `\n`, `\t`, `\b`, `\r` |

#### Important Notes

- To avoid compilation errors and save the conversion time, specify the correct type suffix (e.g., `F` for `float`) when assigning values.
- **Widening Conversion** (automatic): Java automatically converts smaller types (`byte`, `short`, `int`, `long`, `float`, `double`) to larger types.

    ```java
    int i = 10;
    long l = i;  // Automatic conversion from int to long
    ```

- **Narrowing Conversion** (manual): Converting from a larger type to a smaller type requires explicit casting, as it is not done automatically by the compiler. it can lead to data loss or precision loss, so it must be done carefully.

    ```java
    long l = 10;
    int i = (int) l;  // Manual conversion from long to int
    ```

>- **Compilation Error**: In the line `float f = 0.01;`, the value `0.01` is a `double` literal by default. Java does not automatically convert a `double` to a `float` due to the potential loss of precision. Hence, this will result in a compilation error.
>
>- **Correct Assignment**: To correctly assign a `float` value, you need to explicitly specify that `0.01` should be treated as a `float` by appending an `F` or `f` to the value. Thus, `float f = 0.01F;` is the correct way to declare and initialize a `float` variable with the value `0.01`.

### 2. Non-Primitive Data Types

Non-primitive data types, also known as reference types, include classes, interfaces, arrays, and enumerations. They can store complex data and provide additional methods for operations.

#### Wrapper Classes

Wrapper classes are non-primitive data types that correspond to each primitive data type. They enable primitives to be used as objects, which is particularly useful for working with collections and utilizing utility methods.

##### Key Wrapper Classes

- **Primitive Type** → **Wrapper Class**
  - `boolean` → `Boolean`
  - `byte` → `Byte`
  - `short` → `Short`
  - `int` → `Integer`
  - `long` → `Long`
  - `float` → `Float`
  - `double` → `Double`
  - `char` → `Character`

##### Features of Wrapper Classes

1. **Conversion Methods**:
   - Wrapper classes offer methods to convert between strings and primitive values. For example, `Integer.parseInt("123")` converts the string `"123"` to the integer `123`.

2. **Validation Methods**:
   - They include methods to check the limits and validity of values. For example, `Integer.MIN_VALUE` and `Integer.MAX_VALUE` provide the minimum and maximum values for an `int`.

3. **Boxing and Unboxing**:
   - **Boxing**: Converting a primitive value into a wrapper class object. Example: `Integer i = new Integer(10);` (manually boxing).
   - **Unboxing**: Extracting the primitive value from a wrapper class object. Example: `int j = i.intValue();` (manually unboxing).
   - **Auto-Boxing** and **Auto-Unboxing**: Java automatically handles these conversions when needed. For example, `int k = i;` (auto-unboxing) and `i = 20;` (auto-boxing).

### Summary

- **Primitive Data Types**: Basic types with single values and no methods or attributes.
- **Non-Primitive Data Types**: More complex types, including wrapper classes, which provide additional methods and functionalities.
- **Wrapper Classes**: Enable primitives to be treated as objects and provide methods for conversion, validation, and additional functionalities.

Here’s an organized and clarified version of your notes on the **Object class**, **Relational Operators**, and the difference between **&&** and **&** in Java:

---

## Object Class

1. **Object Class**:
   - The `Object` class is the root class of the Java class hierarchy. Every class in Java implicitly inherits from `Object`.

2. **Inheritance**:
   - Any class that does not explicitly extend another class automatically extends `Object`.
   - Any class that extends another class also indirectly extends `Object`.

3. **Methods**:
   - The `Object` class provides several essential methods for all Java objects:
     - `toString()`: Returns a string representation of the object.
     - `equals(Object obj)`: Compares this object to the specified object.
     - `hashCode()`: Returns a hash code value for the object.
     - `getClass()`: Returns the runtime class of the object.
     - `finalize()`: Called by the garbage collector before an object is removed from memory.
     - `clone()`: Creates and returns a copy of this object.
     - `notify()`, `notifyAll()`, `wait()`: Methods for concurrent programming and thread synchronization.

## Relational Operators

- **instanceof Operator**:
  - The `instanceof` operator is used to test whether an object is an instance of a specific class or subclass.
  - **Syntax**: `object instanceof ClassName`
  - **Example**:

    ```java
    String str = "Hello";
    boolean result = str instanceof String; // true, because str is an instance of String
    ```

## Difference Between `&&` and `&` (Performance Wise)

1. **`&&` (Short-Circuit Operator)**:
   - **Behavior**: If the first condition is `false`, the second condition is not evaluated. This is known as short-circuiting.
   - **Use Case**: Efficient when the second condition involves expensive operations or method calls that should not be executed if the first condition is `false`.
   - **Example**:

     ```java
     boolean result = (x > 10) && (expensiveMethod()); // expensiveMethod() is not called if x <= 10
     ```

2. **`&` (Non-Short-Circuit Operator)**:
   - **Behavior**: Both conditions are always evaluated, regardless of the result of the first condition.
   - **Use Case**: Useful when both conditions need to be evaluated, such as in certain mathematical or logical operations.
   - **Example**:

     ```java
     boolean result = (x > 10) & (expensiveMethod()); // expensiveMethod() is always called
     ```

**Performance Considerations**:

- Use `&&` for efficiency when the second condition's evaluation is costly and should be avoided if the first condition is `false`.
- Use `&` when both conditions must be evaluated, such as when checking multiple conditions that affect the outcome.

>if there is assignment or method call in the second condition, it wont be executed using **&&** but it will be executed using **&**.
