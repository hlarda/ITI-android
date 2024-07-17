#include <iostream>
#include <vector>
#include <string>
#include <cstdlib>
#include <limits>

class Battleship {
private:
    std::vector<std::vector<bool>> board;
    int guesses{};
    int maxGuessAllowed{};
    int locationx{}, locationy{};

public:
    Battleship(int maxGuessAllowed) : board(5, std::vector<bool>(5, false)), maxGuessAllowed{maxGuessAllowed} {}

    void setShip() {
        locationx = std::rand() % 5;
        locationy = std::rand() % 5;
        board[locationx][locationy] = true;
    }

    int getGuesses() const {
        return guesses;
    }

    bool gameOver() const {
        return (guesses >= maxGuessAllowed);
    }

    void guess() {
        int x{}, y{};
        while (!gameOver()) {
            try {
                std::cout << "Enter the x and y coordinates (0-4): " << std::endl;
                std::cin >> x >> y;

                if (std::cin.fail()) {
                    throw std::runtime_error("Invalid input. Please enter numbers only.");
                }

                if (x < 0 || x >= 5 || y < 0 || y >= 5) {
                    throw std::out_of_range("Coordinates out of bounds. Please enter numbers between 0 and 4.");
                }

                guesses++;
                if (board[x][y]) {
                    std::cout << "You hit the ship!" << std::endl;
                    break;
                } else {
                    std::cout << "Try Again!" << std::endl;
                    if (std::abs(locationx - x) == 1 && locationy == y) {
                        std::cout << "You are close to x. ";
                    }
                    if (std::abs(locationy - y) == 1 && locationx == x) {
                        std::cout << "You are close to y. ";
                    }
                    if (locationx == x && locationy != y) {
                        std::cout << "x is right. ";
                    }
                    if (locationy == y && locationx != x) {
                        std::cout << "y is right. ";
                    }
                    std::cout << std::endl;
                }

            } catch (const std::exception& e) {
                std::cout << e.what() << std::endl;

                std::cin.clear(); 
                std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
            }
        }
    }
};

int main() {
    Battleship b1(5);
    b1.setShip();
    b1.guess();
    return 0;
}
