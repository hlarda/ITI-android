#include<iostream>

namespace DynamicAlloc{
    void create2Darray(int** &arr, int rows, int cols){
        arr = new int*[rows];
        for(int i=0; i<rows; i++){
            arr[i] = new int[cols];
        }
    }
    void delete2Darray(int** &arr, int rows){
        for(int i=0; i<rows; i++){
            delete[] arr[i];
        }
        delete[] arr;
    }
}

int main(int argc, char const *argv[])
{
    int** arr;

    DynamicAlloc::create2Darray(arr, 3, 3);
    for(int i=0; i<3; i++){
        for(int j=0; j<3; j++){
            arr[i][j] = i;
        }
    }
    for(int i=0; i<3; i++){
        for(int j=0; j<3; j++){
            std::cout<<arr[i][j]<<"    ";
        }
        std::cout<<std::endl;
    }
    DynamicAlloc::delete2Darray(arr, 3);
    return 0;
}
