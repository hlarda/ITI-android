# Device Driver

## Linux Kernel Module

### Linux Kernel

- interface between hardware and software during runtime

- module is a driver that can be loaded and unloaded from the kernel at runtime.

## Moeule compilation

1. Static: during compile time integrated into linux kernel image(zImage)
   - Disadvantage: very large kernel image and need too much time to be compiled and loaded(startup time).
   - Advantage: module startup is faster than dynamic as it's already loaded in memory with kernel image.
2. Dynamic: independant `module.ko` file can be loaded at runtime
    - Advantage: smaller kernel image and faster startup time as it's loaded only when needed.
    - Disadvantage: large size of module.ko file which needed to be loaded in RAM and more time needed to load it so modules startup is slower than static.

## Commands used with modules used with dynamic modules

1. `lsmod`: list all loaded modules
2. `insmod`: insert module into kernel
3. `rmmod`: remove module from kernel
4. `modprobe`: insert and remove module from kernel
5. `modinfo`: show information about module

## Why to use linux daynamic kernel modules?

- To add new functionality to the kernel, support new hardware and add device drivers to the kernel without recompiling the kernel or rebooting the system.
- Load security modules dynamically to enhance system security without downtime.

## LKM(Linux Kernel Module) structure

1. Header section:
   - kernel headers whose .c code is kernel code.
   - when choosing these header files, make sure it's compatible with the kernel version.
   - Location of header files is `/usr/src/linux-headers-$(uname -r)/include/linux/`.

        ```bash
        # kernel version
        $ uname -r
        6.8.0-39-generic
        ```

   - NO USER SPACE HEADERS ARE ALLOWED TO BE INCLUDED IN LKM as they are in user space and kernel space is different.

    ```c
    linux/module.h: contains the necessary functions and macros to create a module.
    linux/init.h: contains the necessary functions and macros to initialize and cleanup the module.
    linux/cdev.h: contains the necessary functions and macros to create a character device.
    linux/fs.h: contains the necessary functions and macros to create a file system.
    linux/kernel.h: contains the necessary functions and macros to print messages to the kernel log.
    ```

2. Code section:

    - No main() function is used in LKM as the kernel is the entry point and the one that calls the module functions.

    1. module_init() is used to load the module.
         - returns integer value.
         - name of the function passed as an argument is not important but the return type and the number of arguments are.
    2. module_exit() is called when the module is unloaded.
       - cleanup function returns void and takes no arguments.
    3. your implementation of module functions passed to module_init() and module_exit() functions.

```cpp
mkdir itiModules
cd itiModules
mkdir driver1
cd driver1
touch driver1.c
```

[Driver source code](./driver/driver.c).

## How to compile LKM dynamically?

1. Create a Makefile in the same directory as the LKM source code.

    ```make
    obj-m += driver.o

    all:
        make -C /lib/modules/$(shell uname -r)/build/ M=$(PWD) modules
    clean:
        make -C /lib/modules/$(shell uname -r)/build/ M=$(PWD) clean
    ```

After creating the Makefile, run `make` command to compile the LKM.

```bash
make
```

## How to load and unload LKM?

1. Load the module using `insmod` command.

    ```bash
    sudo insmod driver.ko
    ```

2. Check if the module is loaded using `lsmod` command.

    ```bash
    $ lsmod | grep driver
    driver                 12288  0
    ```

3. validate reading and writing in `proc/hello` file.

    ```bash
    $ cat /proc/hello 
    $ echo "m7md" >> /proc/hello 
    $ dmesg | grep Hello
    [45862.712174] Hello, World!
    [46792.782361] Hello, read!
    [46889.759375] Hello, write!
    ```

4. Unload the module using `rmmod` command.

    ```console
    $ sudo rmmod driver
    $ dmesg | grep Goodbye
    [47194.497034] Goodbye, World!
    ```

> kernelModule.ko is not executable as it's not a user space program but a kernel module and the kernel is the one that calls the module functions concerned with read and write operations.
>
> obj-m is a variable that is used to specify the object files that are built as loadable kernel modules.
> obj-m is used to specify the object files that are built as static kernel modules with other required steps.

## Why .ko is much larger than .o from static build?

1. make init_module static: to prevent any other module from using the module.
2. make exit_module __init: deletes the module from RAM after initialization.
3. make exit_module __exit: to prevent memory unloading of the module.
4. __init and __exit are used to specify the section of the module that is used to initialize and cleanup the module.

static has only two sections:   init and code.
dynamic has three sections:     init, code and exit.

## i wanna create a character device driver creates a file under /proc

/proc is a virtual file system that is used to communicate between the kernel and the user space.
kernel only can write to /proc and user space can only read from /proc.

1. include proc_fs.h header file.
2. create a file ubder /proc using proc_create() function.
   1. the operation that can be done on the file are defined in proc_ops structure.
   2. the operations is system calls that can be done on the file.

### pass data from user space to kernel space

### Device Driver categories

1. Character device driver: used to read and write data to and from the device(character by character) as in GPIO and UART.
2. Block device driver: used to read and write data to and from the device in blocks.
3. Network device driver: used to read and write data to and from the network.

## /var/log/syslog /var/log/kern.log

Dont forget to delete from time to time.
