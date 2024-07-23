# Busybox with init script in QEMU

## Busybox

- BusyBox combines tiny versions of many common UNIX utilities into a single
  small executable.
-It provides minimalist replacements for most of the
  utilities you usually use.
- written with size-optimization and limited resources in mind to reduce run-time memory usage.

## A.To Build file system

### 1.Clone Busybox

```bash
git clone https://github.com/mirror/busybox.git
cd busybox/
```

### 2. Configure Busybox

```bash
make menuconfig
```

settings -> Build static binary (no shared libs)

### 3. Build Busybox

**Before building** to avoid error `‘TCA_CBQ_MAX’ undeclared` because of some kernel version's removal for CBQ used by TC. You need to disable it by editing  `busybox/.config`.

Find `CONFIG_TC=y` and change it to `CONFIG_TC=n` then save, exit and build.

```bash
make
make install        # _install to PATH/busybox/_install
```

### 4.Mount your virtual SD card

```bash
$ sudo losetup -f --show --partscan sd.img
/dev/loop0
$ sudo mount /dev/loop0p1 ~/SD/boot
$ sudo mount /dev/loop0p2 ~/SD/rootfs/
```

Or create if not exists. [Check this](../../../3-AdminLinux/tasks/AdminLinux_Task5/1-creatingVirtualSD.md)

### 5.Create root file system

copy eiles under `busybox/_install` to rootfs.

```bash
rsync -avz busybox/_install/ ~/SD/rootfs/
```

### 6.Create missing directories

```bash
mkdir -p ./dev /etc
```

### 7.Create inittab script

**Syntax:**

`nobe` : `dev if used` : `action` : `app ro run with action`

```inittab
# inittab file 
::sysinit:/etc/init.d/rcS
# Start an "askfirst" shell on the console (whatever that may be)
ttyAMA0::askfirst:-/bin/sh
# Stuff to do when restarting the init process
::restart:/sbin/init
```

## B. Run QEMU

### 1.Build kernel

[Check this](../../RPI-3/2-tftp+extlinux/3-buildKernel.md)

### 2. Add kernel and dbt files to boot partition

```bash
sudo cp ~/linux/arch/arm/boot/zImage ~/SD/boot/
sudo cp ~/linux/arch/arm/boot/dts/arm/vexpress-v2p-ca9.dtb ~/SD/boot/
```

### 3. Run QEMU

```bash
sudo qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -sd ~/sd.img 
```

### 4.Edit bootcmd to load kernel and dtb file

```uboot
RPI$ editenv bootcmd
edit: echo Hello Sweetie!; fatload mmc 0:1 ${kernel_addr_r} zImage; fatload mmc 0:1 ${fdt_addr_r} vexpress-v2p-ca9.dtb; bootz ${kernel_addr_r} - ${fdt_addr_r}
```

### 5. edit bootargs

```uboot
editenv bootargs 
edit: console=ttyAMA0 root=/dev/mmcblk0p2 rootfstype=ext4 rw rootwait init=/sbin/init
```

### 6.Save and Reset QEMU

```uboot
saveenv
reset
```
