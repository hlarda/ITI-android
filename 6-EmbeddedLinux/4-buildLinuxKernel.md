# Build linux kernel for Raspberry Pi 3

## 1. Clone the Linux kernel repository

```bash
cd ~
git clone --depth=1 git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git
```

## 2. Build for Vexpress a9

```bash
cd linux
make vexpress_defconfig
```

## 3. Configure the kernel

```bash
make menuconfig
```

1. Device Drivers -> Generic Driver Options -> Automount devtmpfs(enable)
2. General setup -> Kernel compression mode -> XZ
3. General Setup -> Local version - append to kernel release -> write version name (eg: -vexpress-a9-v1.0)

## 4.Build the kernel configuration

**Before building** to avoid errors
1. `ghp.h: no such file or directory` RUN `sudo apt-get install libgmp-dev`
2. `mpc.h: no such file or directory` RUN `sudo apt-get install libmpc-dev`

```bash
 make -j4 zImage modules dtbs
```

## PATH of zImage

```PATH
~/linux/arch/arm/boot/zImage
```