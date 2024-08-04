# Init Process

## Table of Contents

- [Init Process](#init-process)
  - [Table of Contents](#table-of-contents)
  - [Types of Init Systems](#types-of-init-systems)
    - [1. UBOOt](#1-uboot)
    - [2. SystemV](#2-systemv)
      - [What is runlevel?](#what-is-runlevel)
      - [Run levels in SystemV](#run-levels-in-systemv)
      - [Why using systemv in embedded systems?](#why-using-systemv-in-embedded-systems)
      - [How does it work?](#how-does-it-work)
      - [How to use SystemV?](#how-to-use-systemv)
      - [What happens when switching run levels?](#what-happens-when-switching-run-levels)

## Types of Init Systems

### 1. UBOOt

- U-boot is an init process that is started by the Raspberry Pi's GPU.
- It is responsible for loading the Linux kernel and device tree, setting up the hardware, and starting the Linux kernel.
- U-boot is a popular bootloader for embedded systems and is used in many different devices.

### 2. SystemV

- SystemV is a traditional init system that is used in many Linux distributions.
- It uses a series of scripts to start and stop services and manage the system's runlevels.
- SystemV is being replaced by newer init systems like systemd, but it is still used in some distributions.
- SystemV uses runlevels to define the state of the system and which services should be started or stopped.

#### What is runlevel?

- they are a way of categorizing the state of the system.
- Each run level has a different set of services that are started or stopped.
- For example, run level 0 is used to shut down the system, while run level 3 is used for normal operation.
- Using run levels lets you optimize CPU and memory usage by only starting the services that are needed for a particular run level.
- To check which runlevel your system is currently in, you can use the `runlevel` command.

    ```bash
    $ runlevel 
    N 5
    ```

- To change the runlevel, you can use the `telinit` command.

    ```bash
    sudo init 3
    ```

- **Each run level services** can be configured in the `/etc/rc?.d` directory. The scripts in this directory are executed when the system enters a particular run level.

    ```bash
    $ ls /etc/rc
    rc0.d/ rc1.d/ rc2.d/ rc3.d/ rc4.d/ rc5.d/ rc6.d/ rcS.d/ 
    ```

#### Run levels in SystemV

1. **Run level 0**: Halt the system. It is used to shut down the system.
2. **Run level 1**: Single-user mode. It is used for system maintenance.
3. **Run level 2**: Multi-user mode without networking.
4. **Run level 3**: Multi-user mode with networking but without graphical user interface.
5. **Run level 5**; Multi-user mode with networking and graphical user interface.
6. **Run level 6**: Reboot the system.

#### Why using systemv in embedded systems?

- You can make yout board support more than one mode of operation, one mode for development, a mode for production, and a mode for testing.
- Each mode can have a different set of services that are started or stopped.

#### How does it work?

1. `/etc/init.d`: contains scripts that handle the logic of starting and stopping services.
2. `/etc/rc?.d`: replace `?` with the run level number. This directory contains symbolic links to the scripts in `/etc/init.d`.
3. theses links have names start with a letter `S` or `K` followed by a number. The `S` links are used to start services, while the `K` links are used to stop services and numbers are used to determine the order in which the services are started or stopped.

- `start-stop-daemon` is used to start and stop the service, app or daemon.

  ```bash
  start-stop-daemon --start -n hala -a /usr/bin/code
  ```

#### How to use SystemV?

1. clone its repository then compile it.
2. it uses inittab file to start the init process.
3. inittab syntax is as follows:

```bash
id:runlevels:action:process
:s:sysinit:/etc/init.d/rcS # start the system with specified run levels
:1:wait:/etc/init.d/rcS
:2:wait:/etc/init.d/rc2
```

>when running `init <level>`, it will start the process in the specified run level or sometimes inittab has `:12345:rcS` which means start the process in run levels 1, 2, 3, 4, and 5.

#### What happens when switching run levels?

- When you switch run levels, the init process will execute the scripts in the `/etc/rc?.d` directory for the new run level.
- The scripts in this directory are executed in order, with the `S` scripts being executed to start services and the `K` scripts being executed to stop services.
- The scripts are executed in numerical order, with lower numbers being executed first.
