# Linux Admin Task 2

## Question 2

Move the binary file output to the directory `/usr/local/bin` with `sudo`
permissions. Afterward, attempt to execute the binary froma ny working
directory and explain the outcome. Provide a detailed explanation supported
by evidence as to why the binary can be executed from any location.

*outcome*: the `binary_search` command runs successfully from any location
*explanation*:

- the ability to run a binary or a script from any workdir in POSIX is managed
  by the `$PATH` env var.
- when a command is being interpreted by shell, directories in `$PATH` are
  searched for a file matching the command's name
- the first match found gets executed

in this case, the binary file `binary_search` was moved to `/usr/local/bin`
which is in `$PATH` by default.

```console
$ echo $PATH
/usr/bin:/usr/sbin:/usr/local/bin:/run/wrappers/bin:/home/hala/.nix-profile/bin:/nix/profile/bin:/home/hala/.local/state/nix/profile/bin:/etc/profiles/per-user/hala/bin:/nix/var/nix/profiles/default/bin:/run/current-system/sw/bin
```

# Na2na2a

## 1. List all available shells on your system

```console
$ cat /etc/shells`
/run/current-system/sw/bin/bash
/run/current-system/sw/bin/sh
/bin/bash
/bin/sh
```

## 2. List the environment variables in your current shell

command: `env` or `printenv`

## 3. Display your current shell name.

```console
$ echo $SHELL
/run/current-system/sw/bin/bash
```

## 4. Execute the command `echo \` then press enter. What is the purpose of `\`?

The backslash `\` is used for explicit line continuation, it tells the shell
to continue reading the text on the next line as part of the same command.
So doing `echo \` will create a newline preceded by `>` in which I can
continue typing the arguments for echo.

```console
$ echo \
> -e "hello\nworld"
hello
world
```

## 5. create a bash shell alias name `PrintPath` for the `echo $PATH` command

command: `alias PrintPath='echo $PATH'`
