# Choose a rootfs partition to boot from

## Srep1: prepare SD card

### 1.1. Create SD card image

it has three partitions: boot, rootfs1, rootfs2 contains busybox file anf initial setup as done in [busybox task](../3/1-Busybox+Inittab+QEMU.md)

### 1.2. Create Script to choose which rootfs in both rootfs partitions

```bash
touch ~/SDmount/dualRootfs/rootfs2/etc/init.d/whichRootfs.sh 
touch ~/SDmount/dualRootfs/rootfs1/etc/init.d/whichRootfs.sh
sudo chmod +x ~/SDmount/dualRootfs/rootfs2/etc/init.d/whichRootfs.sh
sudo chmod +x ~/SDmount/dualRootfs/rootfs1/etc/init.d/whichRootfs.sh
```

```bash
#!/bin/sh

echo "Press 1 to mount root filesystem 1, or 2 to mount root filesystem 2:"
read choice

if [ "$choice" -eq 1 ]; then
    echo "Mounting root filesystem 1..."
    mount /dev/mmcblk0p2 /mnt/rootfs
    if [ $? -eq 0 ]; then
        echo "Mount successful. Changing root..."
        chroot /mnt/rootfs /bin/sh
    else
        echo "Mount failed. Exiting..."
        exit 1
    fi
elif [ "$choice" -eq 2 ]; then
    echo "Mounting root filesystem 2..."
    mount /dev/mmcblk0p3 /mnt/rootfs
    if [ $? -eq 0 ]; then
        echo "Mount successful. Changing root..."
        chroot /mnt/rootfs /bin/sh
    else
        echo "Mount failed. Exiting..."
        exit 1
    fi
else
    echo "Invalid choice. Rebooting..."
    reboot
fi
```

### 1.3. Update inittab file IN BOTH rootfs partitions

```bash
# inittab file 
::sysinit:/etc/init.d/rcS
::sysinit:/etc/init.d/whichRootfs.sh
ttyAMA0::askfirst:/bin/sh
::restart:/sbin/init
```

## Run QEMU

```bash
sudo qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -sd ~/SDs/dualRootfs.img
```

## Edit Uboot Variables

1. bootcmd

    ```uboot
    setenv bootcmd 'echo Hello Sweetie!; fatload mmc 0:1 ${kernel_addr_r} zImage; fatload mmc 0:1 ${fdt_addr_r} vexpress-v2p-ca9.dtb; bootz ${kernel_addr_r} - ${fdt_addr_r}'
    ```

2. bootargs

    ```uboot
    setenv bootargs 'console=ttyAMA0,115200 root=/dev/mmcblk0p2 rootfstype=ext4 rw rootwait'
    ```

3. Save and reset

    ```uboot
    saveenv
    reset
    ```

## Output

