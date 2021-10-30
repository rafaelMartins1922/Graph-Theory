#include<iostream>
#include<stdlib.h>
#include<stdio.h>
#include<fstream>
#include<string>
#include<sstream>
#include<vector>

int main(int argc, char *argv[]){
    char *fileName = argv[1];
    int a,b;
    std::stringstream ss;
    if(argv[1] == NULL){
        std::cout<<"Nenhum arquivo foi informado"<<std::endl;
        return 1;
    } else {
        std::cout<<"Arquivo a ser aberto: "<< argv[1]<<std::endl;
    }
    std::string line;
    std::ifstream file(fileName);
    if(!file.is_open()){
        std::cout<<"Não foi encontrado o arquivo"<<std::endl;
        return 1;
    } else {
        getline(file, line);
        int numberOfLines = std::atoi(line.c_str());
        for(int i = 0; i < numberOfLines; i++){
            ss.clear();
            getline(file, line);
            ss<<line;
            ss>>a>>b;
            std::cout << a<<" " << b << std::endl;
    }
    return 0;
}