# Create Distribution

## Layer

1. Distribution
2. machine
3. Recipes

## Why Distro? And What is the Difference Between Distro and Image?

- **Layers and Images**:
  - In a layer, we can have multiple images.
  - We might have different configuration files for the same image.

- **Base Image and Derived Images**:
  - We can start with a basic image.
  - From this base image, we can create multiple derived images tailored for different purposes, such as development, production, and continuous integration.

- **Distro vs. Image**:
  - A **Distro** defines the overall configuration and policies for a complete system, including package selection, system initialization, and other system-wide settings.
  - An **Image** is a specific build of the system that includes a set of packages and configurations defined by the distro and layers.
  - Essentially, the distro sets the rules and policies, while the image is the actual output that can be deployed on a device.

## Steps for creating a Distro

1. Under `meta-custom/conf/distro`, create a new file `custom-distro.conf`.

    ```cmd
    cd poky
    mkdir -p meta-custom/conf/distro && code meta-custom/conf/distro/custom-distro.conf
    ```

2. Add the following to the file:

    ```conf
    require conf/distro/poky.conf

    DISTRO = "custom-distro"
    DISTRO_NAME = "Custom Distro Version 1.0"
    DISTRO_VERSION = "1.0"
    DISTRO_FEATURES:remove = "ext2 3g nfc"
    ```

    - you can remove or add features to the distro.

        ```console
        $ bitbake -e | grep ^DISTRO_FEATURES=
        DISTRO_FEATURES="acl alsa argp bluetooth debuginfod ext2 ipv4 ipv6 largefile pcmcia usbgadget usbhost wifi xattr nfs zeroconf pci 3g nfc x11 vfat seccomp largefile opengl ptest multiarch wayland vulkan pulseaudio sysvinit gobject-introspection-data ldconfig"
        ```

3. before building the image, you need to set the distro in the local.conf file.

    ```conf
    DISTRO ?= "custom-distro"
    ```

    > Alias to list all distros:
    >
    >```bash
    > alias lsdistros='ls meta*/conf/distro/*.conf'
    > ```

4. Build the image and run qemu

    ```bash
    bitbake custom-image
    ```

    ```Output
    custom-hostname login: root
    root@custom-hostname:~# 
    ```

## Systemd as an Init System in Distro.conf

INIT_MANAGER = "systemd"
