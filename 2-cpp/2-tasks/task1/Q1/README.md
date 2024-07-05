# Q1: Implement the code of the following Flowchart

```mirmaid
graph TD
    A[Start] --> B[Create a variable for input]
    B --> C[Initialize the variable with 0]
    C --> D[Display Please Enter Number:]
    D --> E(Take input from user)
    E --> F{Check if number is not 0}
    F -->|true| G[Accumulate the number in result]
    G --> H[Display message to enter another number]
    H --> E
    F -->|false| I[Display the result]
    I --> J[Stop]
```
