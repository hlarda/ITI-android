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