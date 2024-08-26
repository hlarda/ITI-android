# Add Python script to your image

Make sure that all configuration set as in [Pi3B+](/YOCTO/YOYO/Pi3B+/1.md) until line 192(Flashing the Image).

To make it clear from the beginning, the following is `custom-layer` tree structure and later on, tis file would explain what each file is for:

```console
$ tree ~/windows-a/pi-sato/poky/meta-custom

$ tree
.
├── conf
│   ├── distro
│   │   └── custom-distro.conf
│   └── layer.conf
├── COPYING.MIT
├── README
├── recipes-core
│   ├── base-files
│   │   └── base-files_%.bbappend
│   └── images
│       └── custom-image.bb
└── recipes-custom
    └── pingLed
        ├── files
        │   └── bingLedIndicator.sh
        └── ping-led-indicator.bb
```

1. [ping-led-indicator.bb](/YOCTO/Tasks/meta-custom/recipes-custom/pingLed/ping-led-indicator.bb)

2. [bingLedIndicator.sh](/YOCTO/Tasks/meta-custom/recipes-custom/pingLed/files/bingLedIndicator.sh)

3. [custom-image.bb](/YOCTO/Tasks/meta-custom/recipes-core/images/  custom-image.bb). `ping-led-indicator` is added to `IMAGE_INSTALL`.

    Build the recipes only won't be enough to add the script to the image. Build the image as well:

    ```bash
    bitbake custom-image
    ```

    After the build is done, The script should be added to the image. To check that, run the following command:

    ```bash
    $ ls ~/windows-a/pi-sato/tmp-poky/tmp/work/raspberrypi3_64-poky-linux/custom-image/1.0-r0/rootfs/usr/bin | grep bingLedIndicator
    bingLedIndicator
    ```

4. When connected the ethernet cable to the Raspberry Pi, the script should be executed but with output "Host unreachable, red LED activated.". when `ifconfig` is executed, the eth0 is found without ip.

On your machine:

```cmd
sudo ip addr add 10.10.10.10/24 dev enp2s0
```

On RPI

```cmd
sudo ip addr add 10.10.10.11/24 dev eth0
```

5. Change the ip address in the script to the new ip address to `10.10.10.10`.

> If the ip address doesnt changed, replug the ethernet cable.

Finally!

```output
Host reachable, green LED activated.
```
