#include <iostream>
using namespace std;

int main(int argc, char const *argv[]){
    int input{}, hours{}, minutes{}, seconds{};

    cout << "Please Enter Number of Seconds:"<<endl;
    cin  >> input;

    hours   = input / 3600;
    minutes = (input%3600) / 60;
    seconds = input % 60;

    cout << "\nInput seconds: " << input << endl;
    cout << "H:M:S - " << hours << ":" << minutes << ":" << seconds << endl;

    return 0;
}