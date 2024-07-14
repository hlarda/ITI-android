# Leetcode Tasks

## Q3: Leetcode: Design Parking System

```cpp
class ParkingSystem {
private:
int big{}, medium{}, small{};
public:
    ParkingSystem()=default;
    ParkingSystem(int big, int medium, int small):big(big), medium(medium), small(small){}
    
    bool addCar(int carType) {
        bool status=true;
        switch (carType){
            case 1:
                if(big > 0){
                    status=true;
                    big--;
                }else{
                    status=false;
                }
            break;
            case 2:
                if(medium > 0){
                    status=true;
                    medium--;
                }else{
                    status=false;
                }
            break;
            case 3:
                if(small > 0){
                    status=true;
                    small--;
                }else{
                    status=false;
                }
            break;
        }
        return status;
    }
};
```

## Q4: Leetcode: Add Digits

```cpp
class Solution {
public:
    int addDigits(int num) {
        while (num >= 10) {
            int sum = 0;
            while (num > 0) {
                sum += num % 10;
                num /= 10;
            }
            num = sum;
        }
        return num;
    }
};
```
