# Clone Yocto And Duild Image

## Tab

## 1. Clone the Poky Repository

Follow the [Yocto Documentation Install Guide](https://docs.yoctoproject.org/brief-yoctoprojectqs/index.html) to clone the Poky repository.

## 3. Source the Environment

Run the following command to set up the build environment:

```bash
cd poky
source oe-init-build-env
```

### 4. Update the configuration as needed

we'll set the target machine to `qemuarm64` and configure bitbake to parallelize on 7 threads

```bash
hala@lat:~/poky/build
$ echo 'MACHINE ?= "qemuarm64"
BB_NUMBER_THREADS = "7"
PARALLEL_MAKE = "-j 7"' >> conf/local.conf
```

## 5. Build image

```bash
hala@lat:~/poky/build
$ while ! bitbake core-image-minimal; do :; done
WARNING: Host distribution "linuxmint-21.3" has not been validated with this version of the build system; you may possibly experience unexpected failures. It is recommended that you use a tested distribution.
Loading cache: 100% |##################################################################################| Time: 0:00:00
Loaded 1644 entries from dependency cache.
NOTE: Resolving any missing task queue dependencies

Build Configuration:
BB_VERSION           = "2.0.0"
BUILD_SYS            = "x86_64-linux"
NATIVELSBSTRING      = "universal"
TARGET_SYS           = "aarch64-poky-linux"
MACHINE              = "qemuarm64"
DISTRO               = "poky"
DISTRO_VERSION       = "4.0.20"
TUNE_FEATURES        = "aarch64 armv8a crc cortexa57"
TARGET_FPU           = ""
meta
meta-poky
meta-yocto-bsp       = "kirkstone:322d4df8cb51b531a998de92298914a6710d7677"

Initialising tasks: 100% |#############################################################################| Time: 0:00:01
Sstate summary: Wanted 46 Local 0 Mirrors 0 Missed 46 Current 1303 (0% match, 96% complete)
NOTE: Executing Tasks
NOTE: Tasks Summary: Attempted 3435 tasks of which 3373 didn't need to be rerun and all succeeded.

Summary: There was 1 WARNING message.
```

## 6. Run image in qemu

```bash
$ runqemu qemuarm64 nographic
runqemu - INFO - Running MACHINE=qemuarm64 bitbake -e ...
runqemu - INFO - Continuing with the following parameters:
KERNEL: [/home/hala/poky/build/tmp/deploy/images/qemuarm64/Image]
MACHINE: [qemuarm64]
FSTYPE: [ext4]
ROOTFS: [/home/hala/poky/build/tmp/deploy/images/qemuarm64/core-image-minimal-qemuarm64-20240803103121.rootfs.ext4]
CONFFILE: [/home/hala/poky/build/tmp/deploy/images/qemuarm64/core-image-minimal-qemuarm64-20240803103121.qemuboot.conf]

runqemu - INFO - Setting up tap interface under sudo
[sudo] password for hala:
runqemu - INFO - Network configuration: ip=192.168.7.2::192.168.7.1:255.255.255.0::eth0:off:8.8.8.8
runqemu - INFO - Running /home/hala/poky/build/tmp/work/x86_64-linux/qemu-helper-native/1.0-r1/recipe-sysroot-native/usr/bin/qemu-system-aarch64 -device virtio-net-pci,netdev=net0,mac=52:54:00:12:34:02 -netdev tap,id=net0,ifname=tap0,script=no,downscript=no -object rng-random,filename=/dev/urandom,id=rng0 -device virtio-rng-pci,rng=rng0 -drive id=disk0,file=/home/hala/poky/build/tmp/deploy/images/qemuarm64/core-image-minimal-qemuarm64-20240803103121.rootfs.ext4,if=none,format=raw -device virtio-blk-pci,drive=disk0 -device qemu-xhci -device usb-tablet -device usb-kbd  -machine virt -cpu cortex-a57 -smp 4 -m 256 -serial mon:stdio -serial null -nographic -device virtio-gpu-pci -kernel /home/hala/poky/build/tmp/deploy/images/qemuarm64/Image -append 'root=/dev/vda rw  mem=256M ip=192.168.7.2::192.168.7.1:255.255.255.0::eth0:off:8.8.8.8 console=ttyAMA0 console=hvc0  '

... SNIP ...

[    2.396248] EXT4-fs (vda): mounted filesystem with ordered data mode. Opts: (null). Quota mode: disabled.
[    2.397783] VFS: Mounted root (ext4 filesystem) on device 253:0.
[    2.400485] devtmpfs: mounted
[    2.424037] usb 1-1: new high-speed USB device number 2 using xhci_hcd
[    2.446122] Freeing unused kernel memory: 3648K
[    2.447388] Run /sbin/init as init process
INIT: version 3.01 booting
[    2.602012] input: QEMU QEMU USB Tablet as /devices/platform/4010000000.pcie/pci0000:00/0000:00:04.0/usb1/1-1/1-1:1.0/0003:0627:0001.0001/input/input0
[    2.605194] hid-generic 0003:0627:0001.0001: input: USB HID v0.01 Mouse [QEMU QEMU USB Tablet] on usb-0000:00:04.0-1/input0
[    2.739341] usb 1-2: new high-speed USB device number 3 using xhci_hcd
[    2.892342] input: QEMU QEMU USB Keyboard as /devices/platform/4010000000.pcie/pci0000:00/0000:00:04.0/usb1/1-2/1-2:1.0/0003:0627:0001.0002/input/input1
[    2.956443] hid-generic 0003:0627:0001.0002: input: USB HID v1.11 Keyboard [QEMU QEMU USB Keyboard] on usb-0000:00:04.0-2/input0
Starting udev
[    3.147578] IPv6: ADDRCONF(NETDEV_CHANGE): eth0: link becomes ready
[    3.305020] udevd[131]: starting version 3.2.10
[    3.362091] udevd[132]: starting eudev-3.2.10
[    3.827321] EXT4-fs (vda): re-mounted. Opts: (null). Quota mode: disabled.
INIT: Entering runlevel: 5
Configuring network interfaces... ip: RTNETLINK answers: File exists
Starting syslogd/klogd: done

Poky (Yocto Project Reference Distro) 4.0.20 qemuarm64 /dev/ttyAMA0

qemuarm64 login: root
root@qemuarm64:~# echo hello yocto
hello yocto
root@qemuarm64:~# cat /proc/version
Linux version 5.15.157-yocto-standard (oe-user@oe-host) (aarch64-poky-linux-gcc (GCC) 11.4.0, GNU ld (GNU Binutils) 2.38.20220708) #1 SMP PREEMPT Thu May 2 16:13:45 UTC 2024
```
