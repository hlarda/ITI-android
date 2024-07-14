#include <iostream>
#include <vector>

std::vector<std::vector<int>> transpose(std::vector<std::vector<int>> & matrix){
    int row = matrix.size();
    int col = matrix[0].size();

    std::vector<std::vector<int>> trans(col, std::vector<int>(row));

    for(int i=0 ; i<row ; i++){
        for(int y=0 ; y<col ; y++){
            trans[y][i]=matrix[i][y];
        }
    }
    return trans;
}

int main(int argc, char const *argv[]){
    std::vector<std::vector<int>> matrix{ { 1, 2, 3 ,5}, 
                         { 4, 5, 6 ,6}, 
  
                       { 7, 8, 9 ,5} }; 

    int row = matrix.size();
    int col = matrix[0].size();
    
    matrix = transpose(matrix);
    
    for(int i=0 ; i<col ; i++){
        for(int y=0 ; y<row ; y++){
            std::cout<<matrix[i][y]<<" ";
        }
        std::cout<<std::endl;
    }
    return 0;
}