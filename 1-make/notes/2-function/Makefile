define display
	@echo "function called"
	@echo "function name    is  $0"
	@echo "first argument   is  $1"
	@echo "second argument  is  $2"
	@echo "rule name is	$@"
	@echo "exit status      is  $$?"
endef

all:
	$(call display, "Hello", "World")

	@echo $(patsubst %.c,%.o,x.c.c bar.c) 
	@echo $(sort zoo bar lose)
	@echo $(word 3, foo bar baz)
	@echo $(wordlist 2, 3, foo bar baz)
	@echo $(firstword foo bar)
	@echo $(lastword foo bar)
	@echo $(wildcard *.c)
	@echo $(shell ls)
	@echo $(addprefix src/, foo bar baz)
