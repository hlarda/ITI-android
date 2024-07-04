.PHONY: all clean run

CC ?= gcc

project=task1
src=$(wildcard ./src/*.c)
SRC=./src/
INCS=./include/
BIN=./bin/
BUILD=./build/

all: $(project)

chkDirs:
	@$(foreach dir,$(BIN) $(BUILD),mkdir -p $(dir);)

$(BIN)%.o: $(SRC)%.c | chkDirs
	@$(CC) -I $(INCS) -c $< -o $@

$(project): $(patsubst $(SRC)%.c,$(BIN)%.o,$(src)) | chkDirs
	@$(CC) -I $(INCS) $^ -o $(BUILD)$@

clean:
	@rm -f $(BIN)*
	@rm -f $(BUILD)*
	@echo "Cleaned up the project"

run:
	@$(BUILD)$(project)

help:
	@echo "make all: to build the project"
	@echo "make run: to run the project when native target is host"
	@echo "make clean: to clean the project"
	@echo "make help: to display this help message"
	@echo "use (make CC=<compiler>) to build for a specific target"