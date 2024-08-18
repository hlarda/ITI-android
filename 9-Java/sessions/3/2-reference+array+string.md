# Reference, Array, and String in Java

## Table of Contents

- [Reference, Array, and String in Java](#reference-array-and-string-in-java)
  - [Table of Contents](#table-of-contents)
  - [Reference](#reference)
  - [Array](#array)
    - [1. Array of Primitive Data Types](#1-array-of-primitive-data-types)
    - [2. Array of Objects](#2-array-of-objects)
  - [Difference Between String with `new` and Without `new`](#difference-between-string-with-new-and-without-new)
  - [`else if` in Java](#else-if-in-java)
  - [Switch Case](#switch-case)
  - [Enhanced For Loop](#enhanced-for-loop)
    - [Search Island Problem](#search-island-problem)

## Reference

- **Reference Storage**: In Java, when you create an object, the reference to this object is stored in the stack memory, while the actual object itself is stored in the heap memory.
- **Garbage Collection**: The garbage collector automatically deletes objects from the heap when there are no more references to them. You can't force the garbage collector to run, but you can request it by calling `System.gc();`. This request is just a hint; the garbage collector operates in the background and does not guarantee immediate action.
- **NullPointerException**: A `NullPointerException` occurs when you try to access or modify an object through a reference that is `null`, meaning it doesn’t point to any valid object.
- **Local Variables**: Local variables are not initialized automatically. They must be explicitly initialized before use; otherwise, the Java compiler will generate an error.

## Array

### 1. Array of Primitive Data Types

- **Reference Type**: Arrays in Java are reference types. This means that an array itself is a reference to a block of memory in the heap where the actual elements are stored.
- **Bounds Checking**: If you try to access an element outside the bounds of the array (i.e., an index that is negative or greater than or equal to the length of the array), Java throws an `ArrayIndexOutOfBoundsException`.
- **Length Attribute**: Each array has a `length` attribute that provides the number of elements the array can hold.

```java
DataType[] arrayIdentifier; // Declaration of an array of DataType.
arrayIdentifier = new DataType[size]; // Initialization with a specified size.

DataType[] arrayIdentifier = {value1, value2, value3}; // Declaration and initialization with values.
```

### 2. Array of Objects

```java
// Creating three separate String objects
String name1 = new String("name1");
String name2 = new String("name2");
String name3 = new String("name3");
```

```java
String[] names; // Declaration of an array of String references. Initially, it is a null reference and will throw a NullPointerException if used before initialization.
names = new String[3]; // Initialization of the array to hold 3 String references.
names[0] = new String("name1"); 
names[1] = new String("name2");
names[2] = new String("name3"); 
/* You can make each refernce point to a child class object of the parent class as in shapes example in lab
```

> If the array reference (`names` in this case) is set to `null`, the entire array is eligible for garbage collection.
>
> To compare two objects for equality, use the `equals()` method rather than `==`. The `==` operator checks for reference equality (i.e., whether the two references point to the same object), while `equals()` checks for logical equality (i.e., whether the two objects are meaningfully equivalent). For example, `str1.equals(str2)` is used for comparing the actual content of two strings. This method can be overridden in custom classes to define equality meaning for those classes.

- **Abstract Classes**: You cannot create an object from an abstract class directly with `new type()`. However, you can create an array of an abstract class references using `new type[3]`. Each reference can point to an object of a child class that extends the abstract parent class.

```java
abstract class Animal {
    // Abstract method
    abstract void makeSound();
    // Concrete method
    void sleep() {
        System.out.println("Zzz...");
    }
}

// Define a concrete subclass that extends the abstract class
class Dog extends Animal {
    void makeSound() {
        System.out.println("Woof Woof");
    }
}
class Cat extends Animal {
    void makeSound() {
        System.out.println("Meow Meow");
    }
}

public class Main {
    public static void main(String[] args) {
        // Create an array of references to the abstract class
        Animal[] animals = new Animal[3];

        // Initialize the array with objects of concrete subclasses
        animals[0] = new Dog();
        animals[1] = new Cat();
        animals[2] = new Dog();

        for (Animal animal : animals) {
            animal.makeSound(); // Calls the appropriate method based on the object type
            animal.sleep(); // Calls the regular method from the abstract class
        }
    }
}
```

- **String Concatenation**: In Java, strings can be concatenated using the `+` operator.

    ```java
    String str1 = "Hello";
    String str2 = "World";
    String str3 = str1 + str2; // Concatenates str1 and str2 to form "HelloWorld".
    str3 = str1.concat(str2); // Another way to concatenate, resulting in "HelloWorld".
    str3 += str1; // Appends str1 to str3, making it "HelloWorldHello".
    ```

- **String Immutability**: Strings in Java are immutable, which means once a string is created, its value cannot be changed. When you modify a string, a new string object is created, and the reference is updated to point to this new object. The old string object becomes eligible for garbage collection. Strings are essentially arrays of characters, so operations on strings involve creating new arrays.

## Difference Between String with `new` and Without `new`

- **Using `new` Keyword**: When you use `new` to create a string, it creates a new string object in the heap memory, even if a string with the same value already exists. The reference to this new object is stored in the stack memory.

    ```java
    String str1 = new String("Hello"); // Creates a new string object in the heap.
    ```

- **Without Using `new`**: When you create a string without `new`, it is placed in a special memory area called the string pool. If a string with the same value already exists in the pool, the reference is reused rather than creating a new object.

    ```java
    String str2 = "Hello"; // Reuses the string "Hello" from the string pool if it exists.
    ```

- **Changing Values**: If you modify a string that was created without `new`, a new string object is created in the heap not string pool, and the reference is updated to point to this new object. The old object is no longer referenced.

## `else if` in Java

Java does not have a separate `else if` keyword. Instead, you use nested `if` statements within `else` blocks to achieve similar functionality:

```java
if (condition1) {
    // code to execute if condition1 is true
} else {
    if (condition2) {
        // code to execute if condition1 is false and condition2 is true
    } else {
        // code to execute if both condition1 and condition2 are false
    }
}
```

## Switch Case

- **Supported Types**: The `switch` statement can be used with primitive data types (such as `int`, `char`, `byte`, and `short`), strings, and enums.

## Enhanced For Loop

Range-based for loops, also known as enhanced for loops, provide a concise way to iterate over arrays and collections in Java.

### Search Island Problem
