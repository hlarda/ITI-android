#include <stdio.h>
#include <stdlib.h>

int* sum(int* arr, int n);
int* matrix(int** arr, int rows, int* cols, int* (*sum)(int*, int));

typedef int* (*matrixPtr)(int** arr, int rows, int* cols, int* (*sum)(int*, int));
typedef int* (*sumPtr)(int*, int);

int main(int argc, char const *argv[])
{
    matrixPtr matrixFunc = matrix;
    sumPtr sumFunc = sum;

    int rows;

    printf("Enter the number of rows: \n");
    scanf("%d", &rows);
    int* cols = malloc(sizeof(int) * rows);
    int** matrixaia = malloc(rows * sizeof(int*));

    for(int i = 0; i < rows; i++){
        printf("Enter the number of columns for row %d: \n", i);
        scanf("%d", &cols[i]);
        int* row = (int*)malloc(sizeof(int) * cols[i]);
        for (int j = 0; j < cols[i]; j++){
            printf("Enter the value for row %d and column %d: \n", i, j);
            scanf("%d", &row[j]);
        }
        matrixaia[i] = row;
    }

    int* result = matrixFunc(matrixaia, rows, cols, sumFunc);
    
    for (int i = 0; i < rows; i++) {
        printf("Sum of row %d: %d\n", i, result[i]);
    }

    for (int i = 0; i < rows; i++) {
        free(matrixaia[i]);
    }
    free(matrixaia);
    free(cols);
    free(result);

    return 0;
}

int* sum(int* arr, int n){
    int* total = (int*)malloc(sizeof(int));
    *total = 0;
    for (int i = 0; i < n; i++){
        *total += arr[i];
    }
    return total;
}

int* matrix(int** arr, int rows, int* cols, int* (*sum)(int*, int)){
    int* result = (int*)malloc(sizeof(int) * rows);
    for (int i = 0; i < rows; i++){
        result[i] = *(sum(arr[i], cols[i]));
    }
    return result;
}
