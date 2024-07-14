#include <iostream>
#include <string>

std::string replaceWith(std::string str, std::string_view newWord, std::string_view oldWord){
    int pos = str.find(oldWord);
    while(pos != std::string::npos){
        str.replace(pos, oldWord.size(), newWord);
        pos = str.find(oldWord);
    }
    return str;
}

int main(){
    std::string s{}, newWord{}, oldWord{};
    std::cout << "Enter a string: ";
    std::getline(std::cin, s);
    std::cout << "Enter the word to replace: ";
    std::cin >> oldWord;
    std::cout << "Enter the new word: ";
    std::cin >> newWord;
    std::cout << "New string: " << replaceWith(s, newWord, oldWord) << std::endl;
    return 0;
}