#include <iostream>
#include <thread>
#include <chrono>
#include <cstdlib>

bool stopFlag = false;

void beepEveryNSeconds(int seconds) {
    while (!stopFlag) {
        std::this_thread::sleep_for(std::chrono::seconds(seconds));
        if (!stopFlag) {
            std::cout << '\a' << std::flush; 
        }
    }
}

int main() {
    int seconds;
    std::cout << "Enter the interval in seconds for the beep: ";
    std::cin >> seconds;

    std::jthread beepThread(beepEveryNSeconds, seconds);

    std::cout << "Press Enter to stop the beep...\n";
    std::cin.ignore();  // Ignore the leftover newline from the previous input
    std::cin.get();

    stopFlag = true;
    
    std::cout << "Beep stopped.\n";

    return 0;
}
