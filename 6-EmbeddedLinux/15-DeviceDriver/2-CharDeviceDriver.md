# Character Device Driver

## Table of Contents

- [Character Device Driver](#character-device-driver)
  - [Table of Contents](#table-of-contents)
  - [Difference between character device driver and block device driver?](#difference-between-character-device-driver-and-block-device-driver)
  - [Major and Minor](#major-and-minor)
  - [Create Device Type File](#create-device-type-file)
  - [Data structures for a character device](#data-structures-for-a-character-device)
  - [Implementation](#implementation)
  - [Console Work](#console-work)
  - [Error](#error)

## Difference between character device driver and block device driver?

1. **Character Device Driver**:
    - slow devices.
    - operations with these devices (read, write) are performed sequentially byte by byte such as keyboard, mouse and serial ports.
2. **Block Device Driver**:
    - devices where data volume is large organized on blocks like hard drives, cdroms and ram disks.
    - operations with these devices are performed in blocks of data.

## Major and Minor

- In UNIX, the devices traditionally had a unique, fixed identifier associated with them. This identifier was split into two parts: the major number and the minor number.
- the major identifies the driver, while the minor identifies each physical device served by the driver.
- **Major**: identifies the device type (IDE disk, SCSI disk, serial port, etc.) .
- **Minor**: dentifies the device (first disk, second serial port, etc.).

> Lets say i led device type is created we can have multiple leds connected to the board, so the major number will be the same but the minor number will be different for each led.

```console
$ ls -la /dev/hda? /dev/ttyS?
brw-rw----   1   root    disk      3, 1      2004-09-18   14:51    /dev/hda1
brw-rw----   1   root    disk      3, 2      2004-09-18   14:51    /dev/hda2
crw-rw----   1   root    dialout   4, 64     2004-09-18  14:52   /dev/ttyS0
crw-rw----   1   root    dialout   4, 65     2004-09-18  14:52   /dev/ttyS1
```

1. first letter: `b` for block devices, `c` for character devices.
2. fifth column: major number which specifies the driver(disk).
3. sixth column: minor number which specifies the device.

> both ttyS0 and ttyS1 are serial ports, so they have the same major number but different devices so they have different minor numbers.

## Create Device Type File

Use `mknod` which receives:

- the name of the file to create under `dev`.
- type: `c` character or `b` block.
- major number.
- minor number.

```console
sudo mknod /dev/helloDev c 250 0
```

## Data structures for a character device

- In the kernel, a character-type device is represented by `struct cdev`.
- Driver operations use three important structures: `struct file_operations` ,
`struct file` and `struct inode`.

1. **struct file_operations**

    implementation of a character device driver means implementing the
    system calls specific to files: `open` , `close` , `read` , `write` , `lseek` , `mmap`.

    ```c
    #include <linux/fs.h>
    struct file_operations {
    struct module *owner;
    loff_t (*llseek) (struct file *, loff_t, int);
    ssize_t (*read) (struct file *, char __user *, size_t, loff_t *);
    ssize_t (*write) (struct file *, const char __user *, size_t, loff_t *);
    [...]
    long (*unlocked_ioctl) (struct file *, unsigned int, unsigned long);
    [...]
    int (*open) (struct inode *, struct file *);
    int (*flush) (struct file *, fl_owner_t id);
    int (*release) (struct inode *, struct file *);
    [...]
    };
    ```

    The `file_operations` structure contains pointers to functions that handle operations on the device. The implementation of these functions is user-defined, tailored to the specific device and the actions required for each operation.

## Implementation

[Source Code for Character Device Driver](./charDevice/charDevice.c).

## Console Work

1. Insert the module into the kernel.

    ```console
    sudo insmod charDevice.ko
    ```

    ```console
    $ dmesg | grep Device
    [12051.232135] Device is allocated with Major: 511, Minor: 0
    [12051.232289] Device is created
    ```

2. Check if device and class are created.

    ```console
    $ ls -al /dev/charDevice
    crw-r--r-- 1 root root 250, 0 Aug  6 20:12 /dev/charDevice
    ```

    ```console
    $ ls -al /sys/class/charDeviceClass
    total 0
    drwxr-xr-x  2 root root 0 Aug  6 22:31 .
    drwxr-xr-x 89 root root 0 Aug  6 18:34 ..
    lrwxrwxrwx  1 root root 0 Aug  6 22:31 charDevice -> ../../devices/virtual/charDeviceClass/charDevice
    ```

## Error

```console
$ dmesg | tail -n 2
[18064.143246] Device is allocated with Major: 511, Minor: 0
[18064.143337] Device is created
$ ls -al /dev/charDevice
crw------- 1 root root 511, 0 Aug  6 23:35 /dev/charDevice
$ cat /dev/charDevice
cat: /dev/charDevice: Permission denied
$ sudo chmod 777 /dev/charDevice
$ cat /dev/charDevice
cat: /dev/charDevice: No such device or address
hala@lat:~/windows-b/ITI-android/6-EmbeddedLinux/15-DeviceDriver/charDevice
$ ls -al /dev/charDevice 
crwxrwxrwx 1 root root 511, 0 Aug  6 23:35 /dev/charDevice
$ sudo chmod +x /dev/charDevice
$ ls -al /dev/charDevice 
crwxrwxrwx 1 root root 511, 0 Aug  6 23:35 /dev/charDevice
$ cat /dev/charDevice
cat: /dev/charDevice: No such device or address
```
