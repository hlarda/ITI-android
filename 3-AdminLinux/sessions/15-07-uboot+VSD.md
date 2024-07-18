# Bootloader

The boot process follows this sequence: vendorcode -> bootcode -> start.elf -> u-boot.bin -> kernel.img

Key Points about U-Boot:

- U-Boot is a popular bootloader used in embedded systems.
- U-Boot has a smaller footprint compared to GRUB.
- It features a built-in command-line interface (CLI).
- U-Boot is compatible with a wide range of boards.
- It uses the Linux kernel image efficiently, maintaining a minimal size.

User interaction with U-Boot:   CLI -> Kernel -> Hardware

SD Card Structure:

- Partition 1: A bootable FAT16 partition containing `u-boot.bin`, `kernel.img`, `bootcode.bin`, `config.txt`, and `start.elf`.
- Partition 2: A root filesystem (rootfs) partition formatted with ext4.

## why FAT as bootable partition?

as files on it executable by default and faster in access due to simplicity.

## Uboot installation and configuration

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

        ```bash
        export CROSS_COMPILE=~/x-tools/arm-cortexa9_neon-linux-musleabihf/bin/arm-cortexa9_neon-linux-musleabihf-

        ```

   3. specify the target architecture

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

## QEMU and U-Boot from virtual SD card

1. Install qemu

    ```bash
    sudo apt install qemu-system-arm
    qemu-system-arm --version
    ```

2. Run with virtual SD card

    ```bash
    qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -sd ~/sd.img
    ```

    -M: machine
    -m: RAM
    -nographic: no GUI
    -kernel: kernel image
    -sd: storage device

## u-boot commands

`bdinfo` : board information
`mmc dev` : to select the mmc device
`echo $bootargs` : to see the bootargs
`printenv` : to see the environment variables
`run` : to run the script
`setenv name " echo Hi ; echo Lulu"` : to set the environment variable
`saveenv` : to save the environment variable
`editenv` : to edit the environment variable
exit : ctrl + a    x
`md` : memory display

## u-boot variables

- variables
- holds commands to run at boot time it is the only way to write script as we have no file system.

```uboot
RPI$ setenv var "if mmc dev ; then echo "mmc is available" ; else echo "there is no ms ns no mms" ; fi"
RPI$ run var                                                                              
switch to partitions #0, OK
mmc0 is current device
mmc is available
```

## Auto-boot

`bootcmd` : the command to run at boot time

```uboot
=> echo $bootcmd
run distro_bootcmd; run bootflash
=> setenv bootcmd "mmc dev 0; mmc read 0x80000000 0x800 0x2000; bootm 0x80000000"
saveenv
```

## Edit bootcmd through menuconfig

1. `make menuconfig`
2. Boot options
3. Bootcmd value
4. save and exit
5. `export CROSS_COMPILE=~/x-tools/arm-cortexa9_neon-linux-musleabihf/bin/arm-cortexa9_neon-linux-musleabihf-`
6. `make`
7. `qemu-system-arm -M vexpress-a9 -nographic -kernel u-boot`

## load file in RAM

1. touch files in SD/boot contains any string
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

## make it load on boot

`setenv bootcmd "mmc dev 0; fatload mmc 0:1 $kernel_addr_r zImage; fatload mmc 0:1 $fdt_addr_r vexpress-v2p-ca9.dtb; bootz $kernel_addr_r - $fdt_addr_r"`

