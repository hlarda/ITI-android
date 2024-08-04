# Systemd

## Table of Contents

## What is systemd?

- systemd is a system and service manager for Linux operating systems.
- It starts services in parallel not sequentially as in SystemV so it is faster.

### Busybox VS Systemv Init VS Systemd Init

|Metric|Busybox Init|Systemv Init|Systemd Init|
|---|---|---|---|
|Complexity|Low|Medium|High|
|Boot-up speed|Fast|slow|Medium|
|Libc|Any|Any|glibc|
|Size|Small|Medium|Large|

### Main Directories

1. `/etc/systemd/system` contains the unit files that define the services, targets, and other units that systemd manages.
2. `/etc/systemd/system.conf`: contains the global configuration options for systemd.
3. `/lib/systemd/system`: contains the unit files that are provided by the installed packages and copy of the unit files from `/etc/systemd/system`.
4. `/run/systemd/system`: contains the runtime unit files that are created by systemd.

## Types of Units

- Systemd categories units according to the type of resource they describe.
- The easiest way to determine the type of a unit is with its type suffix, which is appended to the end of the resource name.

|Type|Suffix|Description|
|---|---|---|
|Service|`.service`|A service unit describes how to start or stop the service.|
|Socket|`.socket`|A socket unit describes a network socket.|
|Device|`.device`|A device unit describes a device in the system whose lifetime is not directly tied to a process but rather to the system itself.|
|Mount|`.mount`|A mount unit describes a file system mount point. Systemd can automatically create .mount units for entries specified in /etc/fstab, which is the traditional file used to define how disk partitions, filesystems, and other block devices should be mounted.|
|Automount|`.automount`|An automount unit describes a file system automount point.|

## Anatomy of a Unit File

### 1. [Unit] section

- It is the first section found in most of unit files.
- It contains the metadata about the unit.
- Although section order does not matter to systemd when parsing the file, this section is often placed at the top because it provides an overview of the unit.

#### Common directives

1. `Description`: A free-form string that describes the unit.
2. `Requires`: This directive lists units that the current unit strictly depends on. If the current unit is activated, the units listed in Requires must also be activated successfully. If any of these required units fail to start, the current unit will also fail. By default, these units are started in parallel with the current unit.

3. `Wants`: This directive is similar to Requires, but it is less strict. When the current unit is activated, systemd will attempt to start the units listed in Wants, but if these units are not found or fail to start, the current unit will still continue to function. This is the recommended way to configure most dependency relationships. Like Requires, units listed in Wants are also started in parallel with the current unit unless modified by other directives.

    > `Requires=` defines mandatory dependencies, while `Wants=` defines optional dependencies.

4. `Before`: start this unit before the listed units.
5. `After`: start this unit after the listed units.
6. `Conflicts`: This can be used to list units that cannot be run at the same time as the current unit. Starting a unit with this relationship will cause the other units to be stopped.

### 2. [Service] section

- This section is used to define how the service should be started and stopped.

1. `Type`: The type of the service. It can be
   1. `simple`: The process started with `ExecStart` is the main process of the service so the application will terminate when the service is stopped.
   2. `forking`: The process started with `ExecStart` is the parent of the main process of the service. When stopping the service, the application will not be terminated.
   3. `oneshot`: The process used for initialization runs one time.

    > **Difference the service and app behavior in simple service type and forking service type**:
    > lets say we have a tftp server as a service and an app. In simple service type, when stopping the service, the app is killed. In forking service type, when stopping the service, the app is not killed.
2. `ExecStart`: The command to start the service. Only one of `ExecStart` can be used.
3. `ExecStop`: the command needed to stop the service. If this is not given, the process will be killed immediately when the service is stopped.

    > When calling `systemctl start <unit>`, the `ExecStart` command will be executed. When calling `systemctl stop <unit>`, the `ExecStop` command will be executed.

4. `Restart`: the circumstances under which will attempt to automatically restart the service. This can be set to values like “always”, “on-success”, “on-failure”, “on-abnormal”, “on-abort”, or “on-watchdog”.

### 3. [Install] section

The `[Install]` section in a systemd unit file defines what happens when the unit is enabled or disabled. It is optional and used to configure the unit to start automatically at boot. This is done by linking the unit to other boot-time units. Only units that can be enabled will have this section. Common directives include:

- `WantedBy=`: Specifies targets that should start this unit.
- `RequiredBy=`: Specifies targets that require this unit.
- `Also=`: Additional units to enable/disable with this unit.
- `Alias=`: Additional names for the unit.

Let's Run the following command to see the default target:

```bash
$ systemctl get-default
graphical.target
```

Target is a group of services. Actually, it is a directory containing soft links for services in `/etc/systemd/system` wanted to be run in this target.

you write under the install section in the unit file `WantedBy=multi-user.target`, a soft link will be created in the target directory once it is enabled.

```bash
systemctl enable <unit>
```

## Processes

1. orphaned process

    - A process whose parent has terminated, but the process itself continues to run.
    - The init process is the ancestor of all processes on the system, so when a process is orphaned, it is reparented to the init process.

2. Zombie process
    - A process that has completed execution but still has an entry in the process table.
    - This occurs when the parent process does not read the exit status of the child process.
    - The problem is taking place in process table and the system has a limited number of processes to manage so in the long term, the system will run out of processes.

3. Daemon process
    - A process that runs in the background and has no controlling terminal so it cannot turned into a foreground process.
    - Daemons are typically started at boot time and run as root, waiting for requests to service.
    - Daemons are used to provide services that can be started and stopped independently of other processes.
    - `jobs` command will not show daemon processes as they are not background processes.
