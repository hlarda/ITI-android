# Make + Cross-Compilation

## Objective

Create a Makefile and Bash script to compile and link a calculator application. The application should support both static and dynamic linking.

## Build the Project

- `./build.sh static`       --> build static
- `./build.sh dynamic`      --> build dynamic
- `./build.sh clean`        --> clean the project
- `./build.sh run_static`   --> run the static build
- `./build.sh run_dynamic`  --> run the dynamic build
- `./build.sh help`         --> show help
  
after runnimg file use `file` command with each exectable to figure out whether it is dynamically or statically linked.
