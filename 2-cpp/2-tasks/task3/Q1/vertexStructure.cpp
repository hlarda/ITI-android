#include<iostream>

struct Vertex{
    int x, y;
};

void setRandomVertex(Vertex& v){
    v.x = (rand()%201)-100;
    v.y = (rand()%201)-100;
}

void printVertex(const Vertex& v){
    std::cout << "(" << v.x << ", " << v.y << ")" << std::endl;
}

int main(){
    Vertex v;

    setRandomVertex(v);
    printVertex(v); 

    return 0;
}