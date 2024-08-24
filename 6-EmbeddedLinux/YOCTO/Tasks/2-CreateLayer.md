# Create Layer In Yocto

Previously, we created recipes manually in the `meta` layer. This is not a good practice. We should create a new layer for our recipes. from now on, we will create a new layer for our recipes using `bitbake-layers`.

## `bitbake-layers`

bitbake-layers is a utility that allows you to manipulate the layer configuration of a BitBake build.

    ```console
    $ bitbake-layers --help
    NOTE: Starting bitbake server...
    usage: bitbake-layers [-d] [-q] [-F] [--color COLOR] [-h] <subcommand> ...

    BitBake layers utility

    options:
      -d, --debug           Enable debug output
      -q, --quiet           Print only errors
      -F, --force           Force add without recipe parse verification
      --color COLOR         Colorize output (where COLOR is auto, always, never)
      -h, --help            show this help message and exit

    subcommands:
      <subcommand>
        add-layer           Add one or more layers to bblayers.conf.
        remove-layer        Remove one or more layers from bblayers.conf.
        flatten             flatten layer configuration into a separate output directory.
        show-layers         show current configured layers.
        show-overlayed      list overlayed recipes (where the same recipe exists in
                            another layer)
        show-recipes        list available recipes, showing the layer they are provided
                            by
        show-appends        list bbappend files and recipe files they apply to
        show-cross-depends  Show dependencies between recipes that cross layer
                            boundaries.
        layerindex-fetch    Fetches a layer from a layer index along with its dependent
                            layers, and adds them to conf/bblayers.conf.
        layerindex-show-depends
                            Find layer dependencies from layer index.
        create-layer        Create a basic layer

    Use bitbake-layers <subcommand> --help to get help on a specific command
    ```

    `--help` can be used with any subcommand to get help on a specific command. lets try `create-layer`.

    ```console
    $ bitbake-layers create-layer  --help
    NOTE: Starting bitbake server...
    usage: bitbake-layers create-layer [-h] [--layerid LAYERID] [--priority PRIORITY]
                                       [--example-recipe-name EXAMPLERECIPE]
                                       [--example-recipe-version VERSION]
                                       layerdir

    Create a basic layer

    positional arguments:
      layerdir              Layer directory to create

    options:
      -h, --help            show this help message and exit
      --layerid LAYERID, -i LAYERID
                            Layer id to use if different from layername
      --priority PRIORITY, -p PRIORITY
                            Priority of recipes in layer
      --example-recipe-name EXAMPLERECIPE, -e EXAMPLERECIPE
                            Filename of the example recipe
      --example-recipe-version VERSION, -v VERSION
                            Version number for the example recipe
    ```

## Create a new layer named `meta-custom`

```cmd
. oe-init-build-env build
bitbake-layers create-layer ../meta-custom
bitbake-layers show-layers
bitbake-layers add-layer ../meta-custom
bitbake-layers show-layers
```

Output:

```console
### Shell environment set up for builds. ###

You can now run 'bitbake <target>'

Common targets are:
    core-image-minimal
    core-image-full-cmdline
    core-image-sato
    core-image-weston
    meta-toolchain
    meta-ide-support

You can also run generated qemu images with a command like 'runqemu qemux86'

Other commonly useful commands are:
 - 'devtool' and 'recipetool' handle common recipe tasks
 - 'bitbake-layers' handles common layer tasks
 - 'oe-pkgdata-util' handles common target package tasks
NOTE: Starting bitbake server...
Specified layer directory exists
NOTE: Starting bitbake server...
layer                 path                                      priority
==========================================================================
meta                  /home/hala/windows-a/poky/meta            5
meta-poky             /home/hala/windows-a/poky/meta-poky       5
meta-yocto-bsp        /home/hala/windows-a/poky/meta-yocto-bsp  5
meta-raspberrypi      /home/hala/windows-a/poky/meta-raspberrypi  9
meta-custom           /home/hala/windows-a/poky/meta-custom     6
NOTE: Starting bitbake server...
NOTE: Starting bitbake server...
layer                 path                                      priority
==========================================================================
meta                  /home/hala/windows-a/poky/meta            5
meta-poky             /home/hala/windows-a/poky/meta-poky       5
meta-yocto-bsp        /home/hala/windows-a/poky/meta-yocto-bsp  5
meta-raspberrypi      /home/hala/windows-a/poky/meta-raspberrypi  9
meta-custom           /home/hala/windows-a/poky/meta-custom     6
```

## Run Example Recipe

```console
bitbake poky/meta-custom/recipes-example/example/example
```

Output:

```console
Loading cache: 100% |#######################################| Time: 0:00:00
Loaded 1681 entries from dependency cache.
Parsing recipes: 100% |#####################################| Time: 0:00:00
Parsing of 920 .bb files complete (919 cached, 1 parsed). 1681 targets, 79 skipped, 0 masked, 0 errors.
NOTE: Resolving any missing task queue dependencies

Build Configuration:
BB_VERSION           = "2.0.0"
BUILD_SYS            = "x86_64-linux"
NATIVELSBSTRING      = "universal"
TARGET_SYS           = "aarch64-poky-linux"
MACHINE              = "qemuarm64"
DISTRO               = "custom-distro"
DISTRO_VERSION       = "1.0"
TUNE_FEATURES        = "aarch64 armv8a crc cortexa57"
TARGET_FPU           = ""
meta                 
meta-poky            
meta-yocto-bsp       = "kirkstone:322d4df8cb51b531a998de92298914a6710d7677"
meta-raspberrypi     = "kirkstone:d7544f35756d87834e8b4bf3e3e733c771d380ae"
meta-custom          = "kirkstone:322d4df8cb51b531a998de92298914a6710d7677"

Initialising tasks: 100% |##################################| Time: 0:00:01
Sstate summary: Wanted 0 Local 0 Mirrors 0 Missed 0 Current 154 (0% match, 100% complete)
NOTE: Executing Tasks
***********************************************
*                                             *
*  Example recipe created by bitbake-layers   *
*                                             *
***********************************************
NOTE: Tasks Summary: Attempted 639 tasks of which 637 didn't need to be rerun and all succeeded.
```
