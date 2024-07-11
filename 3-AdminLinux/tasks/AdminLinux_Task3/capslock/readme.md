# Caps Lock LED Toggle Program

## Overview

This program consists of two main C files (`read.c` and `toggle.c`) and a shell script (`run.sh`). The purpose of the program is to read the state of the Caps Lock LED, store it in a file, and toggle the LED state.

## Files

### 1. `read.c`

The `read.c` file reads the state of the Caps Lock LED and writes this state to a file called `fdOutput`.

- **Opening Files:** Opens the Caps Lock LED brightness file (/sys/class/leds/input4::capslock/brightness) in read-only mode and a file fdOutput in read-write mode, creating it if it doesn't exist.
- **Reading State:** Reads the current state of the Caps Lock LED.
- **Writing State:** Writes this state to the fdOutput file.
- **Closing Files:** Closes both file descriptors.

### 2. `toggle.c`

The `toggle.c` file reads the state from `fdOutput`, toggles it, and writes the new state back to the Caps Lock LED brightness file.

- **Opening Files:** Opens fdOutput in read-only mode and the Caps Lock LED brightness file in read-write mode.
- **Reading State:** Reads the current state from fdOutput.
- **Toggling State:** Toggles the state ('0' to '1' or '1' to '0').
- **Writing State:** Writes the new state back to the Caps Lock LED brightness file.
- **Closing Files:** Closes both file descriptors.

### 3. `run.sh`'

The `run.sh` shell script compiles the C files and runs the `read` and `toggle` programs.

### Usage

```console
./run.sh
```
