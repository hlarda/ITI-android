# Board support package (BSP)

## The Rest of Previous Lecture

## Difference between IMAGE_FEATURES and EXTRA_IMAGE_FEATURES

They are both to enable high level features in the image. High level features like x11, are set of packages that are required to enable the feature. [meta/classes/core-image.bbclassb](../../../../../../windows-a/poky/meta/classes/core-image.bbclass) contains some of the features that can be enabled.

`IMAGE_FEATURES` is used within the image recipe but `EXTRA_IMAGE_FEATURES` is used in the local.conf file.

## Alias to list all images

```bash
alias lsimgs = 'ls meta*/recipes*/images/*.bb'
```

when creating your own image, stick to the naming convention to make it easier to find the image recipe.

## Creating a new image

### 1. Create img recipe

under `meta-custom` the layer we've created in the previous lecture.

```bash
cd poky/build
mkdir -p ../meta-custom/recipes-core/images && code ../meta-custom/recipes-core/images/custom-image.bb
```

### 2. Add the following to the recipe

1. inherit core-image class.
   1. inside core-image class, there are some default features that are enabled.
   2. it inherits image class which is the base class for all images.

2. include `recipes-core/images/core-image-minimal.bb`.

```recipe
inherit core-image
include recipes-core/images/core-image-minimal.bb

IMAGE_FEATURES += " ssh-server-dropbear "
IMAGE_INSTALL += " hello-c strace "
```

### 3. clean core-image-minimal

```bash
bitbake -c cleanall core-image-minimal
```

### 4. Build the image

```bash
bitbake custom-image
```

### 5. Check the image

```bash
$ ls tmp/deploy/images/qemuarm64/ | grep custom-image-
custom-image-qemuarm64-20240816105347.qemuboot.conf
custom-image-qemuarm64-20240816105347.rootfs.ext4
custom-image-qemuarm64-20240816105347.rootfs.manifest
custom-image-qemuarm64-20240816105347.rootfs.tar.bz2
custom-image-qemuarm64-20240816105347.testdata.json
custom-image-qemuarm64.ext4
custom-image-qemuarm64.manifest
custom-image-qemuarm64.qemuboot.conf
custom-image-qemuarm64.tar.bz2
custom-image-qemuarm64.testdata.json
```

`tmp/deploy/images` is the default directory where the images are stored for each machine. in this case, the machine is `qemuarm64`.

### 6. Run the image

```bash
runqemu qemuarm64 nographic
```

RUN hello and strace commands to check if they are installed.

### Additional settings

1. Change hostname

    1. `hostname` variable in the base-files recipe using either an append file or a configuration file as in `poky/meta-selftest/recipes-test/base-files/base-files_3.0.14.bbappend.base-files_%.bbappend`
    2. .bbappend file is used to add or modify some configurations related to the recipe without modifying the original recipe affecting other images.
    3. in meta-custom, create `recipes-core/base-files/base-files_%.bbappend`

        ```cmd
        cd poky/meta-custom
        mkdir -p recipes-core/base-files && code recipes-core/base-files/base-files_%.bbappend
        ```

    4. Add the following to the file

        ```recipe
        hostname = "custom-hostname"
        ```

    5. Show which files append to the base-files recipe

        ```bash
        $ bitbake-layers show-appends base-files
        Loading cache: 100% |#####################################| Time: 0:00:00
        Loaded 1681 entries from dependency cache.

        === Matched appended recipes ===
        base-files_3.0.14.bb:
          /home/hala/windows-a/poky/meta-custom/recipes-core/base-files/base-files_%.bbappend
        ```

    6. Build the image

        ```cmd
        bitbake custom-image
        ```

    7. Run the image

        ```console
        $ runqemu qemuarm64 nographic
        custom-hostname login: root
        root@custom-hostname:~# 
        ```
