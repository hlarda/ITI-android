#include<iostream>
#include<bitset>
using namespace std;

int main(int argc, char const *argv[])
{
    int option{};
    string input{};

    while (1){
        cout << "convert from:  1. binary to decimal       2. decimal to binary        3. exit\n";
        cin  >> option;

        if (option != 1 && option != 2 && option != 3){
            cout << "invalid option\n";
            continue;
        }else if (option == 3){
            break;
        }else if (option ==1){
            cout << "Enter binary number: ";
            cin  >> input;
            cout << "Decimal: " << bitset<16>(input).to_ulong() << endl;
            
        }else if (option == 2){
            cout << "Enter decimal number: ";
            cin  >> input;
            cout << "Binary: " << bitset<16>(stoi(input)).to_string() << endl;
        }
        cout << "-----------------------------------------------------------------------------" << endl;
    }
    
    return 0;
}
