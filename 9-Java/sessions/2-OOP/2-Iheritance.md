# Inheritance in Java

## 1. Introduction to Inheritance

- **Inheritance**
  - allows a class to extend the functionality of another class.
  - a child class can inherit properties and behaviors from a parent class, which promotes code reuse and efficient code management.

## 2. Inheritance Concepts

### 2.1 Basic Definition

- Inheritance is a mechanism in Java where one class (child class) inherits attributes and methods from another class (parent class).
- This process enables the child class to gain the capabilities of the parent class and potentially add or override some features.

### 2.2 The `Obfuscator` Class Example

- **`Obfuscator` Class**:
  - The `Obfuscator` class is designed to obfuscate code, making it harder to reverse-engineer using decompilers.
  - This process not only protects the code but also makes its footprint smaller.
  - The only way to reuse the `Obfuscator` class is to inherit from it and extend its functionality.

### 2.3 Inheritance Hierarchy

- **Inheritance Hierarchy**: The structure formed by inheritance, where classes are connected from parent to child and vice versa.
  - **Specialization**: Moving down the hierarchy from parent to child, where the child class adds more specific functionality.
  - **Generalization**: Moving up the hierarchy from child to parent, focusing on the broader, more general aspects.

## 3. Implementing Inheritance

### 3.1 Creating a Subclass

- To inherit from a class, the child class should be a subclass of the parent class and provide some extended functionality. This is done using the `extends` keyword.

```java
public class ParentClass {}
public class ChildClass extends ParentClass {}
```

### 3.2 Constructor Behavior in Inheritance

- **Constructor Chain**:
  - When a child object is created, the parent’s default constructor is firstly called by default, followed by the child’s constructor.
  - If the parent class does not have a default constructor, the child constructor should explicitly call the parent constructor using the `super()` keyword as the first line in the child constructor.

```java
public class ParentClass {
    public ParentClass(int arg1, int arg2) {}
}
public class ChildClass extends ParentClass {
    public ChildClass(int arg1, int arg2, int arg3) {
        super(arg1, arg2); // Calls the parent constructor
    }
}
```

- By default, `super()` is implicitly called at the beginning of the child constructor if not explicitly mentioned.

### 3.3 Accessing Parent Class Members

- **Instance Members**:
  - Accessing instance members of the parent class through the child proves that there is a parent object created first when calling the constructor.

### 3.4 Conditions for Applying Inheritance

- The parent class should have a public default constructor to be inherited by the child class.

## 4. The `super` Keyword

- The `super` keyword serves two main purposes in inheritance:
  1. **Constructor Invocation**: It is used to call the parent class’s constructor from the child class.
  2. **Parent Class Reference**: It can be used as a reference to the parent class, allowing access to the current instance's parent class members.

```java
public class ChildClass extends ParentClass {
    public ChildClass() {
        super(); // Calls parent class's default constructor
    }

    public void someMethod() {
        super.parentMember(); // Accesses a member of the parent class
    }
}
```

- `super` can be used as a reference to the parent class, with `super.parentMember` accessing the current instance of the parent class.
