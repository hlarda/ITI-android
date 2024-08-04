# How to Create a Simple Debian Package

A Debian package is a file containing the compiled code, resources, and metadata for a software application, designed to be installed on Debian-based Linux distributions. These packages typically have a `.deb` extension and can be managed using package management tools like `dpkg` and `apt`.

## Table of Contents

## 1.filels structure

### 1.1. Application files

- hello.sh (executable script) located at `./bin`
- hello.conf (configuration file) located at `./etc`
With this, we’ve placed our program files in a folder structure that would be the same once it’s installed in the target machine

```bash
mkdir -p debBuild/{bin,etc}
touch debBuild/bin/hello.sh && sudo chmod +x debBuild/bin/hello.sh
touch  debBuild/etc/hello.conf
```

```bash
$ cat debBuild/bin/hello.sh
#!/bin/bash
. /etc/hello.conf
echo "Hello $NAME"
```

```bash
$ cat debBuild/etc/hello.conf
NAME=$USER
```

### 1.2. Control file

a control file contains the metadata for the package. This is a text file with fields and descriptions. The supported fields are:

- Package (Required): Name of package
- Version (Required): Version of the package
- Maintainer (Required): Name and email of maintainer
- Architecture (Required): Supported target machine architecture
- Description (Required): Short description of the package
- Section: Package classification like admin, database, kernel, utils
- Priority: Whether the package is optional or required
- Essential: Whether the package is always required
- Depends: List other dependent packages like libc6 (>= 2.2.4-4)
- Homepage: URL of the website associated with the package
- Package-Type: Indicate the types, like deb or udeb, for example

Place the control file in the `DEBIAN` folder

```bash
mkdir -p debBuild/DEBIAN && touch debBuild/DEBIAN/control
```

```bash
$ cat debBuild/DEBIAN/control
Package: hello
Version: 1.0
Architecture: all
Maintainer: hlarda <hlarda63@gmail.com>
Description: A simple hello world program for Debian package creation
```

## 2. Building the Debian package

For building the Debian package, we need to create a `.deb` file. This can be done using the `dpkg-deb` command.

```bash
$ dpkg-deb --root-owner-group --build debBuild hello.deb
dpkg-deb: building package 'hello' in 'hello.deb'.
hala@lat:~/windows-b/ITI-android/6-EmbeddedLinux/10-CreateSimpleDebianPackage
$ ls
debBuild  depianPackage.md  hello.deb
```

## 3. Verifying the Debian package

### 3.1. Check contents

To verify that each file in the Debian package have been added in the correct path, we can use the `dpkg` command.

```bash
$ dpkg -c hello.deb
drwxrwxr-x root/root         0 2024-08-04 16:40 ./
drwxrwxr-x root/root         0 2024-08-04 16:29 ./bin/
-rwxrwxr-x root/root        42 2024-08-04 16:20 ./bin/hello.sh
drwxrwxr-x root/root         0 2024-08-04 16:30 ./etc/
-rw-rw-r-- root/root        10 2024-08-04 16:22 ./etc/hello.conf
```

### 3.2. Linting

In order to further comply with the Debian packaging conventions, we can lint the package. For this, we use the tool called `lintian`.

```bash
$ lintian hello.deb
E: hello: extended-description-is-empty
E: hello: file-in-etc-not-marked-as-conffile [etc/hello.conf]
E: hello: malformed-contact Maintainer hlarda
E: hello: no-changelog usr/share/doc/hello/changelog.gz (native package)
E: hello: no-copyright-file
W: hello: description-synopsis-starts-with-article
W: hello: no-manual-page [bin/hello.sh]
W: hello: non-standard-dir-perm 0775 != 0755 [bin/]
W: hello: non-standard-dir-perm 0775 != 0755 [etc/]
W: hello: non-standard-executable-perm 0775 != 0755 [bin/hello.sh]
W: hello: non-standard-file-perm 0664 != 0644 [etc/hello.conf]
W: hello: recommended-field hello.deb Priority
W: hello: recommended-field hello.deb Section
W: hello: script-with-language-extension [bin/hello.sh]
```

