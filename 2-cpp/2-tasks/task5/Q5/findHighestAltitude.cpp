class Solution {
public:
    int largestAltitude(vector<int>& gain) {
        int output{};
        int greatest{};
        
        for(auto element: gain){
            output+=element;
            if(output>greatest){
                greatest=output; 
            }
        }
        return greatest;
    }
};