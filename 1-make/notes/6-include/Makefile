## compile the source files in the current directory and includes in directory ../include
projectName= lab
cc= gcc
src= $(wildcard *.c)
obj= $(src:.c=.o)
inc= -I ../include

all: $(projectName)
# implicit rule
%.o: %.c
	$(cc) $(inc) -c $< -o $@

$(projectName): $(obj)
	$(cc) $(inc) $^ -o $@