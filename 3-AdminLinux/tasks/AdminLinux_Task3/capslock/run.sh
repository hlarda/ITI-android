#!/bin/bash

gcc ./src/read.c -o ./src/read
./src/read
sleep 2

gcc ./src/toggle.c -o ./src/toggle 
./src/toggle

rm ./src/read 
rm ./src/toggle 
rm fdOutput