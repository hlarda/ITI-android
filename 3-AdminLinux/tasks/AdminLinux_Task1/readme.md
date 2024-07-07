# Linux Admin Task 1

## 1. List three Linux Distributions

- Ubuntu
- Red Hat Enterprise Linux
- OpenSUSE
- Arch Linux
- NixOS

## 2. From the slides what is the man command used for?

provides short reference manuals (pages) for individual commands, API functions, concepts configuration file syntax, file formats and is organized in sections(1 for user commands, 2 for system calls...). That's the traditional Unix documentation system.

## 3. What is the difference between `rm` and `rmdir` using `man` command?

- `rmdir` removes empty directories
- `rm` removes files and directories; It can recursively remove directories and
  their contents.

## 4

### a. Remove dir11 with rmdir in one-step. What did you notice? And how did you overcome that?

It failed with the error `rmdir: failed to remove 'dir1/dir11': Directory not
empty`.  
This can be overcome by:

- running `rm -r dir1/dir11` to remove the directory and its contents.
- running `rm dir1/dir11/* && rmdir dir1/dir11` to remove the contents of the
  directory first, then remove the directory itself.

### b. Then remove OldFiles sunig `rmdir -p` command. State what happened to the hierarchy.

It failed with the error: `rmdir: failed to remove 'Documents/OldFiles': Not a
directory`

### c. Assuming the output of `pwd` was `/home/user`. Write the absolute and relative path for the file mycv.

- Absolute path: `/home/user/docs/mycv`
- Relative path: `docs/mycv`

## 5. Copy the `/etc/passwd` file to your home directory making its name `mypasswd`

command: `cp /etc/passwd ~/mypasswd`

## 6. Rename this new file to be `oldpasswd`

command: `mv ~/mypasswd ~/oldpasswd`

## 7. You are in `/usr/bin/`, list four ways to go to your home directory.

- `cd ~`
- `cd`
- `cd $HOME`
- `pushd ~`
- `cd /home/$USER`

## 8. List Linux commands in `/usr/bin/` that start with letter "w"

command: `ls /usr/bin/w*`

## 9. What command `type` are used for? (from the slide)

Display information about command type.

## 10. Show 2 types of command file in `/usr/bin` that start with letter "c"

```console
ls /usr/bin/c* | head -n 2
```

## 11. Using `man` command find the command to read file. (Note: `man` takes options)

```console
$ man --global-apropos 'read file'
--Man-- next: aria2c(1) [ view (return) | skip (Ctrl-D) | quit (Ctrl-C) ]
--Man-- next: ffmpeg-filters(1) [ view (return) | skip (Ctrl-D) | quit (Ctrl-C) ]
--Man-- next: curl(1) [ view (return) | skip (Ctrl-D) | quit (Ctrl-C) ]
--Man-- next: ffprobe-all(1) [ view (return) | skip (Ctrl-D) | quit (Ctrl-C) ]
--Man-- next: du(1p) [ view (return) | skip (Ctrl-D) | quit (Ctrl-C) ]
--Man-- next: more(1p) [ view (return) | skip (Ctrl-D) | quit (Ctrl-C) ]
--Man-- next: logger(1p) [ view (return) | skip (Ctrl-D) | quit (Ctrl-C) ]
--Man-- next: cat(1p) [ view (return) | skip (Ctrl-D) | quit (Ctrl-C) ]
```

By skimming each of the results descriptions, both `cat` and `more` are
suitable for reading plaintext files.

## 12. What is the usage of the `apropos` command?

Searching the man page names and descriptions for a keyword. This keyword
can be a regex if the `-r` option is passed, or contain wildcards with `-w`
option, or be an exact phrase.
