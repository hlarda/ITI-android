# Creating Virtual Disk Image

## Table of Contents

- [Creating Virtual Disk Image](#creating-virtual-disk-image)
  - [Table of Contents](#table-of-contents)
  - [Why?](#why)
  - [How?](#how)
    - [1. create a file calles `sd.img` with a size of 1GB](#1-create-a-file-calles-sdimg-with-a-size-of-1gb)
    - [2. configure the partitions on the image](#2-configure-the-partitions-on-the-image)
    - [3. Emulate the sdcard as a storage decive](#3-emulate-the-sdcard-as-a-storage-decive)
    - [4. format each partition with some type such as `vfat` and `ext4` suitable for its purpose](#4-format-each-partition-with-some-type-such-as-vfat-and-ext4-suitable-for-its-purpose)
    - [5. mount the partitions](#5-mount-the-partitions)

## Why?

a common practice in software development and testing, as it allows for the simulation of storage devices without the need for physical hardware.

## How?

### 1. create a file calles `sd.img` with a size of 1GB

       ```bash
       dd if=/dev/zero of=sd.img bs=1M count=1024
       ```

  `dd`: command-line utility for copying and converting files.the advantage of it over `cp` is that it can read from and write to specific locations in the file.
  `if`: input file.
  `/dev/zero`: a special file that provides an endless stream of null bytes.
  `of`: output file.
  `bs`: block size.
  `count`: number of blocks to copy.

### 2. configure the partitions on the image

    `cfdisk`: a command-line utility for displaying and manipulating disk partition tables.

        ```bash
          cfdisk sd.img
        ```
    1. selecr `dos` form selec type menu.
    2. create bootaable partition.
        1. select `new`.
        2. write the size of the partition which is `200M` in this case.
        3. select `primary` type.
        4. mark it as `bootable`.
        5. from `type` menu selest `FAT16`.
        6. select `write` to write the changes.
    3. create the rootfs partition.
        1. select `new`.
        2. write the size of the partition which is `800M` in this case.
        3. select `extended` type.
        4. from `type` menu selest `Linux`.
        5. select `write` to write the changes.
    4. Quit  

### 3. Emulate the sdcard as a storage decive

   1. used loop device: a kernel module that allows a file to be mounted as a block device.

            ```bash
              sudo losetup -f --show --partscan  sd.img
            ```

  `losetup`: command-line utility for setting up and controlling loop devices.
  `-f`: find the first available loop device.
  `--show`: print the name of the loop device.
  `--partscan`: scan the partitions on the loop device.
  2. to list all used loop devices.

    ```bash
      losetup -a
    ```

  now if you run `lsblk` you will see the loop device with its partitions.

### 4. format each partition with some type such as `vfat` and `ext4` suitable for its purpose

   1. format the boot partition.
  
    ```bash
    sudo mkfs.vfat -F 16 -n boot /dev/loop<x>p1
    ```

    2. format the rootfs partition.

    ```bash
    sudo mkfs.ext4 -L rootfs /dev/loop<x>p2
    ```

  to make sure that the partitions are created successfully you can run `lsblk` to list the partitions wirh `-o NAME,FSTYPE,LABEL,MOUNTPOINT`.

### 5. mount the partitions

    ```bash
    cd ~/SD/
    mkdir boot rootfs
    sudo mount /dev/loop<x>p1 ~/SD/boot
    sudo mount /dev/loop<x>p2 ~/SD/rootfs
    ```

NOW, you have a virtual disk image with two partitions, boot and rootfs, that can be used for testing and development purposes.

    ```bash
        $ lsblk -o NAME,FSTYPE,LABEL,MOUNTPOINT | grep loop0
        NAME        FSTYPE LABEL  MOUNTPOINT
        loop0                     
        ├─loop0p1   vfat   boot   /home/hala/SD/boot
        └─loop0p2   ext4   rootfs /home/hala/SD/rootfs
    ```
