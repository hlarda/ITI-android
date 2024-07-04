#include<stdio.h>
#include<stdlib.h>


void print(char** strings, int stringsCount){
    for(int i = 0; i < stringsCount; i++){
        printf("String %d: %s\n", i, strings[i]);
    }
}
void delete(char** strings, int index, int* stringsCount){
    for(int i = index; i < *stringsCount - 1; i++){
        strings[i] = strings[i + 1];
    }
    print(strings, *stringsCount - 1);
}

void insert(char** strings, int index, int* stringsCount){
    char* string = (char*)malloc(100 * sizeof(char));
    printf("Enter the string to insert: \n");
    scanf("%s", string);
    strings = (char**)realloc(strings, (*stringsCount + 1) * sizeof(char*));
    for(int i = *stringsCount; i > index; i--){
        strings[i] = strings[i - 1];
    }
    strings[index] = string;
    (*stringsCount)++;
    print(strings, *stringsCount);
}

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

    printf("Do you want to 1. delete or 2. insert a string? \n");
    int choice;
    scanf("%d", &choice);
    if (choice == 1){
        printf("Enter the index of the string to delete: \n");
        int index;
        scanf("%d", &index);
        if(index < stringsCount && index >= 0){
            delete(strings, index, &stringsCount);
        }else{
            printf("Invalid index\n");
        }
    } else if (choice == 2){
        printf("Enter the index of the string to insert: \n");
        int index;
        scanf("%d", &index);
        if(index >= 0 && index <= stringsCount){
            insert(strings, index, &stringsCount);
        }else{
            printf("Invalid index\n");
        }
    }

    printf("total size: %d\n", stringsCount);

    for(int i = 0; i < stringsCount; i++){
        free(strings[i]);
    }
    return 0;
}
