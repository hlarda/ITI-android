#include<iostream>
#include<utility>

namespace ArrayPair{
    std::pair<int, int>* arrpair;

    void createPairArray(int size, int* first, int* second){
        arrpair = new std::pair<int, int>[size];
        for(int i=0; i<size; i++){
            arrpair[i] = std::make_pair(first[i], second[i]);
        }
    }
    void deletePairArray(int size, std::pair<int, int>* arrpair){
        delete[] arrpair;
    }
    void setPair(int index, std::pair<int, int> pairr){
        arrpair[index] = pairr;
    }
    std::pair<int, int> getPair(int index){
        return arrpair[index];
    }
    void printPairArray(int size){
        for(int i=0; i<size; i++){
            std::cout<<arrpair[i].first<<"    "<<arrpair[i].second<<std::endl;
        }
    }
}
int main(int argc, char const *argv[])
{
    int first[5] = {1, 2, 3, 4, 5};
    int second[5] = {6, 7, 8, 9, 10};

    ArrayPair::createPairArray(5, first, second);
    std::cout<<std::endl;
    ArrayPair::printPairArray(5);
    std::cout<<std::endl;
    ArrayPair::deletePairArray(5, ArrayPair::arrpair);
    ArrayPair::setPair(0, std::make_pair(1, 2));
    ArrayPair::getPair(0);
    std::cout<<std::endl;
    ArrayPair::printPairArray(5);

    return 0;
}
