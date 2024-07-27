#include <iostream>
#include <ctime>

class Account {
    private:
        int accountNumber{};
        int amount{};
        int numOfDeposits{};
        int numOfWithdrawals{};

        static int numOfAccounts;
        static int totalAmount;
        static int totalNumOfDeposits;
        static int totalNumOfWithdrawals;

        Account(void){}
        static void displayTimeStamp(void){
            std::time_t now = std::time(nullptr);
            std::cout << "Current Time: " << std::ctime(&now);
        }
    public:
        using acc = Account;

        Account(int amount): amount(amount){
            accountNumber = ++numOfAccounts;
            totalAmount += amount;
        }

        ~Account(void){
            numOfAccounts--;
            totalAmount -= amount;
        }

        static int getNumOfAccounts(void){
            return numOfAccounts;
        }
        static int getTotalAmount(void){
            return totalAmount;
        }
        static int getTotalNumOfDeposits(void){
            return totalNumOfDeposits;
        }
        static int getTotalNumOfWithdrawals(void){
            return totalNumOfWithdrawals;
        }

        void deposit(int amount){
            this->amount += amount;
            numOfDeposits++;
            totalAmount += amount;
            totalNumOfDeposits++;
            displayTimeStamp();
        }
        bool withdraw(int amount){
            if (this->amount >= amount){
                this->amount -= amount;
                numOfWithdrawals++;
                totalAmount -= amount;
                totalNumOfWithdrawals++;
                displayTimeStamp();
                return true;
            }
            return false;
        }
        int chkBalance(void) const {
            return amount;
        }
        void displayStatus(void) const{
            std::cout << "Account Number: " << accountNumber << std::endl;
            std::cout << "Amount: " << amount << std::endl;
            std::cout << "Number of Deposits: " << numOfDeposits << std::endl;
            std::cout << "Number of Withdrawals: " << numOfWithdrawals << std::endl;
        }

};
int Account::numOfAccounts         = 0;
int Account::totalAmount           = 0;
int Account::totalNumOfDeposits    = 0;
int Account::totalNumOfWithdrawals = 0;

int main(int argc, char const *argv[]){
    Account acc1(1000);
    Account acc2(2000);

    acc1.deposit(100);
    acc2.deposit(200);

    acc1.withdraw(50);
    acc2.withdraw(100);

    std::cout << "Account 1 Balance: " << acc1.chkBalance() << std::endl;
    std::cout << "Account 2 Balance: " << acc2.chkBalance() << std::endl;

    acc1.displayStatus();
    acc2.displayStatus();

    std::cout << "Total Number of Accounts: " << Account::getNumOfAccounts() << std::endl;
    std::cout << "Total Amount: " << Account::getTotalAmount() << std::endl;
    std::cout << "Total Number of Deposits: " << Account::getTotalNumOfDeposits() << std::endl;
    std::cout << "Total Number of Withdrawals: " << Account::getTotalNumOfWithdrawals() << std::endl;

    return 0;
}
