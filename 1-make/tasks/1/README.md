# Simple Build Automation Program

## Description

Create a Makefile to manage a basic C application project with source files organized into directories (src for .c files and include for .h files). The Makefile should automate the compilation and linking process while maintaining directory structure integrity.

## Makefile specifications

1. compiles for more than one target based on the value of the `CC` variable passed through cmd and gcc by default.

2. checks for the existence of the directories' structure and create them if they don't exist.

3. uses pattern rules and some built-in variables and functions such as `wildcard`, `patsubst` and `foreach` 4. to automate the compilation and linking process.

4. used automatic variables such as `$@`, `$<` and `$^` to simplify the rules.

5. after the compilation, the executable is placed in `bin` and the object files are placed in `obj` directories.

## Usage

```bash
make CC=gcc
make CC=arm-linux-gnueabihf-gcc

make clean

make help
```
