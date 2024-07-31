#include <iostream>
#include <thread>
#include <vector>
#include <mutex>
#include <condition_variable>
#include <numeric>

std::mutex mtx;
std::condition_variable cv;
bool ready = false;
std::vector<int> results(4);

// Function to calculate Fibonacci number
int fibonacci(int n) {
    if (n <= 1) return n;
    return fibonacci(n - 1) + fibonacci(n - 2);
}

// Function to calculate and store a Fibonacci number
void calculate_fibonacci(int index, int n) {
    int result = fibonacci(n);
    std::lock_guard<std::mutex> lock(mtx);
    results[index] = result;
    std::cout << "Fibonacci(" << n << ") = " << result << std::endl;
}

// Function to print the results and their sum
void print_results() {
    std::unique_lock<std::mutex> lock(mtx);
    cv.wait(lock, []{ return ready; });

    std::cout << "Fibonacci results: ";
    for (const int& result : results) {
        std::cout << result << " ";
    }
    std::cout << std::endl;

    int sum = std::accumulate(results.begin(), results.end(), 0);
    std::cout << "Sum of results: " << sum << std::endl;
}

int main() {
    std::vector<int> fib_indices = {4, 9, 14, 17};
    std::vector<std::thread> threads;

    // Create threads to calculate Fibonacci numbers
    for (size_t i = 0; i < fib_indices.size(); ++i) {
        threads.emplace_back(calculate_fibonacci, i, fib_indices[i]);
    }

    // Create a thread to print the results
    std::thread printer_thread(print_results);

    // Wait for all threads to finish
    for (auto& th : threads) {
        th.join();
    }

    // Notify the printer thread that results are ready
    {
        std::lock_guard<std::mutex> lock(mtx);
        ready = true;
    }
    cv.notify_one();
    printer_thread.join();

    return 0;
}
