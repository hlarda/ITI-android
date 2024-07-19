# Loop on boot options

## Table of Contents

- [Loop on boot options](#loop-on-boot-options)
  - [Table of Contents](#table-of-contents)
  - [STEPS](#steps)
    - [1. Prerequisites](#1-prerequisites)
    - [2. write uboot text script](#2-write-uboot-text-script)
  - [3. Create a boot script](#3-create-a-boot-script)
  - [4. Copy the boot script to the boot partition](#4-copy-the-boot-script-to-the-boot-partition)
  - [5. Edit Bootcmd to run the script](#5-edit-bootcmd-to-run-the-script)
  - [6. Test running in more than one scenario](#6-test-running-in-more-than-one-scenario)

## STEPS

### 1. Prerequisites

Prepare the environment by installing the necessary tools and dependencies. Make sure you have done almost all the steps in the previous tasks.

1. [Create a virtual SD card](../AdminLinux_Task5/1-creatingVirtualSD.md) with two partitions, boot and rootfs.
2. [U-Boot Installation and Configuration](../AdminLinux_Task5/2-uboot%2BVSD.md)
3. [Run Qemu with U-Boot and Virtual SD card](../AdminLinux_Task5/4-bootOptions.md)
4. [Load file from SD card into RAM then read](../AdminLinux_Task5/5-LoadFileFromSD.md)
5. [Ping the host machinr to check the connection](../AdminLinux_Task5/3.uboot+network.md)

### 2. write uboot text script

1. touch text file in fat partition.
2. write the following script in the text file.

    ```uboot
    setenv success 0

    for boot_option in mmc tftp noOption; do

        if test "$boot_option" = "mmc"; then
            echo "----------------------------------------------------------------------"
            echo "Checking MMC..."
            if fatload mmc 0:1 ${fdt_addr_r} vexpress-v2p-ca9.dtb && fatload mmc 0:1 $kernel_addr_r zImage; then
                echo "Loaded zImage and dtb from MMC"
                setenv success 1
            else
                echo "Failed to load from MMC"
            fi
        fi

        if test "$success" -eq 0 && test "$boot_option" = "tftp"; then
            echo "----------------------------------------------------------------------"
            setenv ipaddr 192.168.1.20
            echo "Checking TFTP..."
            if ping 192.168.1.10; then
                echo "Loaded zImage and dtb via TFTP"
                setenv success 1
            else
                echo "TFTP server not reachable"
            fi
        fi

        if test "$success" -eq 0 && test "$boot_option" = "noOption"; then
            echo "----------------------------------------------------------------------"
            echo "No boot option succeeded"
        fi

    done
    ```

## 3. Create a boot script

```bash
mkimage -T script -n "Bootscript" -C none -d bootScript.txt bootScript
```

## 4. Copy the boot script to the boot partition

```bash
sudo cp bootScript ~/SD/boot/
```

## 5. Edit Bootcmd to run the script

```uboot
setenv bootcmd 'fatload mmc 0:1 ${loadaddr} bootScript; source ${loadaddr}'
saveenv
reset
```

## 6. Test running in more than one scenario

1. Without the SD card or network interface

   ```bash  
    qemu-system-arm -M vexpress-a9 -nographic -kernel ~/u-boot/u-boot
   ```

   ```uboot
    Checking MMC...
    Card did not respond to voltage select! : -110
    ** Bad device specification mmc 0 **
    Couldn't find partition mmc 0:1
    Can't set block device
    Failed to load from MMC
    ----------------------------------------------------------------------
    Checking TFTP...
    smc911x: detected LAN9118 controller
    smc911x: phy initialized
    smc911x: MAC 52:54:00:12:34:56
    Using ethernet@3,02000000 device

    ARP Retry count exceeded; starting again
    smc911x: MAC 52:54:00:12:34:56
    ping failed; host 192.168.1.10 is not alive
    TFTP server not reachable
    ----------------------------------------------------------------------
    No boot option succeeded
    ```

2. With the SD card

   ```bash
    qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -sd ~/sd.img
   ```

   ```uboot
    Checking MMC...
    21 bytes read in 7 ms (2.9 KiB/s)
    7 bytes read in 6 ms (1000 Bytes/s)
    Loaded zImage and dtb from MMC
    ```

3. With the network interface

    ```bash
    qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -sd ~/sd.img -net nic -net tap,script=/home/hala/windows-b/ITI-android/3-AdminLinux/tap.sh
    ```

    ```uboot
    Checking TFTP...
    smc911x: detected LAN9118 controller
    smc911x: phy initialized
    smc911x: MAC 52:54:00:12:34:56
    Using ethernet@3,02000000 device
    smc911x: MAC 52:54:00:12:34:56
    host 192.168.1.10 is alive
    Loaded zImage and dtb via TFTP
    ```