#### Solve the lintian errors

- `E: hello: file-in-etc-not-marked-as-conffile [etc/hello.conf]`
  - Add the `conffiles` file in the `DEBIAN` folder

    ```bash
    touch debBuild/DEBIAN/conffiles
    ```

    ```bash
    $ cat debBuild/DEBIAN/conffiles
    etc/hello.conf
    ```

    ```console
    $ dpkg-deb --root-owner-group --build debBuild hello.deb
    dpkg-deb: building package 'hello' in 'hello.deb'.
    $ lintian hello.deb
    E: hello: no-changelog usr/share/doc/hello/changelog.gz (native package)
    E: hello: no-copyright-file
    W: hello: description-starts-with-leading-spaces line 1
    W: hello: description-synopsis-starts-with-article
    W: hello: no-manual-page [bin/hello.sh]
    W: hello: non-standard-dir-perm 0775 != 0755 [bin/]
    W: hello: non-standard-dir-perm 0775 != 0755 [etc/]
    W: hello: non-standard-executable-perm 0775 != 0755 [bin/hello.sh]
    W: hello: non-standard-file-perm 0664 != 0644 [etc/hello.conf]
    W: hello: recommended-field hello.deb Section
    W: hello: script-with-language-extension [bin/hello.sh]
    ```

- `E: hello: no-changelog usr/share/doc/hello/changelog.gz (native package)`

    ```console
    $ touch debBuild/DEBIAN/changelog
    $ cat debBuild/DEBIAN/changelog
    hello (1.0) unstable; urgency=low

      * Initial release

     -- Hala Reda Fawzi <your-email@example.com>  Sun, 04 Aug 2024 16:40:00 +0000
    $ mkdir -p  debBuild/usr/share/doc/hello
    $ gzip --best -c debBuild/DEBIAN/changelog  > debBuild/usr/share/doc/hello/changelog.gz
    ```

- `E: hello: no-copyright-file`

    ```console
    $ touch debBuild/usr/share/doc/hello/copyright
    $ cat debBuild/usr/share/doc/hello/copyright
    Copyright: 2024 hlarda <hlarda69@gmail.com>
    License: GPL-3+

     This package is free software; you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation; either version 3 of the License, or
     (at your option) any later version.

     This package is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.
    ```

#### Solve the lintian warnings

```log
W: hello: no-manual-page [bin/hello.sh]
W: hello: non-standard-dir-perm 0775 != 0755 [bin/]
W: hello: non-standard-dir-perm 0775 != 0755 [etc/]
W: hello: non-standard-dir-perm 0775 != 0755 [usr/]
W: hello: non-standard-dir-perm ... use "--tag-display-limit 0" to see all (or pipe to a file/program)
W: hello: non-standard-executable-perm 0775 != 0755 [bin/hello.sh]
W: hello: non-standard-file-perm 0664 != 0644 [etc/hello.conf]
W: hello: non-standard-file-perm 0664 != 0644 [usr/share/doc/hello/changelog.gz]
W: hello: non-standard-file-perm 0664 != 0644 [usr/share/doc/hello/copyright]
W: hello: script-with-language-extension [bin/hello.sh]
W: hello: unknown-control-file [changelog]
```

1. Changing permissions

    ```bash
    sudo chmod 0755 debBuild/bin
    sudo chmod 0755 debBuild/etc
    sudo chmod 0755 debBuild/usr
    sudo chmod 0755 debBuild/usr/share
    sudo chmod 0755 debBuild/usr/share/doc
    sudo chmod 0755 debBuild/usr/share/doc/hello
    sudo chmod 0755 debBuild/bin/hello.sh
    sudo chmod 0644 debBuild/etc/hello.conf
    sudo chmod 0644 debBuild/usr/share/doc/hello/changelog.gz
    sudo chmod 0644 debBuild/usr/share/doc/hello/copyright
    ```

    ```log
    W: hello: no-manual-page [bin/hello.sh]
    W: hello: script-with-language-extension [bin/hello.sh]
    W: hello: unknown-control-file [changelog]
    ```

