# Linux Admin Task 3

## Section 1

Write a Bash script that checks IF the `.bashrc` file exists in the user's home
directory. If it does, append new environment variables to the file: one called
HELLO with the value of `HOSTNAME`, and another local variable called `LOCAL` with
the value of the `whoami` command. Additionally, the script should include a
command to open another terminal at the end. Describe what happens when the
terminal is opened.

*script*: [exercise.sh](./exercise.sh)

When the terminal is opened, the new environment variables are available in the
new shell session. The `HELLO` variable has the value of my hostname (`lat`),
and the `LOCAL` variable has my username (`hala`). Also the old shell session
hangs until the new terminal process is closed.

## Section 2

## List the user commands and redirect the output to /tmp/commands.list

```console
ls /usr/bin > /tmp/commands.list`
```

## Edit in your profile to display date at login and change your prompt permanently

```bash
cat<<'EOF' >> ~/.bashrc
date
PS1="[\u@\h \W]\$ "
EOF
```

## 4. What is the command to count the word in a file or number of file in directory.

to count words in a file: `wc -w <filename>`
to count files in a directory: `ls -1 | wc -l`

to count the number of user commands: `ls -1 /usr/bin | wc -l`

## 5. Write a comamnd to search for all files on the system that, its name is ".profile"

`find / -type f -name .profile`

## 6. List the inode number of /, /etc, /etc/hosts

`ls -i / /etc /etc/hosts`

## 7. Create a symbolic link of `/etc/passwd` in `/boot`

`sudo ln -s /etc/passwd /boot/`

## 8. Create a hard link of `/etc/passwd` in `/boot`. Could you? Why?

running `sudo ln /etc/passwd /boot/` will fail with the error

```plaintext
ln: failed to create hard link '/boot/passwd' => '/etc/passwd': Invalid cross-device link
```

This is because hard links cannot be created across different filesystems.

## 9. `echo \` it will jump to teh next line, and will print `> `. What is that? and how can you chang to `:`?

This is the shell's "secondary prompt", used in this case for prompting line
continuations. It can be changed to `:` by setting the env var `PS2=:`

```console
$ PS2=:
$ echo \
:
```
