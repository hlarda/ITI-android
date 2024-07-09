/*implement a class for a dynamic array (Vector)
    * Containing the following functions
        * resize() - resize the array to double the size
        * pushback(value) - add the value to the end of the array
        * popback() - remove the last element from the array
        * removeAt(index) - remove the element at the given index
        * insertAt(index, value) - insert the value at the given index and shift the elements to the right
        * insertMiddle(value) - insert the value in the middle of the array
        * removeMiddle() - remove the middle element from the array
        * size() - return the size of the array
        * print() - print the array
 
    * The following overload constructors:
        * DynamicArray() - default constructor with capacity of 1
        * DynamicArray(size) - constructor with given capacity
        * DynamicArray(size, value) - constructor with given capacity and initial value for all elements --> DynamicArray arr(5, 10);
        * DynamicArray(size, values) - constructor with given capacity and initial values --> DynamicArray arr(5, new int[5]{1, 2, 3, 4, 5});
        * DynamicArray(arr) - copy constructor
 
    * Appropriate destructor
 
    - Private members:
        * array - pointer to the array
        * capacity - capacity of the array
        * currentSize - current size of the array
*/

#include <iostream>

class DynamicArray {
private:
    int* array;
    int capacity;
    int currentSize;
public:
    DynamicArray():capacity(0), currentSize(0),array(nullptr) {}

    DynamicArray(int size):capacity(size), currentSize(0), array(new int[capacity]) {}

    DynamicArray(int size, int value):capacity(size), currentSize(size), array(new int[capacity]){
        for (int i = 0; i < currentSize; i++) {
            array[i] = value;
        }
    }

    DynamicArray(int size, int* values):capacity(size), currentSize(size), array(new int[capacity]){
        for (int i = 0; i < currentSize; i++) {
            array[i] = values[i];
        }
    }

    DynamicArray(const DynamicArray& arr):capacity(arr.capacity), currentSize(arr.currentSize), array(new int[capacity]){
        for (int i = 0; i < currentSize; i++) {
            array[i] = arr.array[i];
        }
    }

    void resize() {
        capacity *= 2;
        int* temp = new int[capacity];
        for (int i = 0; i < currentSize; i++) {
            temp[i] = array[i];
        }
        delete[] array;
        array = temp;
    }

    void pushback(int value) {
        if (currentSize == capacity) {
            resize();
        }
        array[currentSize++] = value;
    }

    int popback() {
        if (currentSize > 0) {
            currentSize--;
            return array[currentSize];
        }else {
            std::cout << "it's already an empty dynamic array" << std::endl;
        }
    }

    void removeAt(int index) {
        if (index < 0 || index >= currentSize) {
            std::cout << "out of range index" << std::endl;
            return;
        }
        for (int i = index; i < currentSize - 1; i++) {
            array[i] = array[i + 1];
        }
        currentSize--;
    }

    void insertAt(int index, int value) {
        if (index < 0 || index >= currentSize) {
            std::cout << "out of range index" << std::endl;
            return;
        }
        if (currentSize == capacity) {
            resize();
        }
        for (int i = currentSize; i > index; i--) {
            array[i] = array[i - 1];
        }
        array[index] = value;
        currentSize++;
    }

    void insertMiddle(int value){
        insertAt(currentSize/2, value);
    }

    void removeMiddle(){
        removeAt(currentSize/2);
    }

    int size() {
        return currentSize;
    }

    void print() {
        for (int i = 0; i < currentSize; i++) {
            std::cout << array[i] << " ";
        }
        std::cout << std::endl;
        std::cout<<"current size: "<<currentSize<<std::endl;
        std::cout<<"capacity: "<<capacity<<std::endl;
        std::cout << std::endl;
    }
     ~DynamicArray() {
        delete[] array;
    }
};

int main(int argc, char const *argv[])
{
    std::cout<<"arrDefault: ";
    DynamicArray arrDefault;
    arrDefault.print();

    std::cout<<"arrWithSize: ";
    DynamicArray arrWithSize(3);
    arrWithSize.print();

    std::cout<<"arrWithRepeativeValue: ";
    DynamicArray arrWithRepeativeValue(3, 7);
    arrWithRepeativeValue.print();

    std::cout<<"arrWithValues: ";
    DynamicArray arrWithValues(3, new int[3]{1, 2, 3});
    arrWithValues.print();

    std::cout<<"arrCopy: ";
    DynamicArray arrCopy(arrWithValues);
    arrCopy.print();

    arrDefault.pushback(1);
    arrDefault.print();

    arrWithValues.pushback(1);
    arrWithValues.print();

    arrWithValues.popback();
    arrWithValues.print();

    std::cout<<"--------------"<<std::endl;
    arrWithValues.insertAt(3,4);
    arrWithValues.print();
    std::cout<<"----------------"<<std::endl;

    arrWithValues.insertMiddle(5);
    arrWithValues.print();

    arrWithValues.removeMiddle();
    arrWithValues.print();

    arrWithValues.removeAt(2);
    arrWithValues.print();

    return 0;
}
