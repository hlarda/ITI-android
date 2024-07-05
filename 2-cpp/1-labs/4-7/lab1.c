#include<stdio.h>
#include<stdlib.h>

int main(int argc, char const *argv[]){
    int stringsCount=0;
    printf("Enter the number of strings: \n");
    scanf("%d", &stringsCount);

    char** strings = (char**)malloc(stringsCount * sizeof(char*));

    for (int i = 0; i < stringsCount; i++){
        printf("Enter the string %d: \n", i);
        char* string = (char*)malloc(100 * sizeof(char));
        scanf("%s", string);
        strings[i] = string;
    }
    for(int i = 0; i < stringsCount; i++){
        printf("String %d: %s\n", i, strings[i]);
    }
    for(int i = 0; i < stringsCount; i++){
        free(strings[i]);
    }
    return 0;
}
