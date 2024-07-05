#include <iostream>
#include <cmath>
using namespace std;

int main() {
    int population=162100, target=1000000, years{0};
    double growthRate=0.065;
    
    /*  using formula of compound interests: A = P * (1 + r/n)^(nt)   */
    /*  A:target      P:initial amount        n:times     r:rate      */
    years = ceil(log(target / population) / log(1 + growthRate));
    cout << "The population will surpass " << target << " in approximately " << years << " years." << std::endl;

    return 0;
}