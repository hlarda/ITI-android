#include<iostream>

enum class Type {
    INT,
    DOUBLE,
    CHAR
};


class VoidPtrArray {
    void** arr;
    Type* types; 
    int size;
public:
    VoidPtrArray(int size) : size(size) {
        arr = new void*[size];
        types = new Type[size]; 
    }
    void set(int index, void* value, Type type) {
        arr[index] = value;
        types[index] = type; 
    }
    Type getType(int index) {
        return types[index]; 
    }
    ~VoidPtrArray() {
       for (int i = 0; i < size; ++i) {
            switch (types[i]) {
                case Type::INT:
                    delete static_cast<int*>(arr[i]);
                    break;
                case Type::DOUBLE:
                    delete static_cast<double*>(arr[i]);
                    break;
                case Type::CHAR:
                    delete static_cast<char*>(arr[i]);
                    break;
            }
        }
        delete[] arr;
        delete[] types;
    }
};

std::ostream& operator<<(std::ostream& os, Type type) {
    switch (type) {
        case Type::INT: os << "INT"; break;
        case Type::DOUBLE: os << "DOUBLE"; break;
        case Type::CHAR: os << "CHAR"; break;
    }
    return os;
}

int main(int argc, char const *argv[]){
    VoidPtrArray arr(3);
    int a = 1;
    double b = 2.2;
    char c = 'c';
    arr.set(0, &a, Type::INT);
    arr.set(1, &b, Type::DOUBLE);
    arr.set(2, &c, Type::CHAR);

    std::cout << (arr.getType(0)) << std::endl;;
    std::cout << (arr.getType(1)) << std::endl;;
    std::cout << (arr.getType(2)) << std::endl;;

    return 0;
}