#include<iostream>

enum class ErrorCode{
    badRequest = 400,
    notFound=404,
    serverError=500,
    gateway_timeout=504
};

void printErrot(ErrorCode code){
    switch (code){
        case ErrorCode::badRequest:
            std::cout << "Bad Request" << std::endl;
            break;
        case ErrorCode::notFound:
            std::cout << "Not Found" << std::endl;
            break;
        case ErrorCode::serverError:
            std::cout << "Server Error" << std::endl;
            break;
        case ErrorCode::gateway_timeout:
            std::cout << "Gateway Timeout" << std::endl;
            break;
        default:
            std::cout << "Unknown Error" << std::endl;
            break;
    }
}

int main(int argc, char const *argv[]){
    int code{};
    std::cout << "Please Enter Error Code:"<<std::endl;
    std::cin  >> code;
    printErrot(static_assert<ErrorCode>(code));
    return 0;
}