1. Press 1 to mount root filesystem 1

    ```bash
    $ sudo qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -sd ~/SDs/dualRootfs.img 
    WARNING: Image format was not specified for '/home/hala/SDs/dualRootfs.img' and probing guessed raw.
             Automatically detecting the format is dangerous for raw images, write operations on block 0 will be restricted.
             Specify the 'raw' format explicitly to remove the restrictions.


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
    4075328 bytes read in 1867 ms (2.1 MiB/s)
    14329 bytes read in 20 ms (699.2 KiB/s)
    Kernel image @ 0x60100000 [ 0x000000 - 0x3e2f40 ]
    ## Flattened Device Tree blob at 60000000
       Booting using the fdt blob at 0x60000000
    Working FDT set to 60000000
       Loading Device Tree to 66b07000, end 66b0d7f8 ... OK
    Working FDT set to 66b07000

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
    Kernel command line: console=ttyAMA0,115200 root=/dev/mmcblk0p2 rootfstype=ext4 rw rootwait
    printk: log_buf_len individual max cpu contribution: 4096 bytes
    printk: log_buf_len total cpu_extra contributions: 12288 bytes
    printk: log_buf_len min size: 16384 bytes
    printk: log_buf_len: 32768 bytes
    printk: early log buf free: 14812(90%)
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
    Calibrating local timer... 87.59MHz.
    Calibrating delay loop... 921.60 BogoMIPS (lpj=4608000)
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
    SMP: Total of 1 processors activated (921.60 BogoMIPS).
    CPU: All CPU(s) started in SVC mode.
    Memory: 97400K/131072K available (9216K kernel code, 737K rwdata, 2144K rodata, 1024K init, 189K bss, 15892K reserved, 16384K cma-reserved)
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
    workingset: timestamp_bits=30 max_order=15 bucket_order=0
    squashfs: version 4.0 (2009/01/31) Phillip Lougher
    jffs2: version 2.2. (NAND) © 2001-2006 Red Hat, Inc.
    9p: Installing v9fs 9p2000 file system support
    io scheduler mq-deadline registered
    io scheduler kyber registered
    io scheduler bfq registered
    sii902x 0-0060: supply iovcc not found, using dummy regulator
    sii902x 0-0060: supply cvcc12 not found, using dummy regulator
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
    rtc-pl031 10017000.rtc: setting system clock to 2024-07-27T02:14:54 UTC (1722046494)
    mmci-pl18x 10005000.mmci: Got CD GPIO
    mmci-pl18x 10005000.mmci: Got WP GPIO
    mmci-pl18x 10005000.mmci: mmc0: PL181 manf 41 rev0 at 0x10005000 irq 31,32 (pio)
    ledtrig-cpu: registered to indicate activity on CPUs
    usbcore: registered new interface driver usbhid
    usbhid: USB HID core driver
    hw perfevents: enabled with armv7_cortex_a9 PMU driver, 7 counters available
    mmc0: new SD card at address 4567
    mmcblk0: mmc0:4567 QEMU! 1.00 GiB
    aaci-pl041 10004000.aaci: ARM AC'97 Interface PL041 rev0 at 0x10004000, irq 37
    aaci-pl041 10004000.aaci: FIFO 512 entries
    NET: Registered PF_PACKET protocol family
    9pnet: Installing 9P2000 support
    Registering SWP/SWPB emulation handler
    input: AT Raw Set 2 keyboard as /devices/platform/bus@40000000/bus@40000000:motherboard-bus@40000000/bus@40000000:motherboard-bus@40000000:iofpga@7,00000000/10006000.kmi/serio0/input/input0
     mmcblk0: p1 p2 p3
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
    drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
    drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
    drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
    drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
    clk: Disabling unused clocks
    ALSA device list:
      #0: ARM AC'97 Interface PL041 rev0 at 0x10004000, irq 37
    input: ImExPS/2 Generic Explorer Mouse as /devices/platform/bus@40000000/bus@40000000:motherboard-bus@40000000/bus@40000000:motherboard-bus@40000000:iofpga@7,00000000/10007000.kmi/serio1/input/input2
    drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
    drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
    EXT4-fs (mmcblk0p2): warning: mounting fs with errors, running e2fsck is recommended
    EXT4-fs (mmcblk0p2): recovery complete
    EXT4-fs (mmcblk0p2): mounted filesystem af1eda63-20c1-41cd-b6f0-3ad12f240873 r/w with ordered data mode. Quota mode: disabled.
    VFS: Mounted root (ext4 filesystem) on device 179:2.
    devtmpfs: mounted
    Freeing unused kernel image (initmem) memory: 1024K
    Run /sbin/init as init process
    random: crng init done
    Press 1 to mount root filesystem 1, or 2 to mount root filesystem 2:
    1
    Mounting root filesystem 1...
    /dev/mmcblk0p2: Can't open blockdev
    Mount successful. Changing root...
    /bin/sh: can't access tty; job control turned off
    EXT4-fs error (device mmcblk0p2): ext4_lookup:1813: inode #2: comm sh: deleted inode referenced: 419
    ~ # EXT4-fs error (device mmcblk0p2): ext4_mb_generate_buddy:1217: group 1, block bitmap and bg descriptor inconsistent: 32416 vs 32417 free clusters
    ls
    EXT4-fs error (device mmcblk0p2): ext4_lookup:1813: inode #2: comm sh: deleted inode referenced: 419
    bin         etc         lost+found  root        srv
    boot        home        mnt         rootfs1?    sys
    dev         linuxrc     proc        sbin        usr
    ```

