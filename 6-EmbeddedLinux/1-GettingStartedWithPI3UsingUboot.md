# Getting started with Raspberry Pi 3

- The Raspberry Pi's boot process begins with the GPU, not the CPU.
- Upon power-up, the GPU executes the BootROM code stored in ROM.
- This BootROM is read-only memory that initializes the system's hardware. It configures the GPU and sets up the minimal environment necessary for the system to boot.

- After initializing the hardware, the GPU then reads the `bootcode.bin` file from the SD card.
- `bootcode.bin` is the first stage bootloader that is responsible for setting up the minimal environment necessary for the system to access the SD card and load further components.

- `bootcode.bin` subsequently loads `start.elf`, which is the second stage bootloader.
- This stage of the boot process is crucial, as it is responsible for loading the kernel image in RAM and initializing the system's memory and peripherals. In addition to `start.elf`, the GPU also loads the `fixup.dat` and `config.txt` files, which contain configuration settings and parameters for the boot process.

- after loading the kernel image, the GPU passes control to the CPU, which then executes the kernel code. The kernel initializes the system's software environment, including the operating system and user applications, and completes the boot process.

## Some files involved in the Raspberry Pi boot process

1. `bootcode` : frst stage bootloader, sets minimal environment for accessing the SD card, loads `start.elf`
2. `start.elf` : second stage bootloader, loads the kernel image in RAM, initializes the system's memory and peripherals and loads `fixup.dat` and `config.txt`
    1. `fixup.dat` : This file contains binary data used by the Raspberry Pi's firmware to adjust the system's hardware configuration and functionality. It ensures compatibility and optimization for different versions of the Raspberry Pi hardware.
    2. `config.txt` : user-editable configuration file allows user to enable or disable hardware interfaces, set display resolution, overclock the system, and more.
    3. `cmdline.txt` :  This file contains the command line parameters for the Linux kernel. It specifies various options for the kernel to use during boot, such as root file system location, boot modes, and other kernel parameters.

bootcode, sart.elf, fixup.dat all are cloned from the Raspberry Pi firmware repository. The `config.txt` and `cmdline.txt` files are user-editable configuration files that can be used to customize the boot process and system settings.

## Setting up the Raspberry Pi 3

### 1.Preparing the SD card

partition it into two partitions:

1. A small FAT32 partition for the boot files.
2. A the rest of the space ext4 partition for the root file system.
3. mount the partitions and copy the necessary files to the boot partition.

### 2.Download boot files

[from repository contains pre-compiled binaries of the current Raspberry Pi kernel and modules, userspace libraries, and bootloader/GPU firmware.](https://github.com/raspberrypi/firmware/tree/master)

- `/boot/bcm2710-rpi-3-b-plus.dtb`(<https://github.com/raspberrypi/firmware/blob/master/boot/bcm2710-rpi-3-b-plus.dtb>)
- `/boot/bootcode.bin`(<https://github.com/raspberrypi/firmware/blob/master/boot/bootcode.bin>)
- [`/boot/fixup.dat`](<https://github.com/raspberrypi/firmware/blob/master/boot/fixup.dat>)
- `/boot/start.elf`(<https://github.com/raspberrypi/firmware/blob/master/boot/start.elf>)

then locate them in the boot partition of the SD card.

### 3.Build U-boot for Raspberry Pi 3

1. Clone the U-Boot repository from the official source if it is not already installed.

    ```bash
    git clone git@github.com:u-boot/u-boot.git
    export CROSS_COMPILE=arm-linux-gnueabihf-
    export ARCH=arm
    ```

2. Configure the U-Boot build for the Raspberry Pi 3.

    ```bash
    cd /path/to/u-boot
    ls configs | grep rpi_3_32b_defconfig
    make rpi_3_32b_defconfig
    ```

3. Copy the U-Boot binary to the boot partition of the SD card.

    ```bash
    cp u-boot.bin /path/to/SDboot_partition
    ```

### 4. Create config.txt in boot partition

```txt
kernel=u-boot.bin
enable_uart=1
device_tree=bcm2710-rpi-3-b-plus.dtb
```

UART is enabled to display the U-Boot console output on the serial console.

### 5.Hardware connections

1. Connect the Raspberry Pi 3 to TTL serial adapter.
    GND -> GND
    TX -> RX
    RX -> TX
2. plug in the SD card and power up with micro USB cable.

### 6.connect to the serial console

1. check the serial port name

    ```bash
    $ dmesg | tail -n 5
    [ 9602.480500] usb 1-1: New USB device strings: Mfr=1, Product=2, SerialNumber=0
    [ 9602.480508] usb 1-1: Product: USB-Serial Controller
    [ 9602.480514] usb 1-1: Manufacturer: Prolific Technology Inc.
    [ 9602.484256] pl2303 1-1:1.0: pl2303 converter detected
    [ 9602.485378] usb 1-1: pl2303 converter now attached to ttyUSB0
    ```

2. Use Picocom to connect to the serial console.

    ```bash
    sudo apt install  picocom
    picocom -b 115200 /dev/ttyUSB0
    ```

NOW, you can see the U-Boot console output on the serial console on your terminal.
