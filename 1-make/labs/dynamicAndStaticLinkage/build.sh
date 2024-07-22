#!/bin/bash

# Usage function
usage() {
    echo "Usage: $0 {static|dynamic|clean|all|run_static|run_dynamic|help} [compiler]"
    exit 1
}

# Check for at least one argument
if [ $# -lt 1 ]; then
    usage
fi

# Set the target and compiler
TARGET=$1
COMPILER=${2:-gcc}

# Export the compiler to the Makefile
export CC=$COMPILER

# Handle the targets
case "$TARGET" in
    static)
        make static
        ;;
    dynamic)
        make dynamic
        ;;
    clean)
        make clean
        ;;
    all)
        make all
        ;;
    run_static)
        make run_static
        ;;
    run_dynamic)
        make run_dynamic
        ;;
    help)
        make help
        ;;
    *)
        usage
        ;;
esac