2. Press 2 to mount root filesystem 2

    ```bash
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
    4075328 bytes read in 2181 ms (1.8 MiB/s)
    14329 bytes read in 21 ms (666 KiB/s)
    Kernel image @ 0x60100000 [ 0x000000 - 0x3e2f40 ]
    ## Flattened Device Tree blob at 60000000
       Booting using the fdt blob at 0x60000000
    Working FDT set to 60000000
       Loading Device Tree to 66b07000, end 66b0d7f8 ... OK
    Working FDT set to 66b07000
    
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
    Kernel command line: console=ttyAMA0,115200 root=/dev/mmcblk0p2 rootfstype=ext4 rw rootwait
    printk: log_buf_len individual max cpu contribution: 4096 bytes
    printk: log_buf_len total cpu_extra contributions: 12288 bytes
    printk: log_buf_len min size: 16384 bytes
    printk: log_buf_len: 32768 bytes
    printk: early log buf free: 14812(90%)
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
    Calibrating local timer... 91.28MHz.
    Calibrating delay loop... 956.00 BogoMIPS (lpj=4780032)
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
    SMP: Total of 1 processors activated (956.00 BogoMIPS).
    CPU: All CPU(s) started in SVC mode.
    Memory: 97400K/131072K available (9216K kernel code, 737K rwdata, 2144K rodata, 1024K init, 189K bss, 15892K reserved, 16384K cma-reserved)
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
    workingset: timestamp_bits=30 max_order=15 bucket_order=0
    squashfs: version 4.0 (2009/01/31) Phillip Lougher
    jffs2: version 2.2. (NAND) © 2001-2006 Red Hat, Inc.
    9p: Installing v9fs 9p2000 file system support
    io scheduler mq-deadline registered
    io scheduler kyber registered
    io scheduler bfq registered
    sii902x 0-0060: supply iovcc not found, using dummy regulator
    sii902x 0-0060: supply cvcc12 not found, using dummy regulator
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
    rtc-pl031 10017000.rtc: setting system clock to 2024-07-27T02:15:30 UTC (1722046530)
    mmci-pl18x 10005000.mmci: Got CD GPIO
    mmci-pl18x 10005000.mmci: Got WP GPIO
    mmci-pl18x 10005000.mmci: mmc0: PL181 manf 41 rev0 at 0x10005000 irq 31,32 (pio)
    ledtrig-cpu: registered to indicate activity on CPUs
    usbcore: registered new interface driver usbhid
    usbhid: USB HID core driver
    hw perfevents: enabled with armv7_cortex_a9 PMU driver, 7 counters available
    aaci-pl041 10004000.aaci: ARM AC'97 Interface PL041 rev0 at 0x10004000, irq 37
    aaci-pl041 10004000.aaci: FIFO 512 entries
    NET: Registered PF_PACKET protocol family
    9pnet: Installing 9P2000 support
    Registering SWP/SWPB emulation handler
    mmc0: new SD card at address 4567
    mmcblk0: mmc0:4567 QEMU! 1.00 GiB
    Timer migration: 1 hierarchy levels; 8 children per group; 1 crossnode level
    input: AT Raw Set 2 keyboard as /devices/platform/bus@40000000/bus@40000000:motherboard-bus@40000000/bus@40000000:motherboard-bus@40000000:iofpga@7,00000000/10006000.kmi/serio0/input/input0
    10009000.serial: ttyAMA0 at MMIO 0x10009000 (irq = 38, base_baud = 0) is a PL011 rev1
    printk: legacy console [ttyAMA0] enabled
     mmcblk0: p1 p2 p3
    1000a000.serial: ttyAMA1 at MMIO 0x1000a000 (irq = 39, base_baud = 0) is a PL011 rev1
    1000b000.serial: ttyAMA2 at MMIO 0x1000b000 (irq = 40, base_baud = 0) is a PL011 rev1
    1000c000.serial: ttyAMA3 at MMIO 0x1000c000 (irq = 41, base_baud = 0) is a PL011 rev1
    drm-clcd-pl111 1001f000.clcd: assigned reserved memory node vram@4c000000
    drm-clcd-pl111 1001f000.clcd: using device-specific reserved memory
    drm-clcd-pl111 1001f000.clcd: core tile graphics present
    drm-clcd-pl111 1001f000.clcd: this device will be deactivated
    drm-clcd-pl111 1001f000.clcd: Versatile Express init failed - -19
    drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
    drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
    drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
    drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
    clk: Disabling unused clocks
    ALSA device list:
      #0: ARM AC'97 Interface PL041 rev0 at 0x10004000, irq 37
    input: ImExPS/2 Generic Explorer Mouse as /devices/platform/bus@40000000/bus@40000000:motherboard-bus@40000000/bus@40000000:motherboard-bus@40000000:iofpga@7,00000000/10007000.kmi/serio1/input/input2
    drm-clcd-pl111 10020000.clcd: DVI muxed to daughterboard 1 (core tile) CLCD
    drm-clcd-pl111 10020000.clcd: initializing Versatile Express PL111
    EXT4-fs (mmcblk0p2): warning: mounting fs with errors, running e2fsck is recommended
    EXT4-fs (mmcblk0p2): recovery complete
    EXT4-fs (mmcblk0p2): mounted filesystem af1eda63-20c1-41cd-b6f0-3ad12f240873 r/w with ordered data mode. Quota mode: disabled.
    VFS: Mounted root (ext4 filesystem) on device 179:2.
    devtmpfs: mounted
    Freeing unused kernel image (initmem) memory: 1024K
    Run /sbin/init as init process
    random: crng init done
    Press 1 to mount root filesystem 1, or 2 to mount root filesystem 2:
    2
    Mounting root filesystem 2...
    EXT4-fs (mmcblk0p3): warning: mounting fs with errors, running e2fsck is recommended
    EXT4-fs (mmcblk0p3): recovery complete
    EXT4-fs (mmcblk0p3): mounted filesystem d0e25021-6153-40ba-8b4e-673703a0b67f r/w with ordered data mode. Quota mode: disabled.
    Mount successful. Changing root...
    EXT4-fs error (device mmcblk0p2): ext4_mb_generate_buddy:1217: group 1, block bitmap and bg descriptor inconsistent: 32416 vs 32417 free clusters
    /bin/sh: can't access tty; job control turned off
    EXT4-fs error (device mmcblk0p3): ext4_lookup:1813: inode #2: comm sh: deleted inode referenced: 419
    ~ # ls
    EXT4-fs error (device mmcblk0p3): ext4_lookup:1813: inode #2: comm sh: deleted inode referenced: 419
    bin         etc         lost+found  root        srv
    boot        home        mnt         rootfs2?    sys
    dev         linuxrc     proc        sbin        usr
    ```
