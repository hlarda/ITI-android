#include<iostream>
#include<string>
#include<cmath>
#include<cctype>

namespace MathFunctions{

    void print(double s){
        std::cout<< sqrt(s)<<std::endl;
    }
}
namespace StringFunctions{
    void print(std::string s, bool isUpper){
        if(isUpper){
            for(char & c : s) c = toupper(c);
        }else{
            for(char & c : s) c = tolower(c);
        }
        std::cout<<s<<std::endl;
    }
}
namespace ArrayFunctions{
    void print(int* arr, int size){
        for(int i=size-1; i>=0; i--){
            std::cout<<arr[i]<<"    ";
        }
        std::cout<<std::endl;
    }
}

int main(int argc, char const *argv[]){
    MathFunctions::print(4);
    StringFunctions::print("Hello", true);
    ArrayFunctions::print(new int[5]{1, 2, 3, 4, 5}, 5);
    return 0;
}
