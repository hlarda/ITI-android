# TFTP

## Best Image Flashing Methods

1. Directly through device access using `fatload`.
2. Remotely via server using `tftp`.

## 2.TFTP

### Why tftp?

tftp is a simple file transfer protocol that is used to transfer files between the client and the server supported by the uboot.

## A.Linux Side

### 1.Download tftp on the server

```bash
sudo apt-get install tftpd-hpa
```

there is a system user created under /etc/passwd called tftp.

```bash
$ cat /etc/passwd | grep tftp
tftp:x:129:139:tftp daemon,,,:/srv/tftp:/usr/sbin/nologin
```

`tftp`: deamon name
`x`: it has an encrypted password stored in /etc/shadow file.
`129`: user id `139`: group id
`tftp daemon`: comment "account description".
`/srv/tftp`: home directory like home directory for regular user.
`/usr/sbin/nologin`: shell prevents the user from logging in.

### 2.Configure tftp server

Default configuration file is located at `/etc/default/tftpd-hpa`

```bash
$ cat /etc/default/tftpd-hpa
# /etc/default/tftpd-hpa

TFTP_USERNAME="tftp"
TFTP_DIRECTORY="/srv/tftp"
TFTP_ADDRESS=":69"
TFTP_OPTIONS="--secure"
```

It's needed to add `--create` option to give the tftp server the ability to create files

```bash
$ sudo nano /etc/default/tftpd-hpa
$ cat /etc/default/tftpd-hpa
# /etc/default/tftpd-hpa

TFTP_USERNAME="tftp"
TFTP_DIRECTORY="/srv/tftp"
TFTP_ADDRESS=":69"
TFTP_OPTIONS="--secure --create"
```

### 3.Run tftp demon

```bash
$ sudo systemctl status tftpd-hpa
● tftpd-hpa.service - LSB: HPA's tftp server
     Loaded: loaded (/etc/init.d/tftpd-hpa; generated)
     Active: active (running) since Sun 2024-07-21 11:44:24 EEST; 5h 44min ago
       Docs: man:systemd-sysv-generator(8)

$ sudo systemctl stop tftpd-hpa

$ sudo systemctl status tftpd-hpa
○ tftpd-hpa.service - LSB: HPA's tftp server
     Loaded: loaded (/etc/init.d/tftpd-hpa; generated)
     Active: inactive (dead) since Sun 2024-07-21 17:38:36 EEST; 2s ago
       Docs: man:systemd-sysv-generator(8)

$ systemctl restart tftpd-hpa
```

#### initd script for tftp demon

The tftp demon is started by the initd script located at `/etc/init.d/tftpd-hpa`. This script downloaded with the tftp package. you can find the location of the script by using the `dpkg` command.

```bash
$ dpkg -L tftpd-hpa 
/.
/etc
/etc/init
/etc/init/tftpd-hpa.conf
/etc/init.d
/etc/init.d/tftpd-hpa
/usr
/usr/sbin
/usr/sbin/in.tftpd
/usr/share
/usr/share/doc
/usr/share/doc/tftpd-hpa
/usr/share/doc/tftpd-hpa/README
/usr/share/doc/tftpd-hpa/README.Debian
/usr/share/doc/tftpd-hpa/README.security
/usr/share/doc/tftpd-hpa/changelog.Debian.gz
/usr/share/doc/tftpd-hpa/copyright
/usr/share/doc/tftpd-hpa/examples
/usr/share/doc/tftpd-hpa/examples/sample.rules
/usr/share/man
/usr/share/man/man8
/usr/share/man/man8/in.tftpd.8.gz
/usr/share/man/man8/tftpd.8.gz
$ cat /etc/init.d/tftpd-hpa
```

### 4.Change the ownership of the tftp directory

when request is made to the tftp server, the server will look for the file in the directory `/srv/tftp`

but this folder is owned by root, so we need to change the ownership of the folder to the tftp user

```bash
$ sudo chown -R tftp:tftp /srv/tftp
$ ls -al /srv |grep tftp
drwxr-xr-x 1 tftp tftp   0 Jul 18 16:44 tftp
```

## B. Uboot Side

### 1. Setup network between the server and the target

[Check this..](../../../3-AdminLinux/tasks/AdminLinux_Task5/3.uboot+network.md)

### 2.Set the serverip address

```uboot
=> setenv serverip <tap0IP>
=> tftp $kernel_addr_r zimage 
=> tftp $fdt_addr_r bcm2710-rpi-3-b-plus.dtb
=> bootz $kernel_addr_r - $fdt_addr_r
```

## IPs in RPI

`serverip`: IP address of the server.
`ipaddr`: IP address of the RPI.
