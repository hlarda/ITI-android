#include <iostream>
#include <stdexcept>

template <typename T>
class DynamicArray {
private:
    T* array;
    int capacity;
    int currentSize;

    void resize() {
        capacity = capacity > 0 ? capacity * 2 : 1;
        T* temp = new T[capacity];
        for (int i = 0; i < currentSize; i++) {
            temp[i] = array[i];
        }
        delete[] array;
        array = temp;
    }

public:
    DynamicArray() : capacity(1), currentSize(0), array(new T[capacity]) {}

    DynamicArray(int size) : capacity(size), currentSize(0), array(nullptr) {
        if (size < 1) throw std::invalid_argument("Size must be at least 1");
        array = new T[capacity];
    }

    DynamicArray(int size, T value) : capacity(size), currentSize(size), array(nullptr) {
        if (size < 1) throw std::invalid_argument("Size must be at least 1");
        array = new T[capacity];
        for (int i = 0; i < currentSize; i++) {
            array[i] = value;
        }
    }

    DynamicArray(int size, T* values) : capacity(size), currentSize(size), array(nullptr) {
        if (size < 1) throw std::invalid_argument("Size must be at least 1");
        array = new T[capacity];
        for (int i = 0; i < currentSize; i++) {
            array[i] = values[i];
        }
    }

    DynamicArray(const DynamicArray& arr) : capacity(arr.capacity), currentSize(arr.currentSize), array(new T[capacity]) {
        for (int i = 0; i < currentSize; i++) {
            array[i] = arr.array[i];
        }
    }

    ~DynamicArray() {
        delete[] array;
    }

    void pushback(T value) {
        if (currentSize == capacity) {
            resize();
        }
        array[currentSize++] = value;
    }

    T popback() {
        if (currentSize > 0) {
            return array[--currentSize];
        } else {
            throw std::out_of_range("Array is empty");
        }
    }

    void removeAt(int index) {
        if (index < 0 || index >= currentSize) {
            throw std::out_of_range("Index out of range");
        }
        for (int i = index; i < currentSize - 1; i++) {
            array[i] = array[i + 1];
        }
        currentSize--;
    }

    void insertAt(int index, T value) {
        if (index < 0 || index > currentSize) {
            throw std::out_of_range("Index out of range");
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

    void insertMiddle(T value) {
        insertAt(currentSize / 2, value);
    }

    void removeMiddle() {
        removeAt(currentSize / 2);
    }

    int size() const {
        return currentSize;
    }

    void print() const {
        for (int i = 0; i < currentSize; i++) {
            std::cout << array[i] << " ";
        }
        std::cout << std::endl;
        std::cout << "Current size: " << currentSize << std::endl;
        std::cout << "Capacity: " << capacity << std::endl;
        std::cout << std::endl;
    }
};

int main() {
        DynamicArray<int> arrDefault;
        std::cout << "arrDefault after creation: ";
        arrDefault.print();

        arrDefault.pushback(1);
        arrDefault.pushback(2);
        arrDefault.pushback(3);
        std::cout << "arrDefault after pushback operations: ";
        arrDefault.print();

        DynamicArray<double> arrWithSize(3);
        std::cout << "arrWithSize after creation: ";
        arrWithSize.print();

        arrWithSize.pushback(1.1);
        arrWithSize.pushback(2.2);
        arrWithSize.pushback(3.3);
        std::cout << "arrWithSize after pushback operations: ";
        arrWithSize.print();


        DynamicArray<char> arrWithRepeativeValue(3, 'A');
        std::cout << "arrWithRepeativeValue after creation: ";
        arrWithRepeativeValue.print();


        int vals[] = {1, 2, 3};
        DynamicArray<int> arrWithValues(3, vals);
        std::cout << "arrWithValues after creation: ";
        arrWithValues.print();

        DynamicArray<int> arrCopy(arrWithValues);
        std::cout << "arrCopy after creation: ";
        arrCopy.print();

        int poppedValue = arrWithValues.popback();
        std::cout << "arrWithValues after popback operation (popped value = " << poppedValue << "): ";
        arrWithValues.print();

        arrWithValues.insertAt(1, 99);
        std::cout << "arrWithValues after insertAt operation: ";
        arrWithValues.print();

        arrWithValues.insertMiddle(55);
        std::cout << "arrWithValues after insertMiddle operation: ";
        arrWithValues.print();

        arrWithValues.removeAt(1);
        std::cout << "arrWithValues after removeAt operation: ";
        arrWithValues.print();

        arrWithValues.removeMiddle();
        std::cout << "arrWithValues after removeMiddle operation: ";
        arrWithValues.print();

    return 0;
}