2. `W: hello: script-with-language-extension [bin/hello.sh]`

    ```bash
    mv debBuild/bin/hello.sh debBuild/bin/hello
    ```

3. `W: hello: unknown-control-file [changelog]`

   ```bash
   rm debBuild/DEBIAN/changelog
   ```

4. `W: hello: no-manual-page [bin/hello.sh]`

    ```bash
    mkdir -p debBuild/usr/share/man/man1 && touch debBuild/usr/share/man/man1/hello.1
    ```

    ```bash
    $ cat debBuild/usr/share/man/man1/hello.1
    .TH hello 1 "August 2024" "version 1.0"
    .SH NAME
    hello \- Simple hello world program
    .SH SYNOPSIS
    .B hello
    .SH DESCRIPTION
    A simple hello world program for demonstrating Debian package creation.
    .SH AUTHOR
    hlarda <hlarda69@gmail.com>
    ```

    ```bash
    gzip -9 debBuild/usr/share/man/man1/hello.1
    ```

Permission Warnings AGAIN

```console
$ dpkg-deb --root-owner-group --build debBuild hello.deb
dpkg-deb: building package 'hello' in 'hello.deb'.
$ lintian hello.deb
W: hello: non-standard-dir-perm 0775 != 0755 [usr/share/man/]
W: hello: non-standard-dir-perm 0775 != 0755 [usr/share/man/man1/]
W: hello: non-standard-file-perm 0664 != 0644 [usr/share/man/man1/hello.1.gz]
```

```bash
sudo chmod 0755 debBuild/usr/share/man
sudo chmod 0755 debBuild/usr/share/man/man1
sudo chmod 0644 debBuild/usr/share/man/man1/hello.1.gz
```

## Last Build with no errors or warnings

```bash
$ dpkg-deb --root-owner-group --build debBuild hello.deb
dpkg-deb: building package 'hello' in 'hello.deb'.
$ lintian hello.deb
```

To run your newly created Debian package (`hello.deb`), follow these steps:

### 1. **Install the Package**

To install the package, use the `dpkg` command. You might need `sudo` privileges:

```bash
sudo dpkg -i hello.deb
```

This command installs the package on your system.

### 2. **Verify Installation**

After installation, verify that the package is installed correctly and check its files:

```bash
dpkg -L hello
```

This will list all files installed by the `hello` package.

### 3. **Run the Installed Program**

If your package installs an executable or script (like `hello` in your `bin` directory), you can run it from the command line:

```bash
hello
```

Ensure that the executable is in your `PATH`. If not, you may need to specify its full path, such as:

```bash
/usr/bin/hello
```

### 4. **Check Installed Files**

Verify that the files are in the correct locations:

- **Executable**: Check that it’s in the expected directory, e.g., `/usr/bin/hello`.
- **Configuration Files**: Check that configuration files are in the `/etc` directory, e.g., `/etc/hello.conf`.
- **Man Page**: Verify that the man page is accessible by running:

  ```bash
  man hello
  ```

### 5. **Uninstall the Package (if needed)**

To uninstall the package:

```bash
sudo dpkg -r hello
```

## Testing on my machine

```bash
$ sudo dpkg -i hello.deb
Selecting previously unselected package hello.
(Reading database ... 807591 files and directories currently installed.)
Preparing to unpack hello.deb ...
Unpacking hello (1.0) ...
Setting up hello (1.0) ...
Processing triggers for man-db (2.12.0-4build2) ...
hala@lat:~/windows-b/ITI-android/6-EmbeddedLinux/10-CreateSimpleDebianPackage
$ hello 
Hello hala
$ man hello
hello(1)                       General Commands Manual                      hello(1)

NAME
       hello - Simple hello world program

SYNOPSIS
       hello

DESCRIPTION
       A simple hello world program for demonstrating Debian package creation.

AUTHOR
 Manual page hello(1) line 1 (press h for help or q to quit)
$ sudo dpkg -r hello
(Reading database ... 807595 files and directories currently installed.)
Removing hello (1.0) ...
Processing triggers for man-db (2.12.0-4build2) ...
```
