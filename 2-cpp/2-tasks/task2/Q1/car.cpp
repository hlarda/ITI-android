#include <iostream>
#include <string>

class car{
private:
    std::string company, model;
    int year;
public:
    car(std::string company, std::string model, int year)
        :company(company), model(model), year(year){}

    car() : company(""), model(""), year(0) {}

    ~car() {
        std::cout << "Bye" << std::endl;
    }

    void setCompany(std::string company) {
        this->company = company;
    }

    void setModel(std::string model) {
        this->model = model;
    }

    void setYear(int year) {
        this->year = year;
    }

    std::string getCompany() {
        return company;
    }

    std::string getModel() {
        return model;
    }

    int getYear() {
        return year;
    }

    void print() {
        std::cout << company << "    " << model << "    " << year << std::endl<<std::endl;
    }
};

int main(){
    car c1("Toyota", "Corolla", 2020);
    std::cout << c1.getCompany() << "    " << c1.getModel() << "    " << c1.getYear() << std::endl<< std::endl;
    
    car c2;
    c2.setCompany("Honda");
    c2.setModel("Civic");
    c2.setYear(2019);
    c2.print();
    return 0;
}