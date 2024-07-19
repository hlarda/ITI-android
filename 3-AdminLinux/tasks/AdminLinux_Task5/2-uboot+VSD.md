# Booting u-boot from SD card over QEMU

## Table of Contents

- [Why U-Boot?](#why-u-boot)

## Why U-Boot?

- U-Boot is a popular bootloader used in embedded systems.
- U-Boot has a smaller footprint compared to GRUB.
- It features a built-in command-line interface (CLI).
- U-Boot is compatible with a wide range of boards.
- It uses the Linux kernel image efficiently, maintaining a minimal size.
  
## Steps

- [Booting u-boot from SD card over QEMU](#booting-u-boot-from-sd-card-over-qemu)
  - [Table of Contents](#table-of-contents)
  - [Why U-Boot?](#why-u-boot)
  - [Steps](#steps)
  - [1. Create a virtual SD card](#1-create-a-virtual-sd-card)
  - [2. U-Boot Installation and Configuration](#2-u-boot-installation-and-configuration)
  - [4. Run Qemu with U-Boot and Virtual SD card](#4-run-qemu-with-u-boot-and-virtual-sd-card)
  - [Load file from SD card into RAM then read](#load-file-from-sd-card-into-ram-then-read)
  - [Make them loaded](#make-them-loaded)

## 1. Create a virtual SD card

[Create a virtual SD card](../AdminLinux_Task5/1-creatingVirtualSD.md) with two partitions, boot and rootfs.

Don't panic if you don't find the device each time you open the PC, nothing is wrong, you just need to repeat the steps starting from the loop creation.

## 2. U-Boot Installation and Configuration

1. clone u-boot from git

   ```CMD
   git clone git@github.com:u-boot/u-boot.git
    ```

2. `cd u-boot`

3. Machine configueartion

   1. machine: vexpress_ca9x4

        ```bash
        ls configs/ | grep vexpress_ca9x4_defconfig
        ```

   2. export cross compiler environment variable as u-boot uses it.
    It's better to add then to the `.bashrc` file to avoid the hassle
    caused by setting them each time you open a new terminal.

        ```bash
        export CROSS_COMPILE=~/x-tools/arm-cortexa9_neon-linux-musleabihf/bin/arm-cortexa9_neon-linux-musleabihf-

        ```

   3. specify the target architecture. it's better to add it to the `.bashrc` file.

        ```bash
        export ARCH=arm
        ```

   4. use defualt configuration for the board

        ```bash
        make vexpress_ca9x4_defconfig
        ```

4. uboot configuration

    ```bash
    make menuconfig
    ```

    1. `Command line interface`
        1. `Boot commands`
            1. `bootd`
            2. `run`
        2. `Environment commands`
            1. `editenv`
            2. `saveenv`
        3. `memory commands`
            1. `md5sum -v`
        4. `Device access commands`
            1. `lsblk`
        5. edit shell prompt to `uboot>`
    2. `Environments`
       1. enable `Environment in a FAT filesystem`
       2. disable `Environment in flash memory`
       3. (0:1)`Device and parttiton where to store the environment in FAT`
    3. `Boot options`
       1. `Autoboot options`
           1. (3)`delay in seconds before automatically booting`
       2. (Hello Sweetie!)`bootcmd value`
5. build u-boot

    ```bash
    make
    ```

## 3. Install QEMU

```bash
sudo apt install qemu-system-arm
qemu-system-arm --version
```

Run with uboot without SD to make sure it's working

```bash
sudo qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot
```

## 4. Run Qemu with U-Boot and Virtual SD card

```bash
qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -sd ~/sd.img
```

`M` : machine
`m` : RAM
`nographic` : no GUI
`kernel` : kernel image
`sd` : storage device

## Load file from SD card into RAM then read

1. touch files in SD/boot contains any string. in may case I created a file called `vexpress-v2p-ca9.dtb` contains `"vexpress-v2p-ca9.dtb"` and `zImage` contains `"zImage"`.
2. open qemu
3. `fatload mmc 0:1 $fdt_addr_r file`
4. `md $fdt_addr_r` to check if loaded in RAM

to load kernel we need two files: zImage and dtb file(config with static size).
kernel loaded in $kernel_addr_r and dtb loaded in $fdt_addr_r

```uboot
 RPI$ fatload mmc 0:1 $fdt_addr_r vexpress-v2p-ca9.dtb
 21 bytes read in 6 ms (2.9 KiB/s)
 RPI$ md $fdt_addr_r                                  
 60000000: 70786576 73736572 7032762d 3961632d  vexpress-v2p-ca9
 60000010: 6274642e 0000000a 00000000 00000000  .dtb............
 60000020: 00000000 00000000 00000000 00000000  ................
 60000030: 00000000 00000000 00000000 00000000  ................
 RPI$ fatload mmc 0:1 $kernel_addr_r zImage           
 7 bytes read in 5 ms (1000 Bytes/s)
 RPI$ md $kernel_addr_r                    
 60100000: 616d497a 000a6567 00000000 00000000  zImage..........
 60100010: 00000000 00000000 00000000 00000000  ................
 60100020: 00000000 00000000 00000000 00000000  ................
 60100030: 00000000 00000000 00000000 00000000  ................
 ```

## Make them loaded

edit `bootcmd` to load the kernel and dtb file from the SD card then `saveenv` to save the changes.

```uboot
setenv bootcmd "mmc dev 0; fatload mmc 0:1 $kernel_addr_r zImage; fatload mmc 0:1 $fdt_addr_r vexpress-v2p-ca9.dtb; md $kernel_addr_r; md $fdt_addr_r"
saveenv
```
