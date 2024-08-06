# include <linux/init.h>
# include <linux/module.h>
# include <linux/fs.h>
# include <linux/cdev.h>
# include <linux/proc_fs.h>

int __init static mod_init(void);
void __exit static mod_exit(void);
ssize_t hello_read(struct file *file, char __user *buf, size_t count, loff_t *ppos);
ssize_t hello_write(struct file *file, const char __user *buf, size_t count, loff_t *ppos);

struct proc_dir_entry *proc_dir_entry_hello= NULL;

const struct proc_ops hello_proc_fops = {
    .proc_read = hello_read,
    .proc_write = hello_write,
};

ssize_t hello_read(struct file *file, char __user *buf, size_t count, loff_t *ppos)
{
    printk("Hello, read!\n");
    return 0;
}

ssize_t hello_write(struct file *file, const char __user *buf, size_t count, loff_t *ppos)
{
    printk("Hello, write!\n");
    return count;
}

int __init static mod_init(void)
{
    proc_dir_entry_hello = proc_create("hello", 0666, NULL, &hello_proc_fops);
    printk("Hello, World!\n");
    return 0;
}

void __exit static mod_exit(void)
{
    proc_remove(proc_dir_entry_hello);
    printk("Goodbye, World!\n");
}

module_init(mod_init);
module_exit(mod_exit);

MODULE_LICENSE("GPL");