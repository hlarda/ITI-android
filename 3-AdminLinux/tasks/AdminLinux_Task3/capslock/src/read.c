
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(){
    int fdCaps,fdOutput,status;
    int bufferSize=1;
    char buffer[bufferSize];

    fdCaps = open("/sys/class/leds/input4::capslock/brightness", O_RDONLY);
    if(fdCaps == -1){
        perror("fdCaps: ");
        exit(1);
    }else{
        printf("fdCaps: %d\n", fdCaps);
    }

    fdOutput = open("fdOutput", O_RDWR|O_CREAT, 0664);
    if(fdOutput == -1){
        perror("fdOutput: ");
        exit(1);
    }else{
        printf("fdOutput: %d\n", fdOutput);
    }

    perror("Openning:");
    printf("\n");

    if(read(fdCaps, buffer, 1) <= 0){
        perror("read: ");
        exit(1);
    }else{
        printf("capsLock: %c\n", buffer[0]);
    }

    if(write(fdOutput, buffer, 1) <= 0){
        perror("write: ");
        exit(1);
    }else{
        printf("Output: %c\n", buffer[0]);
    }

    perror("Writing And Reading:");
    printf("\n");
    
    if(close(fdCaps)<0){
        perror("fdCaps: ");
        exit(1);
    }else{
        printf("fdCaps closed\n");
    }
    if(close(fdOutput)<0){
        perror("fdOutput: ");
        exit(1);
    }else{
        printf("fdOutput closed\n");
    }
	return 0;
}
