#include <bits/stdc++.h>
using namespace std;

int binarySearch(int arr[], int low, int high, int x){
    if (high >= low) {
        int mid = low + (high - low) / 2;
        if (arr[mid] == x)
            return mid;

        if (arr[mid] > x)
            return binarySearch(arr, low, mid - 1, x);

        return binarySearch(arr, mid + 1, high, x);
    }
    return -1;
}

int main(int argc, char const *argv[]){
    int arr[] = { 1, 2, 3, 4, 5 };
    int length = sizeof(arr) / sizeof(arr[0]);

    int result = binarySearch(arr, 0, length - 1, 1);
    (result == -1)
        ? cout << "NOT FOUND"<< endl
        : cout << "Index: " << result<<endl;

    result = binarySearch(arr, 0, length - 1, 10);
    (result == -1)
        ? cout << "NOT FOUND"<< endl
        : cout << "Index: " << result<<endl;

    return 0;
}