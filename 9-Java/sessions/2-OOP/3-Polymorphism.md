# Polymorphism in Java

## 1. Method Overloading

- **Method Overloading** refers to defining multiple methods with the same name but different parameters within the same class or subclass.
- This allows methods to perform similar functions but with different types or numbers of inputs.

## 2. Method Overriding

- **Method Overriding** occurs when a subclass defines a method with the same name, return type, and parameters as a method in its parent class.
- This process leads to specialization in the inheritance hierarchy, allowing the subclass to provide a specific implementation for the inherited method.
- `virtual` keyword in C++ is not required in Java because all methods are virtual by default.

## 3. Constructor Redirection

- **Constructor Redirection**:
  - Within the same class, one constructor can call another using the `this()` keyword, similar to how `super()` is used in inheritance to call a parent class constructor.
  
- **Reference and Object Types**:
  - When you write `Parent p = new Child();`, `p` is a reference to a parent object of type `Parent`, but it points to a child object of type `Child`.
  - Although `p` can only directly call methods defined in the parent class, Java uses dynamic binding (late binding) to determine the method implementation to call at runtime.
  - If the method is overridden in the child class, the child class's method is invoked. If not, the parent class's method is used.

### Dynamic Binding in Java

- **Dynamic Binding (Late Binding)**:
  - In Java, all methods are virtual by default, meaning the method that is executed is determined at runtime, not compile-time.
  - This contrasts with **Early Binding**, where the method is determined at compile-time, as is typical in languages like C++ (unless the `virtual` keyword is used).

### Example: Polymorphic Behavior with Shapes

- Suppose you want to calculate the sum of the areas of different shapes. You can create an array of `Shape` objects and store different types of shapes (like `Circle`, `Rectangle`, and `Triangle`), each of which overrides the `calculateArea()` method.

```java
Shape[] shapes = new Shape[3];
shapes[0] = new Circle(5);
shapes[1] = new Rectangle(5, 10);
shapes[2] = new Triangle(5, 10);
```

- The `calculateArea()` method in the parent `Shape` class is overridden in each child class.

```java
double calculateArea(Shape[] shapes) {
    double sum = 0;
    for (Shape s : shapes) {
        sum += s.calculateArea();  // Each time, the overridden method in the child class is called.
    }
    return sum;
}
```

## 4. Abstract Classes and Methods

- **Template Method**:
  - The implementation of a method in the parent class that is intended to be overridden by subclasses is often referred to as a "template method."

- **Abstract Method**:
  - An abstract method is a method declared in the parent class with no implementation (i.e., without a body).
  - The `abstract` keyword is used to define such methods, and a class containing one or more abstract methods must be declared as an abstract class.

```java
abstract class Shape {
    abstract double calculateArea();  // Abstract method
}
```

- **Abstract Classes**:
  - An abstract class may contain zero or more abstract methods and zero or more concrete (implemented) methods.
  - Abstract classes cannot be instantiated directly, meaning you cannot create an object of an abstract class using `new`.
  - Even if a class is abstract, its constructor is called when an object of a subclass is created. However, you cannot create an object from the abstract class explicitly.

- **References**:
  - You cannot create a reference of the child class to the parent class (this would cause a compiler error).
  - However, you can create a reference of the parent class to a child class object, which is a common practice in polymorphism.
