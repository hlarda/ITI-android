.PHONY: all clean run help static dynamic

CC ?= gcc

project = calculator
src = $(wildcard ./src/*.c)
SRC = ./src/
INCS = ./include/
BIN = ./bin/
BUILD = ./build/
LIB = ./lib/

all: static_exec dynamic_exec

chkDirs:
	@$(foreach dir,$(BIN) $(BUILD) $(LIB),mkdir -p $(dir);)

$(BIN)%.o: $(SRC)%.c | chkDirs
	@$(CC) -I $(INCS) -c $< -o $@

$(LIB)libcalc.a: $(patsubst $(SRC)%.c,$(BIN)%.o,$(src)) | chkDirs
	@ar rcs $@ $^

$(LIB)libcalc.so: $(patsubst $(SRC)%.c,$(BIN)%.o,$(src)) | chkDirs
	@$(CC) -I $(INCS) -fPIC -shared -o $@ $^

static_exec: $(patsubst $(SRC)%.c,$(BIN)%.o,$(src)) $(LIB)libcalc.a | chkDirs
	@$(CC) -static -I $(INCS) $^ -o $(BUILD)$(project)_static

dynamic_exec: $(patsubst $(SRC)%.c,$(BIN)%.o,$(src)) $(LIB)libcalc.so | chkDirs
	@$(CC) -I $(INCS) $^ -L$(LIB) -lcalc -o $(BUILD)$(project)_dynamic

static: $(LIB)libcalc.a
dynamic: $(LIB)libcalc.so

clean:
	@rm -f $(BIN)*
	@rm -f $(BUILD)*
	@rm -f $(LIB)*
	@rm -f $(BUILD)$(project)_static
	@rm -f $(BUILD)$(project)_dynamic
	@echo "Cleaned up the project"

run_static: static_exec
	@$(BUILD)$(project)_static

run_dynamic: dynamic_exec
	@$(BUILD)$(project)_dynamic

help:
	@echo "make all: to build the project"
	@echo "make run_static: to run the project with static linking"
	@echo "make run_dynamic: to run the project with dynamic linking"
	@echo "make clean: to clean the project"
	@echo "make help: to display this help message"
	@echo "make static: to build the static library"
	@echo "make dynamic: to build the dynamic library"
	@echo "use (make CC=<compiler>) to build for a specific target"
