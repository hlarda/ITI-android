# Yocto vs. Buildroot

## Overview

Building a Linux image from scratch can be complex and time-consuming. Tools like Yocto and Buildroot simplify this process by automating many aspects of image creation.

### Yocto Project

- **Yocto Project** provides a set of tools and instructions to build custom Linux distributions.
- It involves creating configuration scripts that handle dependencies and build the image.
- **Advantages**:
  - Rich feature set.
  - Easier integration with other tools and new hardware.
- **Disadvantages**:
  - Time-consuming setup.
  - Requires learning Yocto-specific syntax.
  - Larger footprint compared to scratch-built images.
  - Larger workspace environment.

### Buildroot

Buildroot offers a simpler approach for building Linux images.

- **Configuration Steps**:
  1. **Clone Buildroot Repository**:

     ```bash
     git clone https://github.com/buildroot/buildroot.git
     cd buildroot
     ```

  2. **List Supported Configurations**:

     ```bash
     make list-defconfigs
     ```

  3. **Select Board Configuration** (e.g., Raspberry Pi 3):

     ```bash
     make raspberrypi3_defconfig
     ```

  4. **Configure Buildroot**:

     ```bash
     make menuconfig
     ```

  5. **Build the Image**:

     ```bash
     make
     ```

### OpenEmbedded

OpenEmbedded is a build framework for embedded Linux that uses Bitbake as its build tool.

- **Key Concepts**:
  - **Layers**: Collections of recipes and metadata to build images.
  - **Bitbake**: The task executor that uses recipes and metadata to create the final image written in python.
  - **Why Python?**: Python is chosen for its readability, ease of use, and hardware independence.

#### Community Layers

1. **meta-core**: Provides application layers.
2. **meta-rpi**: Provides board support for Raspberry Pi.
3. **meta-skeleton**: Template for creating new board support packages.

#### Yocto Project Integration

Yocto Project organizes and manages layers used by OpenEmbedded:

- **Key Components**:
  - **meta-poky**: Reference application layer.
  - **meta-yocto-bsp**: Reference board support layer.
  - **meta-core**: Application layer.
  - **meta-skeleton**: Template for board support packages.

#### Layer Compatibility Issues

- Layers might be incompatible if not carefully managed.
- Yocto Project branches for different releases:
  - **Zeus**: Long-term support.
  - **Dunfell**
  - **Kirkstone**: LTS till 2026.
  - **Scarthgap**: LTS till 2028.
- Ensure that layers match the release version of Poky.

#### Bitbake Workflow

1. **Clone**: Retrieves apps as specified in meta-application.
2. **Unpack**: Extracts apps into the source directory (S).
3. **Patch**: Applies patches to apps in the source directory.
4. **Configure**: Configures apps in the source directory.
5. **Compile**: Compiles apps in the build directory (B).
6. **Install**: Installs apps into the destination directory (D).
7. **Package Feeder**: Generates SDK or image with debug symbols.
