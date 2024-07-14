#include<iostream>
#include<string>

int main(int argc, char const *argv[]){
    int pos{};

    std::string str = {"the cycle of life is a cycle of cycles"};

    std::cout << "str: " << str << std::endl << std::endl;

    std::cout << "Replace 'cycle' with 'circle': " << std::endl;

    while (pos = str.find("cycle"), pos != std::string::npos){
        str.replace(pos, 5, "circle");
    }
    std::cout << str << std::endl << std::endl;

    std::cout << "Insert the word 'great' beford the firs circle: " << std::endl;
    pos = str.find("circle");
    str.insert(pos, "great ");
    std::cout << str << std::endl << std::endl;

    std::cout << "Insert the word 'never ending' before the second cycle: " << std::endl;
    pos = str.find("circle", 0);
    pos = str.find("circle", pos + 1);
    str.insert(pos, "never ending ");
    std::cout << str << std::endl;
    
    return 0;
}

