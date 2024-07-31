#include <iostream>

class Time
{
private:
    int hours{}, minutes{}, seconds{};
public:
    Time(void): hours(0), minutes(0), seconds(0) {};
    Time(int h, int m, int s): hours(h), minutes(m), seconds(s) {};
    ~Time()=default;

    void display(void) const {
        std::cout << hours << ":" << minutes << ":" << seconds << std::endl;
    }

    Time add(Time t) {
        Time temp;
        temp.seconds = seconds + t.seconds;
        temp.minutes = minutes + t.minutes + temp.seconds / 60;
        temp.hours = hours + t.hours + temp.minutes / 60;
        temp.seconds %= 60;
        temp.minutes %= 60;
        return temp;
    }
};

int main(int argc, char const *argv[])
{
    Time t1(2, 45, 30);
    Time t2(3, 30, 30);
    Time t3;

    t3 = t1.add(t2);
    t3.display();
    return 0;
}

