# MAKE

## what is make?

a build automation tool that automatically builds executable programs and libraries from source code by reading files called makefiles which specify how to derive the target program.

## why make?

1. to organize the build process and make it more efficient independant on the IDE.
2. to automate the build process.
3. to make the build process more reliable.
4. to make the build process more maintainable.

## how make works?

## is it must to name the makefile as makefile?

no, it is not necessary to name the makefile as makefile. you can name it as anything you want but you have to specify the name of the makefile while running the make command but it's not recommended to do so.

```make
make -f <makefileName>
```

## Basic syntax of makefile

1. setting rules

    ```make
    target: prerequisites
    <tab> recipe 
    ```

    ```make
    display: 
        @echo "Hello, World!"
    all: display
    ```

    to specify the tsrget to be built, use `make <target>` command. here if we run `make all` then it will run the `display` target as `display` is a prerequisite of `all`.

2. define variables

    ```make
    src = main.c
    src += $(wildcard ./src/*.c)
    ```

3. environment variables access

    ```make
    @echo $(CC)
    ```

4. shell commands

    ```make
    @echo $(shell ls)
    @echo "`date` done building" >> build.log
    ```

5. access and run external scripts

    ```make
    @$(shell ./script.sh)
    ```

    this script may be useful for code analysis, code formatting or performing any other task.

6. define functions

   ```make
   define <functionName>
    <tab> recipe
    endef
    ```

    ```make
    define display
        @echo "function called"
        @echo "function name    is  $0"
        @echo "first argument   is  $1"
        @echo "second argument  is  $2"
        @echo "rule name is $@"
        @echo "exit status      is  $$?"
    endef

    arg:= Hello

    all:
        $(call display,$(arg), "World")
    ```

7. built-in functions

    ```make
    @echo $(subst .c,.cpp, main.c test.c lcd.c)
    # withn patsubst pattern can be used
    @echo $(patsubst %.c,%.o,x.c.c bar.c) 
    @echo $(sort zoo bar lose)
    @echo $(word 3, foo bar baz)
    @echo $(wordlist 2, 3, foo bar baz)
    @echo $(firstword foo bar)
    @echo $(lastword foo bar)
    @echo $(wildcard *.c)
    @echo $(shell ls)
    @echo $(addprefix src/, foo bar baz)
    ```

8. loops

    1. bash style

        ```make
        list = 1 2 3 4 5

        bashLoop:
            @for i in $(list); do \
            echo $$i; \
        done
        ```

    2. foreach style

        ```make
        list = 1 2 3 4 5
        forEach:
            @$(foreach i, $(list), echo $(i);)
        ```

9. conditional statements

    ```make
    ifeq (arg1, arg2)
    ifeq 'arg1' 'arg2'
    ifeq "arg1" "arg2"
    ifeq "arg1" 'arg2'
    ifeq 'arg1' "arg2"

    ifneq (arg1, arg2)
    ifneq 'arg1' 'arg2'
    ifneq "arg1" "arg2"
    ifneq "arg1" 'arg2'
    ifneq 'arg1' "arg2"

    ifdef variable-name
    ifndef variable-name
    ```

    ```make
    foo = false
    ifdef $(foo)
    @echo "true" 
    endif
    ```

    they may surround the entire makefile or a part of it or even a single line in a target.

    ```make
    file = test.c
    foo = false
    
    ifeq '$(file)'  'test.c'
    cond1:
        @echo "true"
    endif
    
    cond2:
    ifneq '$(file)'  'main.c'
        @echo "false"
    endif
    
    cond3:
    ifdef foo
        @echo "true" 
    endif
    ```

10. .PHONY

    it is used to specify the targets that are not files to avoid conflicts with the files with the same name as the target.

    ```make
    .PHONY: all clean
    all: 
        @echo "all target"
    clean:
        @echo "clean target"
    ```

11. include

    it is used to include the content of another file in the makefile.

    ```make
    include <fileName>
    ```

    ```make
    include config.mk
    ```

12. automatic variables

    1. `$@` - the target name
    2. `$<` - the first prerequisite
    3. `$^` - all prerequisites

    ```make
    target=iti
    $(target): main.c test.c
    gcc $^ -o $@
    ```

13. substitution reference

    we can replace the `.o` with `.c` in the `obj` variable and assign it to `src` variable.

    ```make
    ## using suffix
    obj= main.o add.o
    src= $(obj:.o=.c)
    ```

    ```make
    ## using pattern %
    obj= main.o add.o
    src= $(obj:%.o=%.c)
    ```

14. pattern rules

    instead of writing the rule for each file.

    ```make
    main.o: main.c
        gcc -c $< -o $@
    add.o: add.c
        gcc -c $< -o $@
    ## prerequisites here cant be replaced with %
    a.out: main.o add.o
        gcc $^ -o $@
    ```

    we can use pattern rules to replace the above rules with

    ```make
    %.o: %.c
        gcc -c $< -o $@
    ```

    `$<` represents the first prerequisite in each rule and `$@` represents the target name.

15. implicit rules

    make has a set of implicit rules that are used to build the target files from the source files.

    ```make
    ## implicit rule
    %.o: %.c
        $(CC) -c $< -o $@
    ```

    here `CC` is the implicit variable that holds the compiler name.

    to see the navigate the implicit rules use

    ```make
    make -p
    ```

16. foreach function

    ```make
    $(foreach var, list, text)
    ```

    var: is temporary variable that holds the value of each element in the list.
    text: any statment executed for each element in the list.
