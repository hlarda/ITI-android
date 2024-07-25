# Root filesystem

## Table of Contents

-[How to tansfer root filesystem to the target](#how-to-tansfer-root-filesystem-to-the-target)
    - [Initramfs](#initramfs)
        - [How to load and use a standalone initramfs](#how-to-load-and-use-a-standalone-initramfs)
        - [SD card structure](#sd-card-structure)
    - [Boot the kernel with the initramfs](#2boot-the-kernel-with-the-initramfs)
        - [Run QEMU](#21run-qemu)
        - [Edit bootcmd in U-Boot to load and boot nessasary files](#22edit-bootcmd-in-u-boot-to-load-and-boot-nessasary-files)
    - [Final Result](#3final-result)

## How to tansfer root filesystem to the target

1. **initramfs**:

    - Also known as a ramdisk
    - Filesystem image loaded into RAM by the bootloader
    - Easy to create, no dependencies on mass storage drivers
    - Used in recovery mode or as the main root filesystem in small embedded devices
    - Volatile: changes at runtime are lost on reboot

2. **Disk image**:

    - Copy of the root filesystem formatted for mass storage devices
    - Commonly used option

3. **Network filesystem**:

    - Staging directory exported via NFS server
    - Mounted by the target at boot time
    - Preferred during development to avoid slow cycles of creating and reloading disk images

### Initramfs

- Initramfs is a compressed cpio archive.
- cpio: Old Unix archive format, similar to TAR and ZIP, easier to decode.

#### How to load and use a standalone initramfs

#### 1.Prerequisites

1. [Virtual SD card](../../../3-AdminLinux/tasks/AdminLinux_Task5/1-creatingVirtualSD.md)

    the SD image path is `~/SDs/sdInitRanFs.img`

    ```output
        $ lsblk | grep loop1
    loop1         7:1    0     1G  0 loop 
    ├─loop1p1   259:9    0   200M  0 part /home/hala/SDmount/initRamFs/boot
    └─loop1p2   259:10   0   823M  0 part /home/hala/SDmount/initRamFs/rootfs
    ```

2. [zImage and dtb file in the boot partition](../2-tftp+extlinux/3-buildKernel.md)
3. [rootfs in the SD card](../../RPI-3/3/1-Busybox+Inittab+QEMU.md)
    to be honest, I have copied the files to the boot and the rootfs partitions in the SD card from the previous sd card image.
4. **uRamdisk in the boot partition**

```bash
cd ~/SDmount/initRamFs/rootfs/
sudo find . -print -depth | cpio -H newc -ov --owner root:root > ../initramfs.cpio
cd ..
gzip initramfs.cpio
gzip -t initramfs.cpio.gz       # Check the integrity of the compressed file
mkimage -A arm -O linux -T ramdisk -d initramfs.cpio.gz uRamdisk
sudo cp uRamdisk boot/
```

`-print -depth`: Print the name of the file before the contents of the directory. `-H newc`: Use the newc format for the cpio archive. `-ov`: Create a new archive. `--owner root:root`: Set the owner and group of the files in the archive. `-A arm`: Set the architecture to ARM. `-O linux`: Set the operating system to Linux. `-T ramdisk`: Set the image type to ramdisk. `-d initramfs.cpio.gz`: Use the initramfs.cpio.gz file as the input.

##### SD card structure

make sure rootfs directory is owned by root and if not RUN: `sudo chown -R root:root rootfs`

```bash
hala@lat:~/SDmount/initRamFs
$ ls
boot  initramfs.cpio.gz  rootfs  uRamdisk
hala@lat:~/SDmount/initRamFs
$ ls -al boot/
total 5588
drwxr-xr-x 2 root root   16384 Jan  1  1970 .
drwxrwxr-x 1 hala hala      70 Jul 25 02:47 ..
-rwxr-xr-x 1 root root    7100 Jul 25 01:36 init.out
-rwxr-xr-x 1 root root  622332 Jul 25 01:36 u-boot.bin
-rwxr-xr-x 1 root root  262144 Jul 25 01:36 uboot.env
-rwxr-xr-x 1 root root  719051 Jul 25 02:49 uRamdisk
-rwxr-xr-x 1 root root   14329 Jul 25 01:36 vexpress-v2p-ca9.dtb
-rwxr-xr-x 1 root root 4075328 Jul 25 01:36 zImage
hala@lat:~/SDmount/initRamFs
$ ls -al rootfs/
total 68
drwxr-xr-x 15 root root  4096 Jul 25 01:38 .
drwxrwxr-x  1 hala hala    70 Jul 25 02:47 ..
drwxr-xr-x  2 root root  4096 Jul 25 01:38 bin
drwxr-xr-x  2 root root  4096 Jul 25 01:38 boot
drwxr-xr-x  2 root root  4096 Jul 25 01:38 dev
drwxr-xr-x  3 root root  4096 Jul 25 01:38 etc
drwxr-xr-x  2 root root  4096 Jul 25 01:38 home
lrwxrwxrwx  1 root root    11 Jul 25 01:38 linuxrc -> bin/busybox
drwx------  2 root root 16384 Jul 25 01:34 lost+found
drwxr-xr-x  2 root root  4096 Jul 25 01:38 mnt
drwxr-xr-x  2 root root  4096 Jul 25 01:38 proc
drwxr-xr-x  2 root root  4096 Jul 25 01:38 root
drwxr-xr-x  2 root root  4096 Jul 25 01:38 sbin
drwxr-xr-x  2 root root  4096 Jul 25 01:38 srv
drwxr-xr-x  2 root root  4096 Jul 25 01:38 sys
drwxr-xr-x  4 root root  4096 Jul 25 01:38 usr
```

#### 2.Boot the kernel with the initramfs

##### 2.1.Run QEMU

```bash
sudo qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -sd ~/SDs/sdInitRanFs.img 
```

##### 2.2.Edit bootcmd in U-Boot to load and boot nessasary files

```uboot
RPI$ echo $bootargs
console=ttyAMA0 root=/dev/mmcblk0p2 rootfstype=ext4 rw rootwait init=/sbin/init
RPI$setenv bootcmd 'echo Hello Sweetie!; \        
> fatload mmc 0:1 ${kernel_addr_r} zImage; \
> fatload mmc 0:1 ${fdt_addr_r} vexpress-v2p-ca9.dtb; \
> fatload mmc 0:1 0x60600000 uRamdisk; \ 
> bootz $kernel_addr_r 0x60600000 $fdt_addr_r '
RPI$ saveenv 
Saving Environment to FAT... OK
RPI$ reset                                        
resetting ...
```

#### 3.Final Result

```output
U-Boot 2024.07-00787-g2c7c90c34717 (Jul 22 2024 - 19:08:07 +0300)

DRAM:  128 MiB
WARNING: Caches not enabled
Core:  23 devices, 11 uclasses, devicetree: embed
Flash: 128 MiB
MMC:   mmci@5000: 0
Loading Environment from FAT... OK
In:    uart@9000
Out:   uart@9000
Err:   uart@9000
Net:   eth0: ethernet@3,02000000
Hit any key to stop autoboot:  0 
Hello Sweetie!
4075328 bytes read in 559 ms (7 MiB/s)
14329 bytes read in 10 ms (1.4 MiB/s)
719051 bytes read in 104 ms (6.6 MiB/s)
Kernel image @ 0x60100000 [ 0x000000 - 0x3e2f40 ]
## Loading init Ramdisk from Legacy Image at 60600000 ...
   Image Name:   
   Image Type:   ARM Linux RAMDisk Image (gzip compressed)
   Data Size:    718987 Bytes = 702.1 KiB
   Load Address: 00000000
   Entry Point:  00000000
   Verifying Checksum ... OK
## Flattened Device Tree blob at 60000000
   Booting using the fdt blob at 0x60000000
Working FDT set to 60000000
   Loading Ramdisk to 66a60000, end 66b0f88b ... OK
   Loading Device Tree to 66a59000, end 66a5f7f8 ... OK
Working FDT set to 66a59000

Starting kernel ...

Booting Linux on physical CPU 0x0
Linux version 6.10.0hla-v1.0+ (hala@lat) (arm-cortexa9_neon-linux-musleabihf-gcc (crosstool-NG 1.26.0.97_839bfbe) 14.1.0, GNU ld (crosstool-NG 1.26.0.97_839bfbe) 2.42) #1 SMP Mon Jul 22 14:45:56 EEST 2024
CPU: ARMv7 Processor [410fc090] revision 0 (ARMv7), cr=10c5387d
CPU: PIPT / VIPT nonaliasing data cache, VIPT nonaliasing instruction cache
OF: fdt: Machine model: V2P-CA9
OF: fdt: Ignoring memory block 0x80000000 - 0x80000004
Memory policy: Data cache writeback
Reserved memory: created DMA memory pool at 0x4c000000, size 8 MiB
OF: reserved mem: initialized node vram@4c000000, compatible id shared-dma-pool
OF: reserved mem: 0x4c000000..0x4c7fffff (8192 KiB) nomap non-reusable vram@4c000000
cma: Reserved 16 MiB at 0x67000000 on node -1
Zone ranges:
  Normal   [mem 0x0000000060000000-0x0000000067ffffff]
Movable zone start for each node
Early memory node ranges
  node   0: [mem 0x0000000060000000-0x0000000067ffffff]
Initmem setup node 0 [mem 0x0000000060000000-0x0000000067ffffff]
CPU: All CPU(s) started in SVC mode.
percpu: Embedded 17 pages/cpu s38604 r8192 d22836 u69632
Kernel command line: console=ttyAMA0 root=/dev/mmcblk0p2 rootfstype=ext4 rw rootwait init=/sbin/init
printk: log_buf_len individual max cpu contribution: 4096 bytes
printk: log_buf_len total cpu_extra contributions: 12288 bytes
printk: log_buf_len min size: 16384 bytes
printk: log_buf_len: 32768 bytes
printk: early log buf free: 14804(90%)
Dentry cache hash table entries: 16384 (order: 4, 65536 bytes, linear)
Inode-cache hash table entries: 8192 (order: 3, 32768 bytes, linear)
Built 1 zonelists, mobility grouping on.  Total pages: 32768
mem auto-init: stack:all(zero), heap alloc:off, heap free:off
SLUB: HWalign=64, Order=0-3, MinObjects=0, CPUs=4, Nodes=1
trace event string verifier disabled
rcu: Hierarchical RCU implementation.
rcu: 	RCU event tracing is enabled.
rcu: 	RCU restricting CPUs from NR_CPUS=8 to nr_cpu_ids=4.
rcu: RCU calculated value of scheduler-enlistment delay is 10 jiffies.
rcu: Adjusting geometry for rcu_fanout_leaf=16, nr_cpu_ids=4
NR_IRQS: 16, nr_irqs: 16, preallocated irqs: 16
GIC CPU mask not found - kernel will fail to boot.
GIC CPU mask not found - kernel will fail to boot.
L2C: platform modifies aux control register: 0x02020000 -> 0x02420000
L2C: DT/platform modifies aux control register: 0x02020000 -> 0x02420000
L2C-310 enabling early BRESP for Cortex-A9
L2C-310 full line of zeros enabled for Cortex-A9
L2C-310 dynamic clock gating disabled, standby mode disabled
L2C-310 cache controller enabled, 8 ways, 128 kB
L2C-310: CACHE_ID 0x410000c8, AUX_CTRL 0x46420001
rcu: srcu_init: Setting srcu_struct sizes based on contention.
sched_clock: 32 bits at 24MHz, resolution 41ns, wraps every 89478484971ns
clocksource: arm,sp804: mask: 0xffffffff max_cycles: 0xffffffff, max_idle_ns: 1911260446275 ns
smp_twd: clock not found -2
Console: colour dummy device 80x30
Calibrating local timer... 90.65MHz.
Calibrating delay loop... 835.58 BogoMIPS (lpj=4177920)
CPU: Testing write buffer coherency: ok
CPU0: Spectre v2: using BPIALL workaround
pid_max: default: 32768 minimum: 301
Mount-cache hash table entries: 1024 (order: 0, 4096 bytes, linear)
Mountpoint-cache hash table entries: 1024 (order: 0, 4096 bytes, linear)
CPU0: thread -1, cpu 0, socket 0, mpidr 80000000
Setting up static identity map for 0x60100000 - 0x60100060
rcu: Hierarchical SRCU implementation.
rcu: 	Max phase no-delay instances is 1000.
smp: Bringing up secondary CPUs ...
smp: Brought up 1 node, 1 CPU
SMP: Total of 1 processors activated (835.58 BogoMIPS).
CPU: All CPU(s) started in SVC mode.
Memory: 96392K/131072K available (9216K kernel code, 737K rwdata, 2144K rodata, 1024K init, 189K bss, 16596K reserved, 16384K cma-reserved)
devtmpfs: initialized
VFP support v0.3: implementor 41 architecture 3 part 30 variant 9 rev 0
clocksource: jiffies: mask: 0xffffffff max_cycles: 0xffffffff, max_idle_ns: 19112604462750000 ns
futex hash table entries: 1024 (order: 4, 65536 bytes, linear)
NET: Registered PF_NETLINK/PF_ROUTE protocol family
DMA: preallocated 256 KiB pool for atomic coherent allocations
cpuidle: using governor ladder
hw-breakpoint: debug architecture 0x4 unsupported.
Serial: AMBA PL011 UART driver
amba 1001f000.clcd: Fixed dependency cycle(s) with /bus@40000000/motherboard-bus@40000000/iofpga@7,00000000/i2c@16000/dvi-transmitter@39
amba 10020000.clcd: Fixed dependency cycle(s) with /bus@40000000/motherboard-bus@40000000/iofpga@7,00000000/i2c@16000/dvi-transmitter@39
SCSI subsystem initialized
usbcore: registered new interface driver usbfs
usbcore: registered new interface driver hub
usbcore: registered new device driver usb
amba 10020000.clcd: Fixed dependency cycle(s) with /bus@40000000/motherboard-bus@40000000/iofpga@7,00000000/i2c@16000/dvi-transmitter@39
amba 1001f000.clcd: Fixed dependency cycle(s) with /bus@40000000/motherboard-bus@40000000/iofpga@7,00000000/i2c@16000/dvi-transmitter@39
i2c 0-0039: Fixed dependency cycle(s) with /bus@40000000/motherboard-bus@40000000/iofpga@7,00000000/clcd@1f000
i2c 0-0039: Fixed dependency cycle(s) with /clcd@10020000
pps_core: LinuxPPS API ver. 1 registered
pps_core: Software ver. 5.3.6 - Copyright 2005-2007 Rodolfo Giometti <giometti@linux.it>
PTP clock support registered
Advanced Linux Sound Architecture Driver Initialized.
clocksource: Switched to clocksource arm,sp804
NET: Registered PF_INET protocol family
IP idents hash table entries: 2048 (order: 2, 16384 bytes, linear)
tcp_listen_portaddr_hash hash table entries: 512 (order: 0, 4096 bytes, linear)
Table-perturb hash table entries: 65536 (order: 6, 262144 bytes, linear)
TCP established hash table entries: 1024 (order: 0, 4096 bytes, linear)
TCP bind hash table entries: 1024 (order: 2, 16384 bytes, linear)
TCP: Hash tables configured (established 1024 bind 1024)
UDP hash table entries: 256 (order: 1, 8192 bytes, linear)
UDP-Lite hash table entries: 256 (order: 1, 8192 bytes, linear)
NET: Registered PF_UNIX/PF_LOCAL protocol family
RPC: Registered named UNIX socket transport module.
RPC: Registered udp transport module.
RPC: Registered tcp transport module.
RPC: Registered tcp-with-tls transport module.
RPC: Registered tcp NFSv4.1 backchannel transport module.
Unpacking initramfs...
workingset: timestamp_bits=30 max_order=15 bucket_order=0
squashfs: version 4.0 (2009/01/31) Phillip Lougher
jffs2: version 2.2. (NAND) © 2001-2006 Red Hat, Inc.
9p: Installing v9fs 9p2000 file system support
io scheduler mq-deadline registered
io scheduler kyber registered
io scheduler bfq registered
sii902x 0-0060: supply iovcc not found, using dummy regulator
sii902x 0-0060: supply cvcc12 not found, using dummy regulator
Freeing initrd memory: 704K
simple-pm-bus bus@40000000:motherboard-bus@40000000:iofpga@7,00000000: Failed to create device link (0x180) with dcc:clock-controller-2
simple-pm-bus bus@40000000:motherboard-bus@40000000:iofpga@7,00000000: Failed to create device link (0x180) with dcc:clock-controller-2
physmap-flash 40000000.flash: physmap platform flash device: [mem 0x40000000-0x43ffffff]
40000000.flash: Found 2 x16 devices at 0x0 in 32-bit bank. Manufacturer ID 0x000000 Chip ID 0x000000
Intel/Sharp Extended Query Table at 0x0031
Using buffer write method
physmap-flash 40000000.flash: physmap platform flash device: [mem 0x44000000-0x47ffffff]
40000000.flash: Found 2 x16 devices at 0x0 in 32-bit bank. Manufacturer ID 0x000000 Chip ID 0x000000
Intel/Sharp Extended Query Table at 0x0031
Using buffer write method
Concatenating MTD devices:
(0): "40000000.flash"
(1): "40000000.flash"
into device "40000000.flash"
physmap-flash 48000000.psram: physmap platform flash device: [mem 0x48000000-0x49ffffff]
smsc911x 4e000000.ethernet eth0: MAC Address: 52:54:00:12:34:56
isp1760 4f000000.usb: isp1760 bus width: 32, oc: digital
isp1760 4f000000.usb: NXP ISP1760 USB Host Controller
isp1760 4f000000.usb: new USB bus registered, assigned bus number 1
isp1760 4f000000.usb: Scratch test failed. 0x00000000
isp1760 4f000000.usb: can't setup: -19
isp1760 4f000000.usb: USB bus 1 deregistered
usbcore: registered new interface driver usb-storage
rtc-pl031 10017000.rtc: registered as rtc0
rtc-pl031 10017000.rtc: setting system clock to 2024-07-25T00:15:24 UTC (1721866524)
mmci-pl18x 10005000.mmci: Got CD GPIO
mmci-pl18x 10005000.mmci: Got WP GPIO
mmci-pl18x 10005000.mmci: mmc0: PL181 manf 41 rev0 at 0x10005000 irq 31,32 (pio)
ledtrig-cpu: registered to indicate activity on CPUs
usbcore: registered new interface driver usbhid
usbhid: USB HID core driver
hw perfevents: enabled with armv7_cortex_a9 PMU driver, 5 counters available
aaci-pl041 10004000.aaci: ARM AC'97 Interface PL041 rev0 at 0x10004000, irq 37
aaci-pl041 10004000.aaci: FIFO 512 entries
NET: Registered PF_PACKET protocol family
9pnet: Installing 9P2000 support
Registering SWP/SWPB emulation handler
Timer migration: 1 hierarchy levels; 8 children per group; 1 crossnode level
10009000.serial: ttyAMA0 at MMIO 0x10009000 (irq = 38, base_baud = 0) is a PL011 rev1
printk: legacy console [ttyAMA0] enabled
1000a000.serial: ttyAMA1 at MMIO 0x1000a000 (irq = 39, base_baud = 0) is a PL011 rev1
1000b000.serial: ttyAMA2 at MMIO 0x1000b000 (irq = 40, base_baud = 0) is a PL011 rev1
1000c000.serial: ttyAMA3 at MMIO 0x1000c000 (irq = 41, base_baud = 0) is a PL011 rev1
drm-clcd-pl111 1001f000.clcd: assigned reserved memory node vram@4c000000
drm-clcd-pl111 1001f000.clcd: using device-specific reserved memory
drm-clcd-pl111 1001f000.clcd: core tile graphics present
drm-clcd-pl111 1001f000.clcd: this device will be deactivated
drm-clcd-pl111 1001f000.clcd: Versatile Express init failed - -19
mmc0: new SD card at address 4567
drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
mmcblk0: mmc0:4567 QEMU! 1.00 GiB
drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
 mmcblk0: p1 p2
drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
clk: Disabling unused clocks
ALSA device list:
  #0: ARM AC'97 Interface PL041 rev0 at 0x10004000, irq 37
input: ImExPS/2 Generic Explorer Mouse as /devices/platform/bus@40000000/bus@40000000:motherboard-bus@40000000/bus@40000000:motherboard-bus@40000000:iofpga@7,00000000/10007000.kmi/serio1/input/input1
drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
input: AT Raw Set 2 keyboard as /devices/platform/bus@40000000/bus@40000000:motherboard-bus@40000000/bus@40000000:motherboard-bus@40000000:iofpga@7,00000000/10006000.kmi/serio0/input/input2
drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
EXT4-fs (mmcblk0p2): recovery complete
EXT4-fs (mmcblk0p2): mounted filesystem ae8b9fde-9bd6-4061-acd0-c839eb9fa38e r/w with ordered data mode. Quota mode: disabled.
VFS: Mounted root (ext4 filesystem) on device 179:2.
devtmpfs: mounted
Freeing unused kernel image (initmem) memory: 1024K
Run /sbin/init as init process
random: crng init done

Please press Enter to activate this console.
~ # ls
bin         etc         lost+found  root        sys
boot        home        mnt         sbin        usr
dev         linuxrc     proc        srv
```

To determine if the `uRamdisk` has been successfully loaded and used, you can look for specific messages in the boot log that indicate the loading and utilization of the initramfs. Here are the key indicators from log:

1. **Loading Initramfs**:

   ```log
   ## Loading init Ramdisk from Legacy Image at 60600000 ...
      Image Name:   
      Image Type:   ARM Linux RAMDisk Image (gzip compressed)
      Data Size:    718987 Bytes = 702.1 KiB
      Load Address: 00000000
      Entry Point:  00000000
      Verifying Checksum ... OK
   ```

2. **Freeing Initrd Memory**:

   ```log
   Freeing initrd memory: 704K
   ```

3. **Unpacking Initramfs**:

   ```log
   Unpacking initramfs...
   ```
