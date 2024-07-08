#include<iostream>  

template <typename T>
class Pair {
private:
    T first;
    T second;
public:
    Pair(T first, T second) : first(first), second(second) {}

    T getFirst() const { return first; }
    T getSecond() const { return second; }
};

int main(int argc, char const *argv[])
{
    Pair<int> p1(1, 2);
    Pair<double> p2(1.1, 2.2);
    Pair p3("Hello", "World");

    std::cout << p1.getFirst() << " " << p1.getSecond() << std::endl;
    std::cout << p2.getFirst() << " " << p2.getSecond() << std::endl;
    std::cout << p3.getFirst() << " " << p3.getSecond() << std::endl;
    return 0;
}
