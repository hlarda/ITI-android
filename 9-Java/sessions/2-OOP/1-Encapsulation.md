# Encapsulation in Java

## OOP is Mapping Real-World Objects to Software

### Classes

- **Classes**
  - serve as blueprints or templates for creating objects.
  - act as user-defined data types that define the structure and behavior of objects.

### Objects

- **Objects**
  - are the living instances of a class.
  - must be dynamically allocated in memory.

### Java Class Structure

```java
<access_modifier>* class <class_name> {
    <attribute_declaration>*
    <method_declaration>*
    <constructor_declaration>*
}
```

- `*` indicates that there can be zero or more of the specified element.

### Main Concepts

- **Encapsulation**:
  - Encapsulating the data (attributes) and methods into a single unit or class.
- **Data Hiding**:
  - Encapsulation provides data hiding by restricting direct access to some of an object’s components.
  - This is typically done by making attributes private and providing public getter and setter methods to access and modify them to validate the data.
  - Let's say, a machine might require input within a specific range. You can enforce this range using constraints in the setter method to validate the data before storing it or performing any action.

- **Access Modifiers**:
  - `public`: The class, method, or attribute is accessible from anywhere.
  - `private`: Accessible only within the class itself not even by subclasses.It can be accessed through public setter and getter.
  - `protected`: Accessible within the package and by subclasses.

### Constructors

- **Constructors** are special methods called when an object is created. They initialize the instance variables of a class.

#### Members

1. **Instance Members**:
   - These are initialized when an object is created.
   - Each object has its own set of instance variables, but the method code remains the same across all instances.
   - Instance members are non-static accessed using the object name.

2. **Class Members**:
   - These are initialized when the class is first loaded, typically when the first object is created.
   - Only one copy of class members exists in memory.
   - Class members are static and are usually accessed using the class name (though they can also be accessed using an object name, which is not recommended).
   - Note: Static methods cannot manipulate instance members because static methods exist before any object is created.

> **Note**: A constructor is an instance member since it is called when an object is created and manipulates instance members. A constructor can be private but not static.

- Constructors have the same name as the class.
- They do not have a return type.
- Constructors can be overloaded.
- If no constructor is defined, the compiler generates a default constructor. However, if any constructor is defined, the compiler does not create a default constructor.

### Object Allocation

Objects must be dynamically allocated in Java using the `new` keyword:

If you create an object reference without the `new` keyword, it is only a reference, not an actual object:

```java
public class Main {
    public static void main(String[] args) {
        Disc d;
    }
}
```

### The `this` Keyword

- The `this` keyword is a reference to the current instance of the class.
- `this()` is used to call a constructor from another constructor in the same class.

> In Java, you cannot simply write `size = size;` because this will assign the value of the attribute to itself. To refer to the instance variable, you must use the `this` keyword (e.g., `this.size = size;`). Although it will compile without errors, it will not perform as intended without the `this` keyword (this issue is known as shadowing).

### TODO

- Implement a private constructor invoked by a static method to create an object.
- Ensure that the object is created only once (Singleton pattern) -> use null check.
