#include<iostream>

class calculator{
private:
    int num1, num2;
public:
    calculator()
        :num1(0), num2(0){}
    ~calculator() {
        std::cout << "Bye" << std::endl;
    }
    
    void setNum1(int num1) {
        this->num1 = num1;
    }
    
    void setNum2(int num2) {
        this->num2 = num2;
    }
    
    int getNum1() {
        return num1;
    }
    
    int getNum2() {
        return num2;
    }
    
    int add(int num1, int num2) {
        return num1 + num2;
    }

    int sub(int num1, int num2) {
        return num1 - num2;
    }

    int mul(int num1, int num2) {
        return num1 * num2;
    }

    int div(int num1, int num2) {
        return num1 / num2;
    }

    int sqrt(int num1) {
        return num1 * num1;
    }

    void print() {
        std::cout << num1 << "    " << num2 << std::endl<<std::endl;
    }
};

int main(int argc, char const *argv[]){

    int num1, num2;
    char op;
    calculator calc;

    std::cout<<"Enter operation to perform (+, -, *, / , sqrt()'S' ";
    std::cin>>op;

    if(op == 'S' || op == 's'){
        std::cout<<"Enter number: ";
        std::cin>>num1;
        if(num1 < 0){
            std::cout<<"Invalid input"<<std::endl;
            return 0;
        }else{
            std::cout<<"Square root of "<<num1<<" is "<<num1*num1<<std::endl;
        }
        
    }else{
        std::cout<<"Enter two numbers: ";
        std::cin>>num1>>num2;
        switch (op)
        {
        case '+':
            std::cout<<"Sum of "<<num1<<" and "<<num2<<" is "<<calc.add(num1, num2)<<std::endl;
            break;
        case '-':
            std::cout<<"Difference of "<<num1<<" and "<<num2<<" is "<<calc.sub(num1, num2)<<std::endl;
            break;
        case '*':
            std::cout<<"Product of "<<num1<<" and "<<num2<<" is "<<calc.mul(num1, num2)<<std::endl;
            break;
        case '/':
            if(num2 == 0){
                std::cout<<"Invalid input"<<std::endl;
            }else{
                std::cout<<"Division of "<<num1<<" and "<<num2<<" is "<<calc.div(num1, num2)<<std::endl;
            }
            break;
        default:        break;
        }
    }
    return 0;
}
