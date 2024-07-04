list = 1 2 3 4 5

bashLoop:
	@echo "Bash loop"
	@for i in $(list); do \
        echo $$i; \
    done

forEach:
	@echo "forEach loop"
	@$(foreach i, $(list), echo $(i);)