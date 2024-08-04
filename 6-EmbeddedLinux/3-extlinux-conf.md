# extlinux.conf

## Table of Contents

-[Bootflow Tool](#bootflow-tool)
-[1.USE CASE](#1use-case)
-[2.extlinux.conf file](#2extlinuxconf-file)
-[What is EFI partition?](#what-is-efi-partition)
-[Difference between primary and extended partition](#difference-between-primary-and-extended-partition)

## Bootflow Tool

- Bootflow is a tool that can be used to scan the boot partition of the Raspberry Pi 3 and generate a bootflow diagram.
- It boots first available bootflow

```uboot
RPI$ bootflow scan     
** Booting bootflow '<NULL>' with efi_mgr
Loading Boot0000 'mmc 0' failed
EFI boot manager: Cannot load any image
Boot failed (err=-14)
smc911x: detected LAN9118 controller
smc911x: phy initialized
smc911x: MAC 52:54:00:12:34:56
BOOTP broadcast 1
BOOTP broadcast 2
```

- It searches for `extlinux.conf` file located in `extlinux` directory. It replaces written script in `bootcmd`. just edit `bootcmd` to run the script.

```uboot
setenv bootcmd "bootflow scan" 
```

### 1.USE CASE

- You have only one partition in the SD card lets say `mmcblk0p1` of type `ext4` and bootable.
- It's contains boot directory with the rootfs.
- boot directory contains image file, extlinux directory and files needed for booting.

### 2.extlinux.conf file

```conf
LABEL linux
    KERNEL ../zImage
    DTB ../bcm2710-rpi-3-b-plus.dtb
```

### What is EFI partition?

bootable primary partition that contains the boot loader program for the operating system.

### Difference between primary and extended partition

- **Primary Partition:**
  1. Directly accessible by the system's BIOS or UEFI firmware.
  2. Can be used to boot operating systems.
  3. A hard drive can have up to four primary partitions.
  4. Each primary partition appears to the system as a separate disk.
  5. Directly contains a file system.

- **Extended Partition:**
  1. Not directly bootable and not recognized by BIOS or UEFI as a bootable partition.
  2. Acts as a container for an unlimited number of logical partitions.
  3. A hard drive can have only one extended partition.
  4. Does not directly contain a file system; instead, logical partitions within it contain file systems.
  5. Used to bypass the limit of four primary partitions on a hard drive.

- **Example Scenario**
  - Primary Partitions: Suppose you have a hard disk where you want to install multiple operating systems. You might create three primary partitions: one for Windows, one for Linux, and one for another OS.
  - Extended Partition with Logical Partitions: If you need more than four partitions, you could create one extended partition and then create several logical partitions within it for additional storage or for installing more operating systems.
