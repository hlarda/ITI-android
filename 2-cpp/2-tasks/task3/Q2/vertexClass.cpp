#include<iostream>

class Vertex{
    private:
        int x{}, y{};
    public:
        Vertex() = default;
        Vertex(int x, int y): x(x), y(y){}
        Vertex(const Vertex& other): x(other.x), y(other.y){}

        int getX() const {return x;}
        int getY() const {return y;}

        void setRandom(){
            x = (rand() % 201)-100;
            y = (rand() % 201)-100;
        }

        char* print()const{
            char* str = new char[10];
            sprintf(str, "Vertex(%d, %d)", x, y);
            return str;
        }
};

int main(){
    Vertex v1;
    v1.setRandom();
    std::cout << v1.print() << std::endl;

    Vertex v2 = v1;
    std::cout << v2.print() << std::endl;

    Vertex v3(10, 20);
    std::cout << v3.print() << std::endl;

    return 0;
}