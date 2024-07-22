# Extlinux Configuration

## Steps

1. create virtual SD card with only one bootable partition of type `ext4`. [Check this](../../3-AdminLinux/tasks/AdminLinux_Task5/1-creatingVirtualSD.md)
2. create a directory called `boot`.
3. create a directory called `extlinux` inside the `boot` directory.
4. copy the `bzImage` and `vexpress-v2p-ca9.dtb` files to the `boot` directory(I've downloaded trash file from githup).
    1. [bzImage](<https://github.com/NuxRo/macchinina/blob/7f615a7720e44c9c826d42101cc9c1b7390fb476/overlayfs/boot/bzImage>)
    2. [vexpress-v2p-ca9.dtb](<https://github.com/vfdev-5/qemu-rpi2-vexpress/blob/master/vexpress-v2p-ca9.dtb>)
5. create a file called `extlinux.conf` inside the `extlinux` directory.
6. write the following content in the `extlinux.conf` file.

    ```conf
    LABEL linux
        KERNEL ../bzImage
        fdtdir ../bcm2710-rpi-3-b-plus.dtb
    ```

7. Run QEMU with the virtual SD card.

    ```bash
    sudo qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -sd ./sd.img 
    ```

8. Edit `bootcmd` in `uboot` to run the script.

    ```uboot
    setenv bootcmd bootflow scan
    ```

    **ISSUE**

    ```uboot
    RPI$ setenv bootcmd bootflow scan
    RPI$ saveenv 
    Saving Environment to FAT... Unable to use mmc 0:1...
    Failed (1)
    RPI$ mmc dev                           
    switch to partitions #0, OK
    mmc0 is current device
    ```

9. Run `bootflow scan` in the `uboot` shell.

    ```uboot
    RPI$ bootflow scan
    No EFI system partition
    No EFI system partition
    Failed to persist EFI variables
    No EFI system partition
    Failed to persist EFI variables
    No EFI system partition
    Failed to persist EFI variables
    ** Booting bootflow '<NULL>' with efi_mgr
    Loading Boot0000 'mmc 0' failed
    EFI boot manager: Cannot load any image
    Boot failed (err=-14)
    ** Booting bootflow 'mmci@5000.bootdev.part_1' with extlinux
    Ignoring unknown command:         DTB
    1:      linux
    ==================>Retrieving file: /boot/extlinux/../bzImage          
    ==================>zimage: Bad magic!
    Boot failed (err=-14)
    smc911x: detected LAN9118 controller
    smc911x: phy initialized
    smc911x: MAC 52:54:00:12:34:56
    BOOTP broadcast 1
    DHCP client bound to address 10.0.2.15 (2 ms)
    smc911x: MAC 52:54:00:12:34:56
    smc911x: detected LAN9118 controller
    smc911x: phy initialized
    smc911x: MAC 52:54:00:12:34:56
    BOOTP broadcast 1
    DHCP client bound to address 10.0.2.15 (0 ms)
    *** Warning: no boot file name; using '0A00020F.img'
    ```

It found the `extlinux.conf` file and tried to boot from it but failed because the image is not correct so it gives an error `zimage: Bad magic!`.
