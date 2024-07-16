#include <iostream>
#include <fstream>
#include <vector>
#include <iomanip>
#include <limits> 

int main() {
    std::ifstream inputFile("input.txt");
    std::ofstream outputFile("output.txt");
    
    if (!inputFile.is_open()) {
        std::cerr << "Error opening input file" << std::endl;
        return 1;
    }
    if (!outputFile.is_open()) {
        std::cerr << "Error opening output file" << std::endl;
        return 1;
    }
    
    std::vector<int> numbers;
    int number;
    while (inputFile >> number) {
        if (number <= 1000000) {
            numbers.push_back(number);
        } else {
            std::cerr << "Error: Input value exceeds 1,000,000" << std::endl;
            return 1;
        }
    }
    inputFile.close();
    
    if (numbers.empty()) {
        outputFile << "No data to process" << std::endl;
        outputFile.close();
        return 0;
    }
    
    int sum = 0;
    int min = std::numeric_limits<int>::max();
    int max = std::numeric_limits<int>::min();
    
    for (int num : numbers) {
        sum += num;
        if (num < min) min = num;
        if (num > max) max = num;
    }
    
    double average = static_cast<double>(sum) / numbers.size();
    
    int width = 12;
    
    outputFile << std::setfill('-') << std::setw(width * 4 + 13) << "" << std::setfill(' ') << std::endl;
    outputFile << "| "  << std::left << std::setw(width) << "Sum"
               << " | " << std::left << std::setw(width) << "Avg"
               << " | " << std::left << std::setw(width) << "Min"
               << " | " << std::left << std::setw(width) << "Max"
               << " |"  << std::left << std::endl;
    outputFile << std::setfill('-') << std::setw(width * 4 + 13) << "" << std::setfill(' ') << std::endl;
    
    outputFile << "| "  << std::left << std::setw(width) << sum
               << " | " << std::left << std::setw(width) << std::fixed << std::setprecision(2) << average
               << " | " << std::left << std::setw(width) << min
               << " | " << std::left << std::setw(width) << max
               << " |"  << std::left << std::endl;
    outputFile << std::setfill('-') << std::setw(width * 4 + 13) << "" << std::setfill(' ') << std::endl;
    
    outputFile.close();
    return 0;
}
