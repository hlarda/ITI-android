# Mount the rootfs throgh tftp

## Step 1: Create image with full functional rootfs

### 1.1. new img for TFTPboot+NFSrootfs

```bash
cd ~/SDs
dd if=/dev/zero   of=tftp+initramfs.img  bs=1M count=1024
sudo cfdisk tftp+initramfs.img 
sudo losetup -f --show --partscan tftp+initramfs.img 
sudo mkfs.vfat -F 16 -n boot /dev/loop0p1
sudo mkfs.ext4 -L rootfs /dev/loop0p2
cd ~/SDmount
sudo mkdir -p  tftp+initramfs/boot tftp+initramfs/rootfs
sudo mount /dev/loop0p1 ~/SDmount/tftp+initramfs/boot/
sudo mount /dev/loop0p2 ~/SDmount/tftp+initramfs/rootfs/
```

### 1.2. Mount the image to be copied

```bash
sudo losetup -f --show --partscan ~/SDs/sdInitRanFs.img 
sudo mount /dev/loop1p1 ~/SDmount/initRamFs/boot/
sudo mount /dev/loop1p2 ~/SDmount/initRamFs/rootfs/
lsblk 
sudo cp -r ~/SDmount/initRamFs/*  ~/SDmount/tftp+initramfs/
```

### 1.3. Copy rootfs to the shared directory

```bash
sudo cp -r ~/SDmount/tftp+initramfs/rootfs/* /srv/tftp/
```

## Step 2: IP configuration

### 2.1. Host machine

#### 2.1.1. Check the IP address of the host machine and choose a target IP

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

#### 2.1.2. Rsatart the NFS server

```bash
sudo systemctl restart nfs-kernel-server
sudo systemctl status nfs-kernel-server
```

### 2.2. Target machine(QEMU)

#### 2.2.1. Create a script to configure the network

touch `~/scripts/qemu-ifup` and make sure it is executable`chmod +x qemu-ifup`.

```bash
#!/bin/sh
ip a add 192.168.1.9/24 dev $1
ip link set $1 up
```

#### 2.2.2. Run QEMU with NFS

```bash
sudo qemu-system-arm -M vexpress-a9 -m 128M -nographic -kernel ~/u-boot/u-boot -net tap,script=/home/hala/scripts/qemu-ifup -net nic -sd ~/SDs/nfs+initramfs.img
```

#### 3.2.3. Set the IP address on the target machine

```uboot
setenv serverip 192.168.1.9
setenv ipaddr 192.168.1.20
setenv bootargs 'console=ttyAMA0,115200 root=/dev/nfs rw nfsroot=192.168.1.9:/srv/nfs ip=192.168.1.20'
saveenv
reset
```

## Output for Loading the rootfs through NFS

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

## Step 3: Make it load boot through TFTP

### 3.1. Copy the kernal, dtb and initramfs from boot to `/srv/tftp/`

```bash
sudo cp ~/SDmount/tftp+initramfs/boot/{zImage,*.dtb,uRamdisk} /srv/tftp/
```

### 3.2. Set QEMU to boot through TFTP

```uboot
setenv bootcmd 'echo Hello Sweetie!; tftp ${kernel_addr_r} zImage; tftp ${fdt_addr_r} vexpress-v2p-ca9.dtb; tftp 0x60600000 uRamdisk; bootz $kernel_addr_r 0x60600000 $fdt_addr_r'
saveenv
reset
```

## Proof that the job is done

### 1. Check the output of the QEMU

#### 1.1. TFTP

```uboot
TFTP from server 192.168.1.9; our IP address is 192.168.1.20
Filename 'zImage'.
Load address: 0x60100000
Loading: #################################################################
	 #################################################################
	 #################################################################
	 #################################################################
	 ##################
	 6.4 MiB/s
done
Bytes transferred = 4075328 (3e2f40 hex)
smc911x: MAC 52:54:00:12:34:56
smc911x: detected LAN9118 controller
smc911x: phy initialized
smc911x: MAC 52:54:00:12:34:56
Using ethernet@3,02000000 device
TFTP from server 192.168.1.9; our IP address is 192.168.1.20
Filename 'vexpress-v2p-ca9.dtb'.
Load address: 0x60000000
Loading: #
	 4.6 MiB/s
done
Bytes transferred = 14329 (37f9 hex)
smc911x: MAC 52:54:00:12:34:56
smc911x: detected LAN9118 controller
smc911x: phy initialized
smc911x: MAC 52:54:00:12:34:56
Using ethernet@3,02000000 device
TFTP from server 192.168.1.9; our IP address is 192.168.1.20
Filename 'uRamdisk'.
Load address: 0x60600000
Loading: #################################################
	 5.4 MiB/s
done
Bytes transferred = 719051 (af8cb hex)
smc911x: MAC 52:54:00:12:34:56
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

```

#### 1.2. NFS

```uboot
VFS: Mounted root (nfs filesystem) on device 0:14.
```

#### 1.3. initramfs

```uboot
Unpacking initramfs...
```

### 2. Manupulate files in SD card

#### 2.1. Delete boot directory from the image

It becomes unnecessary to have the boot directory in the image as it boots correctly through TFTP.

#### 2.2. touch `rootfsLoadedOverNFS` in `/srv/nfs/`

```uboot
Please press Enter to activate this console. 
~ # ls
bin                  linuxrc              rootfsLoadedOverNFS
boot                 lost+found           sbin
dev                  mnt                  srv
etc                  proc                 sys
home                 root                 usr
```

#### 2.3. Delete all SD content

```bash
sudo rm -r ~/SDmount/tftp+initramfs/*
hala@lat:~/SDmount/tftp+initramfs
$ tree 
.
├── boot
└── rootfs

3 directories, 0 files
```

thanks God, it works fine.
