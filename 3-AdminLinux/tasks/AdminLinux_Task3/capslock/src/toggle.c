
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(){
    char buffer[1];
    int fdTog, fdCaps;

    fdTog = open("fdOutput", O_RDONLY);
    if(fdTog < 0){
        perror("fdTog: ");
        exit(1);
    }else{
        printf("fdTog: %d\n", fdTog);
    }

    fdCaps = open("/sys/class/leds/input4::capslock/brightness", O_RDWR);
    if(fdCaps == -1){
        perror("fdCaps: ");
        exit(1);
    }else{
        printf("fdCaps: %d\n", fdCaps);
    }

    perror("Openning:");
    printf("\n");

    read(fdTog, buffer, 1);
    printf("Before: %c\n", buffer[0]);
    
    buffer[0] = (buffer[0] == '0') ? '1' : '0'; 

    if(write(fdCaps, buffer, 1) <= 0){
        perror("write: ");
        exit(1);
    }else{
        printf("Checkout Capslock Led NOW\n");
    }

    if(close(fdCaps)<0){
        perror("fdCaps: ");
        exit(1);
    }else{
        printf("fdCaps closed\n");
    }
    if(close(fdTog)<0){
        perror("fdTog: ");
        exit(1);
    }else{
        printf("fdTog closed\n");
    }
	return 0;
}
