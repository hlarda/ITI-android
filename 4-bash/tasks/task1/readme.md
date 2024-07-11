# File Organizer

## objective

this is a bash script organize file in directory based on their extensions.

## Usage

```console
./fileOrganizer <dir_name>
```

## Observe that

- no matter the extension's lower case or upper the files are moved to the right directory; `typeset -l extension` specifies that the extension is dealt with as it was lower case within the code.so `file.TXt` and `file.txt` will be moved to the same directory `documents`.
- `-b` and `-f` used in condition to check if the file is a regular file or a directory.
- `mv` command is used to move the file to the right directory.
- `mkdir` command is used to create the directory if it doesn't exist.
