# POKY

## Recap

- Poky is a reference distribution of the Yocto Project, which provides a collection of tools and metadata for creating custom Linux-based systems.
  - It includes the OpenEmbedded build system, BitBake, and a set of metadata.
  - Poky serves as both a starting point for custom distributions and a testbed for new features.
- Yocto offers documentation for OpenEmbedded-Core, BitBake, and Poky.
- Poky ensures compatibility with the latest versions of the Linux kernel and other software components.
- OpenEmbedded is a framework that includes BitBake and metalayer metadata (.bb, .conf, .inc, .class).
  - BitBake is a task scheduler and execution engine that parses metadata and executes tasks to build software.
  - OpenEmbedded-Core (OE-Core) is the core set of metadata for building embedded Linux distributions.
  - OpenEmbedded layers are collections of metadata that extend the build system's functionality.
        - Layers can be obtained from the official website or created by users.
        - Layers can include recipes, configuration files, and classes that define how software is built and integrated.
        - SIMa.ai and meta-ivi are example of a layer.
        - `Layer/conf/layer.conf` has `LAYERSERIES_COMPAT_ivi` variable to specify the compatibility of the layer with the Yocto Project.
- [Releases](https://wiki.yoctoproject.org/wiki/Releases)

## Variables in Yocto

### 1. Create a Variable

1. Local variables are defined in `.bb` , `.bbappend` and `.class`.
2. Global variables are defined in `.conf`.

```YOCTO
var = "value"
```

### 2. Read global variable

write in terminal:

```YOCTO
bitbake-getvar var
```

### 3. Variable assignment

1. normal assignment

    ```YOCTO
    var = "value"
    ```

    ```YOCTO
    x = "5"        
    x = "10"    -->>
    ```

2. weak assignment: looks for first assignment

    ```YOCTO
    var ?= "value"
    ```

    ```YOCTO
    x = "5"         --> x = "5" is assigned
    x ?= "10"
    ```

    ```YOCTO
    x ?= 3      -->>
    x ?= 4
    ```

3. weak weak assignation: waits for last assignment(default value)

    ```YOCTO
    var ??= "10"
    var ??= "5"     -->> 
    ```

4. append assignment: add value to the end of the variable

    `.` and `+=` have the same priority but `:append` has lower priority than `+=`.

    ```YOCTO
    var += "value" --> appends space between the values
    var .= "value" --> appends value without space
    var:append = "value" --> appends value without space
    ```

    ```YOCTO
    x = "5"
    x += "10"   --> x = "5 10"
    x:append = "15"  --> x = "5 1015"

    y = "3"
    y:append = "7"
    y += "9"    --> y = "3 97" as append has lower priority than +=.

    z ??= "1"
    z += "2"    --> z = "2"

    K ?= "3"
    K += "7"    --> K = "3 7"

    J ?= "1"
    J += "2"  
    J = "3"    --> J = "3"
    ```

5. prepend assignment: add value to the beginning of the variable.

    ```YOCTO
    var =+ "value"
    var =. "value"
    var:prepend = "value"
    ```

6. Immediate assignment: assign the value immediately.

    ```YOCTO
    var := "value"
    ```

    ```YOCTO
    x = "5"
    y = "${x}"      --> y = "11"
    x = "10"
    x = "11"
    ```

    ```YOCTO
    y := "${x}"      --> y = "10" although x is not assigned yet, it waits for first assignment.
    x = "10"
    ```

    ```YOCTO
    x = "5"
    y := "${x}"      --> y = "5 "
    x = "10"
    ```

### local variable

in poky `S`, `B`, `D` are local variables holds directories.

## Layer creation

1. `$PATH_TO_POKY/bitbake/bin/bitbake-layers`: there are many subcommands added after this to manage layers one of them is `create-layer`.

    ```YOCTO
    bitbake-layers create-layer $PATH_TO_LAYER/meta-<layer-name>
    ```

    ```console
    hala@lat:~/windows-a/layers-poky
    $ bitbake-layers create-layer  meta-hello
    NOTE: Starting bitbake server...
    Add your new layer with 'bitbake-layers add-layer meta-hello'
    $ tree meta-hello/
    meta-hello/
    ├── conf
    │   └── layer.conf
    ├── COPYING.MIT
    ├── README
    └── recipes-example
        └── example
            └── example_0.1.bb

    4 directories, 4 files
    ```

1. `conf`

    ```console
    $ cat meta-hello/conf/layer.conf
    # We have a conf and classes directory, add to BBPATH
    BBPATH .= ":${LAYERDIR}"
    
    # We have recipes-* directories, add to BBFILES
    BBFILES += "${LAYERDIR}/recipes-*/*/*.bb \
                ${LAYERDIR}/recipes-*/*/*.bbappend"
    
    BBFILE_COLLECTIONS += "meta-hello"
    BBFILE_PATTERN_meta-hello = "^${LAYERDIR}/"
    BBFILE_PRIORITY_meta-hello = "6"
    
    LAYERDEPENDS_meta-hello = "core"
    LAYERSERIES_COMPAT_meta-hello = "kirkstone"
    ```

    - `LAYERSERIES_COMPAT_meta-hello`: specifies the compatibility of the layer with the Yocto Project.
    - `LAYERDEPENDS_meta-hello`: specifies the layers that the new layer depends on.
    - **what happens if the layer which this layer depends on cahnges?** replace it with m7md
    - **Recipes'** location is specified in `BBFILES` variable.

        ```bitbake
        # We have recipes-* directories, add to BBFILES
        BBFILES += "${LAYERDIR}/recipes-*/*/*.bb \
        ${LAYERDIR}/recipes-*/*/*.bbappend"
        ```

    to add new recipes to the layer in differnt directories, append the directories to `BBFILES` variable.

    ```console
    hala@lat:~/windows-a/layers-poky/meta-hello
    $ mkdir hello-recipes
    hala@lat:~/windows-a/layers-poky/meta-hello
    $ touch hello-recipes/hello_0.1.bb
    ```

    ```bitbake
    BBFILES += "${LAYERDIR}/hello-*/*.bb
    ```

2. **Add Your new layer**

- `bblayers.conf` includes all layers in the whole project.

    ```console
    $ bitbake-layers show-layers
    NOTE: Starting bitbake server...
    layer                 path                                      priority
    ==========================================================================
    meta                  /home/hala/windows-a/poky/meta            5
    meta-poky             /home/hala/windows-a/poky/meta-poky       5
    meta-yocto-bsp        /home/hala/windows-a/poky/meta-yocto-bsp  5
    hala@lat:~/windows-a/poky/build
    $ bitbake-layers add-layer ../../layers-poky/meta-hello/
    NOTE: Starting bitbake server...
    $ bitbake-layers show-layers
    NOTE: Starting bitbake server...
    layer                 path                                      priority
    ==========================================================================
    meta                  /home/hala/windows-a/poky/meta            5
    meta-poky             /home/hala/windows-a/poky/meta-poky       5
    meta-yocto-bsp        /home/hala/windows-a/poky/meta-yocto-bsp  5
    meta-hello            /home/hala/windows-a/layers-poky/meta-hello  6
    ```

when adding layer, make sure you are in poky/buildEnvironment.

## Run Recipe

bitbake is used to run recipes.

```console
bitbake <recipe-name>
```

## oe-init-build-env

1. Creates build environment.
2. Adds the necessary environment variables to the shell.
3. Changes the current directory to the build directory.

```console
source oe-init-build-env
```
