#include <iostream>
using namespace std;

int anualPopGrowth(int population, double growthRate, int target){
    static int years{0};
    if (population < target){
        population += population * growthRate;
        years++;
        cout << "Year " << years << ": " << population << endl;
        anualPopGrowth(population, growthRate, target);
    }
    return years;
}

int main(int argc, char const *argv[]){
    int population=162100, target=1000000, years{0};
    double growthRate=0.065;

    years = anualPopGrowth(population, growthRate, target);
    cout << "It will take " << years << " years for the population to exceed " << target << endl;

    return 0;
}