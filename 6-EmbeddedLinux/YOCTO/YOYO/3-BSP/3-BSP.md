# Build images for specific machines

## 1. Download the meta layer fro the BSP

from Openembedded layer index, download the BSP layer for the specific machine.

```bash
git clone https://github.com/agherzan/meta-raspberrypi.git -b kirkstone
```

### Dive into the layer

1. `meta-raspberrypi/conf/machine` contains the configuration files for the supported machines.
2. `meta-raspberrypi/conf/machine/include` contains the common configurations for the machines. `rpi-base.inc` include common configurations for all machines. one variable is `IMAGE_FSTYPES ?= "tar.bz2 ext3 wic.bz2 wic.bmap"` which defines file system types for the images. dont know why my colleague set it with same values in local.conf.
3. This layer specifies kernel, device tree, architecture, sdcard partitioning, and other configurations for the Raspberry Pi machines.

## 2. Add layer to the build

```bash
bitbake-layers add-layer ../meta-raspberrypi
```

## 4. Prioritize the layer

1. Manually edit the `layer.conf` file in the `meta-raspberrypi` directory and set the priority to 9.

## 5. Enable UART

Add the following to the `local.conf` file:

```conf
ENABLE_UART = "1"
```
