# Adding New Package To Buildroot

## Why would you want to add a new package to Buildroot?

new packages (userspace libraries or applications) can be integrated into Buildroot. It also shows how existing packages are integrated, which is needed for fixing issues or tuning their configuration.

## How to add a new package to Buildroot?

### Step 1: Create package directory

Create a directory in the `package` directory of Buildroot. The name of the directory should be the name of the package you are adding.

```bash
mkdir ~/buildroot/package/customPackage
```

### Step 2: Create package configuration files

#### 2.1 Config.in file

- Create a `Config.in` file in the package directory. This file will contain the configuration options for the package.

```bash
touch ~/buildroot/package/customPackage/Config.in
```

- This file will contain the option descriptions related to our libfoo software that will be used and displayed in the configuration tool.

```txt
config BR2_PACKAGE_CUSTOMPACKAGE
    bool "customPackage"
    help
      customPackage is a custom package that is used to demonstrate how to add a new package to Buildroot.
```

- Append the following line to the `Config.in` file in `buirdroot` directory.

```cmd
echo 'source "package/customPackage/Config.in"' >> ~/buildroot/package/Config.in
```

#### 2.2 Config.in.host file

- Create a Config.in.host file in the package/customPackage directory with the necessary configuration options.

```bash
touch ~/buildroot/package/customPackage/Config.in.host
```

and add the following content to the file.

```txt
config BR2_PACKAGE_HOST_CUSTOMPACKAGE
    bool "host customPackage"
    help
      This is a comment that explains what customPackage for the host is.
```

- Append the following line to the `Config.in.host` file in `buirdroot` directory.

```cmd
echo 'source "package/customPackage/Config.in.host"' >> ~/buildroot/package/Config.in.host
```

### Step 3: Create package.mk file

- Create a `package.mk` file in the package directory. This file will contain the build instructions for the package.

```bash
touch ~/buildroot/package/customPackage/customPackage.mk
```

- Add the following content to the `customPackage.mk` file.

```txt
# Define the package
CUSTOMPACKAGE_VERSION = 1.0
CUSTOMPACKAGE_SITE = $(TOPDIR)/package/customPackage/src
CUSTOMPACKAGE_SOURCE = customPackage-$(CUSTOMPACKAGE_VERSION).tar.gz
CUSTOMPACKAGE_SITE_METHOD = local

# Specify that this is a C++ application
CUSTOMPACKAGE_DEPENDENCIES = 
CUSTOMPACKAGE_LICENSE = Custom
CUSTOMPACKAGE_LICENSE_FILES = LICENSE

# Define the target binary and how to compile it
define CUSTOMPACKAGE_BUILD_CMDS
	$(MAKE) CC="$(TARGET_CC)" CXX="$(TARGET_CXX)" LD="$(TARGET_LD)" -C $(@D)
endef

# Define how to install the package
define CUSTOMPACKAGE_INSTALL_TARGET_CMDS
	$(INSTALL) -D -m 0755 $(@D)/hello $(TARGET_DIR)/usr/bin/
endef

# Register the package
$(eval $(generic-package))
```

### Step 4: Create source file

- Create a simple C++ source under `~/buildroot/package/src`

```cmd
mkdir ~/buildroot/package/customPackage/src && touch ~/buildroot/package/customPackage/src/hello.cpp
```

- Add the following content to the `hello.cpp` file.

```cpp
#include <iostream>

int main() {
    std::cout << "Hello, Buildroot!" << std::endl;
    return 0;
}
```

- create makefile for the source file

```cmd
touch ~/buildroot/package/customPackage/src/Makefile
```

Add the following content to the `Makefile` file.

```txt
.PHONY: clean
.PHONY: hello

hello: hello.cpp
	$(CXX) -o '$@' '$<'
```

- Archive and compress the source with Makefile into a tarball with the following name: `customPackage-$(CUSTOMPACKAGE_VERSION).tar.gz`

### Step 5: Rebuild Buildroot

```cmd
make list-defconfigs | grep <target>
make <target>_defconfig
make menuconfig
```

```menuconfig
/home/hala/buildroot/.config - Buildroot -ge321e81c-dirty Configuration
 ────────────────────────────────────────────────────────────────────────────────────
  ┌────────────────── Buildroot -ge321e81c-dirty Configuration ───────────────────┐
  │  Arrow keys navigate the menu.  <Enter> selects submenus ---> (or empty       │  
  │  submenus ----).  Highlighted letters are hotkeys.  Pressing <Y> selects a    │  
  │  feature, while <N> excludes a feature.  Press <Esc><Esc> to exit, <?> for    │  
  │  Help, </> for Search.  Legend: [*] feature is selected  [ ] feature is       │  
  │ ┌───────────────────────────────────────────────────────────────────────────┐ │  
  │ │        Target options  --->                                               │ │  
  │ │        Toolchain  --->                                                    │ │  
  │ │        Build options  --->                                                │ │  
  │ │        System configuration  --->                                         │ │  
  │ │        Kernel  --->                                                       │ │  
  │ │        Target packages  --->                                              │ │  
  │ │    [*] customPackage                                                      │ │  
  │ │        Filesystem images  --->                                            │ │  
  │ │        Bootloaders  --->                                                  │ │  
  │ │        Host utilities  --->                                               │ │  
  │ │    [ ] host customPackage                                                 │ │  
  │ │        Legacy config options  --->                                        │ │  
  │ │                                                                           │ │  
  │ │                                                                           │ │  
  │ └───────────────────────────────────────────────────────────────────────────┘ │  
  ├───────────────────────────────────────────────────────────────────────────────┤  
  │           <Select>    < Exit >    < Help >    < Save >    < Load >            │  
  └───────────────────────────────────────────────────────────────────────────────┘  

```

- Select the package you added in the configuration tool then build.

```cmd
make
```

### Step 6: Run QEMU

```cmd
~/buildroot/output/images/start-qemu.sh
```

 then run the application.

```cmd
# hello
Hello, Buildroot!
# ls /usr/bin/ | grep hello
hello
```
