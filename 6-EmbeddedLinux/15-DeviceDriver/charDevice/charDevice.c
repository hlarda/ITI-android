#include <linux/fs.h>
#include <linux/cdev.h>
#include <linux/device.h>
#include <linux/init.h>
#include <linux/module.h>
#include <linux/uaccess.h>

ssize_t charDeviceRead    (struct file *, char __user *, size_t, loff_t *);
ssize_t charDeviceWrite   (struct file *, const char __user *, size_t, loff_t *);
int     charDeviceOpen    (struct inode *, struct file *);
int     charDeviceRelease (struct inode *, struct file *);

int __init static charDeviceInit(void);
void __exit static charDeviceExit(void);

#define DRIVER_NAME "charDevice"
#define BUFFER_SIZE 1024

struct charDeviceData{
    struct cdev cdev;
    struct class *class;
    struct device *device;
    struct file_operations fops;
    dev_t devID;
    char buffer[BUFFER_SIZE];
};
struct charDeviceData charDeviceData;

ssize_t charDeviceRead (struct file *, char __user *, size_t, loff_t *){
    printk("charDeviceRead");
    return 0;
}
ssize_t charDeviceWrite (struct file *, const char __user *, size_t, loff_t *){
    printk("charDeviceWrite");
    return 3;
}
int charDeviceOpen (struct inode *, struct file *){
    printk("charDeviceOpen");
    return 0;
}
int charDeviceRelease (struct inode *, struct file *){
    printk("charDeviceRelease");
    return 0;
}

int __init static charDeviceInit(void){
    charDeviceData.fops.owner = THIS_MODULE;
    charDeviceData.fops.read = charDeviceRead;
    charDeviceData.fops.write = charDeviceWrite;
    charDeviceData.fops.open = charDeviceOpen;
    charDeviceData.fops.release = charDeviceRelease;

    //  1. dynamically allocate a major and a minor number for the device -- instead of choosing static one reserved for another.
    if(alloc_chrdev_region(&charDeviceData.devID , 0,1 ,DRIVER_NAME) < 0){
        printk("Device couldn't be allocated\n");
        return -1 ;
    }
    else{
        printk("Device is allocated with Major: %d, Minor: %d\n", MAJOR(charDeviceData.devID), MINOR(charDeviceData.devID));
    }
    /* 2. Intialize the cdev*/
    cdev_init(&charDeviceData.cdev, &charDeviceData.fops);
    if (cdev_add(&charDeviceData.cdev, charDeviceData.devID, 1)< 0) {
        unregister_chrdev_region(charDeviceData.devID, 1);
        printk(KERN_ALERT "cdev_add failed\n");
        return -1;
    }
    /* 3. Under /sys/class/ create a class */
    charDeviceData.class = class_create("charDeviceClass");
    if(IS_ERR(charDeviceData.class)){
        printk("Class couldn't be created\n");
        unregister_chrdev_region(charDeviceData.devID, 1);
        return -1;
    }
    /* 4. Create a device under /dev */
    charDeviceData.device = device_create(charDeviceData.class, NULL, charDeviceData.devID, NULL, DRIVER_NAME);
    if(IS_ERR(charDeviceData.device)){
        printk("Device couldn't be created\n");
        class_destroy(charDeviceData.class);
        unregister_chrdev_region(charDeviceData.devID, 1);
        return -1;
    }
    printk("Device is created\n");
    return 0;
}
void __exit static charDeviceExit(void){
    printk("\n");
    device_destroy(charDeviceData.class, charDeviceData.devID);
    class_destroy(charDeviceData.class);
    unregister_chrdev_region(charDeviceData.devID, 1);
    cdev_del(&charDeviceData.cdev);
    printk("Device is destroyed\n");
}
module_init(charDeviceInit);
module_exit(charDeviceExit);

MODULE_DESCRIPTION("A simple character device driver");
MODULE_LICENSE("GPL");
MODULE_AUTHOR("hlarda");
MODULE_VERSION("0.1");