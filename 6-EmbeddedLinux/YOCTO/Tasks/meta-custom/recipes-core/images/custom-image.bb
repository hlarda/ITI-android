inherit core-image
include recipes-sato/images/core-image-sato.bb

IMAGE_FEATURES += " ssh-server-dropbear "
IMAGE_INSTALL += " strace packagegroup-core-boot ping-led-indicator "
