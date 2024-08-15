# introduction

## Difference between Building from scratch and using Yocto

1. **building from scratch**
   1. is time consuming and needs a lot of effort and experience.
   2. (fetching, compiling, configuring, etc)
2. **Auto-Build Tools**(Yocto and Buildroot)
   1. build scripts automation tools.
   2. make it easier to build minimal images with less effort and almost error-free.

## Yocto Project and buildroot

||Yocto|Buildroot|
|---|---|---|
|**ease of development and learning curve**|High learning curve, takes larger time to learn and difficult to master for a biginner| Low learning curve, easy to learn and master for a beginner|
|**configuration**|Partial configuration uses existing layers and recipes with slight modifications, while full manual configuration requires extensive customization and creation of build components.|Partial configuration involves selecting from pre-configured options with minimal adjustments, whereas full manual configuration requires more extensive customization of build parameters and packages.|

## Yocto Project

### 1. Bitbake

executes tasks and recipes written in python and shell scripts.

Lets clone it and explore a little bit.

```cmd
git clone --depth=1  https://github.com/openembedded/bit
bake.git
```

1. **classes:** combination of bash and python scripts.
2. **conf:** configuration files has the configuration of the build in variables in the form of key-value pairs with specific syntax.
3. **doc:** documentation. in this directory, you can find makefile. Run `make help` to see the available options.

   ```cmd
   $ make help
   Makefile:13: *** "The 'sphinx-build' command was not found. Make sure you have Sphinx installed".  Stop.
   ```

   To resolve this error:

   ```cmd
   python3 -m venv myenv
   source myenv/bin/activate
   pip install sphinx_rtd_theme
   ```

   1. **html:** contains the html documentation
4. **lib:** python libraries.

#### Bitbake Hello + Environment setup

[Best place to start is the official documentation example](./bitbake/doc/bitbake-user-manual/bitbake-user-manual-hello.rst)

1. Create a project directory.

   ```cmd
   mkdir hello
   cd hello
   ```

2. Set `BBPATH`.

   `BBPATH` variable is what tells BitBake where to look for metadata files.

   ```cmd
   export BBPATH=$PWD
   ```

3. create `conf/bitbake.conf` file.

   ```cmd
   mkdir conf
   touch conf/bitbake.conf
   ```

4. Add the following content to `conf/bitbake.conf` file.

   ```cmd
   PN  = "${@bb.parse.vars_from_file(d.getVar('FILE', False),d)[0] or 'defaultpkgname'}"

   TMPDIR  = "${TOPDIR}/tmp"
   CACHE   = "${TMPDIR}/cache"
   STAMP   = "${TMPDIR}/${PN}/stamps"
   T       = "${TMPDIR}/${PN}/work"
   B       = "${TMPDIR}/${PN}"
   ```

5. Create `classes/base.bbclass` file.

   ```cmd
   mkdir classes
   touch classes/base.bbclass
   ```

6. Add the following content to `classes/base.bbclass` file.

   ```cmd
   addtask build
   ```

   do_build is the minimal task that needs to be defined in order to build a package.

   NOW, bitbake is ready with no errors.

7. Create a recipe

   1. Create a layer directory.

      ```cmd
      mkdir -p  ../helloLayer/conf/
      ```

   2. add the following content to `../helloLayer/conf/layer.conf` file.

      ```cmd
      BBPATH .= ":${LAYERDIR}"
      BBFILES += "${LAYERDIR}/*.bb"
      BBFILE_COLLECTIONS += "helloLayer"
      BBFILE_PATTERN_helloLayer:= "^${LAYERDIR_RE}/"
      LAYERSERIES_CORENAMES = "hello_world_example"
      LAYERSERIES_COMPAT_helloLayer = "hello_world_example"
      ```

   3. create a recipe file.

      ```cmd
      touch ../helloLayer/printHello.bb
      ```

      recipe file content:

      ```txt
      DESCRIPTION = "Prints Hello World"
      PN = 'printhello'
      PV = '1'

      python do_build() {
         bb.plain("********************");
         bb.plain("*                  *");
         bb.plain("*  Hello, World!   *");
         bb.plain("*                  *");
         bb.plain("********************");
      }
      ```

   4. Add the layer to the `BBPATH` in the `conf/bitbake.conf` file located in the `hello` directory.

      ```cmd
      BBLAYERS ?= " \
           /home/hala/windows-b/ITI-android/6-EmbeddedLinux/YOCTO/YOYO/1-Introduction/bitbake/helloLayer \
       "
      ```

8. Run the following command to build the recipe.

   ```cmd
   bitbake printhello
   ```

   The output will be:

   ```txt
   ********************
   *                  *
   *  Hello, World!   *
   *                  *
   ********************
   ```

Lets have a look at the `tmp` directory whixh is created by bitbake.

```cmd
$ ls tmp/
cache  printHello
```

1. **cache:**
   - bitbake caches build artifacts in this directory.
   - if you run the same command again, it will not build the recipe again, it will use the cached files.
   - delete the cache directory to force bitbake to build the recipe again.
2. **printHello:**

   ```console
   $ ls tmp/printHello/work/
   log.do_build  log.do_build.358404  log.task_order  run.do_build  run.do_build.358404
   ```

   it contains the logs of the build process and source code of the recipe. Each log.do_build file contains the log of the build process and run.do_build file contains the source code of the recipe.

   ```console
   $ cat tmp/printHello/work/log.do_build
   DEBUG: Executing python function do_build
   ********************
   *                  *
   *  Hello, World!   *
   *                  *
   ********************
   DEBUG: Python function do_build finished
   $ cat tmp/printHello/work/run.do_build
   def do_build(d):
      bb.plain("********************");
      bb.plain("*                  *");
      bb.plain("*  Hello, World!   *");
      bb.plain("*                  *");
      bb.plain("********************");

   do_build(d)
   ```

## Bitbake file sequence and execution

1. `bitbacke.lock` file is created in the `tmp` directory to prevent multiple instances of bitbake from running at the same time then delete it when the build process is finished.

2. `bblayers.conf` is parsed to get the layers and the recipes.

3. `layer/conf/layer.conf` is parsed to get the layer configuration and knew where are .bb files is located.

4. `conf/bitbake.conf` is parsed to get the configuration of the build.

5. `classes/base.bbclass` is parsed to get the tasks and the functions.

6. threads are created to execute the tasks in parallel.

### Bitbake tasks

1. **do_fetch:** fetches the source code of the recipe.
2. **do_unpack:** unpacks the source code.
3. **do_deploy**: deploys the source code to the destination directory.
4. **do_configure:** configures the source code.
5. **do_compile:** compiles the source code.
6. **do_install:** installs the compiled source code.
7. **do_build:** is the minimal task that needs to be defined in order to build a package.

### Recipe file

Search for the variables in the recipe in [reference manual](/home/hala/windows-a/poky/documentation/ref-manual/variables.rst)

- `IMAGE_INSTALL`
   Used by recipes to specify the packages to install into an image
   through the :ref:`image <ref-classes-image>` class.

- `RDEPENDS`
   Used by recipes to specify runtime dependencies.

### Bitbake commands

1. Variables

   ```cmd
   bitbake -e printhello | grep ^PN
   ```
