## build for multiple targets
## RUN: $ make target=pi
target ?= host
SRC = main.c add.c

ifeq ($(target),pi)
	CC = arm-linux-gnueabihf-gcc
else ifeq ($(target),host)
	CC = gcc
endif

all: $(SRC)
	$(CC) $^ -o output
clean:
	@rm -f output

## @ before a command will prevent it from being printed to the console