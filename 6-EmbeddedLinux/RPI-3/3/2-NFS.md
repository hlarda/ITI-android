# Mount rootfs through NFS

## Table of Contents

- [Mount rootfs through NFS](#mount-rootfs-through-nfs)
  - [Table of Contents](#table-of-contents)
  - [what is NFS?](#what-is-nfs)
  - [why to access rootfs over NFS?](#why-to-access-rootfs-over-nfs)
  - [1. Install NFS server](#1-install-nfs-server)
  - [2.Check if the service is up and running](#2check-if-the-service-is-up-and-running)
  - [3.Check NFS kernel module](#3check-nfs-kernel-module)
  - [4. Create a directory to share](#4-create-a-directory-to-share)
    - [4.1. create new img for nfs+initramfs](#41-create-new-img-for-nfsinitramfs)
    - [4.2. Mount the image to be copied](#42-mount-the-image-to-be-copied)
    - [4.3. Copy rootfs to the shared directory](#43-copy-rootfs-to-the-shared-directory)
  - [5. IP configuration](#5-ip-configuration)
    - [5.1. Host machine](#51-host-machine)
      - [5.1.1. Check the IP address of the host machine and choose a target IP](#511-check-the-ip-address-of-the-host-machine-and-choose-a-target-ip)
      - [5.1.2. Rsatart the NFS server](#512-rsatart-the-nfs-server)
    - [5.2. Target machine(QEMU)](#52-target-machineqemu)
      - [5.2.1. Create a script to configure the network](#521-create-a-script-to-configure-the-network)
      - [5.2.2. Run QEMU with NFS](#522-run-qemu-with-nfs)
      - [5.2.3. Set the IP address on the target machine](#523-set-the-ip-address-on-the-target-machine)
  - [Final Output](#final-output)

## what is NFS?

NFS (Network File System) is a protocol that allows users to access files over a network as if they were on their local storage, enabling multiple clients to share data stored on a central server.

## why to access rootfs over NFS?

- It is faster and more secure than TFTP.
- Remote debugging.
- cross compilation over more powerful machine.
- easy to update and maintain the rootfs.

## 1. Install NFS server

```bash
sudo apt install nfs-kernel-server
```

## 2.Check if the service is up and running

```bash
systemctl status nfs-kernel-server
```

## 3.Check NFS kernel module

```bash
lsmod | grep nfs
```

## 4. Create a directory to share

put it under `/srv/` directory then copy all inside rootfs to it [or create a new one with initramfs](../3/2-initRAMfs.md).

### 4.1. create new img for nfs+initramfs

```bash
cd ~/SDs
dd if=/dev/zero   of=nfs+initramfs.img  bs=1M count=1024
sudo cfdisk nfs+initramfs.img 
sudo losetup -f --show --partscan nfs+initramfs.img 
sudo mkfs.vfat -F 16 -n boot /dev/loop0p1
sudo mkfs.ext4 -L rootfs /dev/loop0p2
cd ~/SDmount
sudo mkdir -p  nfs+initramfs/boot nfs+initramfs/rootfs
sudo mount /dev/loop0p1 ~/SDmount/nfs+initramfs/boot/
sudo mount /dev/loop0p2 ~/SDmount/nfs+initramfs/rootfs/
```

### 4.2. Mount the image to be copied

```bash
sudo losetup -f --show --partscan ~/SDs/sdInitRanFs.img 
sudo mount /dev/loop1p1 ~/SDmount/initRamFs/boot/
sudo mount /dev/loop1p2 ~/SDmount/initRamFs/rootfs/
lsblk 
sudo cp -r ~/SDmount/initRamFs/*  ~/SDmount/nfs+initramfs/
```

### 4.3. Copy rootfs to the shared directory

```bash
sudo cp -r ~/SDmount/nfs+initramfs/rootfs/* /srv/nfs/
```

## 5. IP configuration

### 5.1. Host machine

#### 5.1.1. Check the IP address of the host machine and choose a target IP

```bash
ifconfig
```

```bash
wlp0s20f3: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 192.168.1.9  netmask 255.255.255.0  broadcast 192.168.1.255
        inet6 fe80::193f:52b9:f36c:692a  prefixlen 64  scopeid 0x20<link>
        ether ac:82:47:21:99:59  txqueuelen 1000  (Ethernet)
        RX packets 84521  bytes 81891273 (81.8 MB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 54289  bytes 31334894 (31.3 MB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0
```

- `192.168.1.9` is the IP address of the host machine.
- choose target IP that is not used in the network. ping it to make sure it is unrachable.

```bash
ping 192.168.1.20
```

#### 5.1.2. Rsatart the NFS server

```bash
sudo systemctl restart nfs-kernel-server
sudo systemctl status nfs-kernel-server
```

### 5.2. Target machine(QEMU)

#### 5.2.1. Create a script to configure the network

touch `~/scripts/qemu-ifup` and make sure it is executable`chmod +x qemu-ifup`.

```bash
#!/bin/sh
ip a add 192.168.1.9/24 dev $1
ip link set $1 up
```

#### 5.2.2. Run QEMU with NFS

```bash
sudo qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -net tap,script=/home/hala/scripts/qemu-ifup -net nic -sd ~/SDs/nfs+initramfs.img
```

```diff
- t script=~/scripts/qemu-ifup
+ t script=/home/hala/scripts/qemu-ifup
```

#### 5.2.3. Set the IP address on the target machine

```uboot
setenv serverip 192.168.1.9
setenv ipaddr 192.168.1.20
setenv bootargs 'console=ttyAMA0,115200 root=/dev/nfs rw nfsroot=192.168.1.9:/srv/nfs ip=192.168.1.20'
saveenv
reset
```

## Final Output

from QEMU output log

```uboot
IP-Config: Guessing netmask 255.255.255.0
IP-Config: Complete:
     device=eth0, hwaddr=52:54:00:12:34:56, ipaddr=192.168.1.20, mask=255.255.255.0, gw=255.255.255.255
     host=192.168.1.20, domain=, nis-domain=(none)
     bootserver=255.255.255.255, rootserver=192.168.1.9, rootpath=
clk: Disabling unused clocks
ALSA device list:
  #0: ARM AC'97 Interface PL041 rev0 at 0x10004000, irq 37
VFS: Mounted root (nfs filesystem) on device 0:14.
```
