#include<iostream>

class Pair{
private:
    int first, second;
public:
    Pair():first(0), second(0){}
    Pair(int first, int second):first(first), second(second){}
    ~Pair(){
        std::cout << "Bye" << std::endl;
    }
    
    void setFirst(int first) {
        this->first = first;
    }
    
    void setSecond(int second) {
        this->second = second;
    }
    
    int getFirst() {
        return first;
    }
    
    int getSecond() {
        return second;
    }
    
    void setPair(int first, int second) {
        this->first = first;
        this->second = second;
    }
    
    void getPair(int first, int second) {
        first = this->first;
        second = this->second;
    }

    void swap(Pair &pairaa){
        std::swap(pairaa.first, pairaa.second);
    }

    void print() {
        std::cout << first << "    " << second << std::endl;
    }
};

int main() {
    Pair pair1;
    Pair pair2(1, 2);
    pair1.print();
    pair2.print();
    pair1.swap(pair2);
    pair2.print();
    return 0;
}