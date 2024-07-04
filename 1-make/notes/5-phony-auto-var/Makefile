.PHONY: clean

## acces with variable reference $(var)
var="Hello from makefile"

target=iti
$(target): main.c add.c
	gcc $^ -o $@

run:
	gcc main.c add.c -o main
	echo $(var)
	./main

clean:
	rm $(target)