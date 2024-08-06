# The following line was added to resolve the error appeared in dmesg (charDevice: module verification failed: signature and/or required key missing - tainting kernel)
CONFIG_MODULE_SIG_ALL=n
obj-m += charDevice.o

all:
	make -C /lib/modules/$(shell uname -r)/build/ M=$(PWD) modules
clean:
	make -C /lib/modules/$(shell uname -r)/build/ M=$(PWD) clean
