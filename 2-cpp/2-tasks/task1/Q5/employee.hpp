#include <iostream>
using namespace std;

typedef struct Name_t{
    string first{};
    string middle{};
    string last{};
} Name_t;

typedef struct BirthDate_t{
    int day{};
    int month{};
    int year{};
} BirthDate_t;

typedef struct Address_t{
    string street{};
    string city{};
    string country{};
} Address_t;

typedef struct Contact_t{
    string telephone{};
    string mobile{};
    string email{};
} Contact_t;

typedef struct Salary_t{
    float basic{};
    float additional{};
    float reductions{};
    float taxes{};
} Salary_t;

typedef struct Employee_t{
    int         id{};
    Name_t      name{};
    BirthDate_t birthDate{};
    Address_t   address{};
    Contact_t   contact{};
    string      job;
    Salary_t    salary{};
} Employee_t;
