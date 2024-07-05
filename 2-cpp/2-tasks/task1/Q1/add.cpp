#include<iostream>
using namespace std;

int main(int argc, char const *argv[]){
    int input{}, sum{};

    cout << "Please Enter Number:"<<endl;
    cin  >> input;

    while (input != 0){
        sum += input;
        cout    << "Please Enter New Number:"<<endl;
        cin     >> input;
    }

    cout << "The Sum of the Numbers is: "<< sum << endl;
    return 0;
}
