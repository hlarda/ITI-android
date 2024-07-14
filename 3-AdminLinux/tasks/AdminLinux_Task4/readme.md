# User And Group

1. section1
    1. create a user

        ```bash
            $ sudo adduser mnna
                Adding user `mnna' ...
                Adding new group `mnna' (1001) ...
                Adding new user `mnna' (1001) with group `mnna' ...
                Creating home directory `/home/mnna' ...
                Copying files from `/etc/skel' ...
                New password: 
                Retype new password: 
                passwd: password updated successfully
                Changing the user information for mnna
                Enter the new value, or press ENTER for the default
                    Full Name []: mnna ahmd 
                    Room Number []: 10
                    Work Phone []: 20
                    Home Phone []: 30
                    Other []: 40
                Is the information correct? [Y/n] y
            $cat /etc/passwd | grep mnna
                mnna:x:1001:1001:mnna ahmd,10,20,30,40:/home/mnna:/bin/bash
        ```

    2. create a group

        ```bash
            $ sudo addgroup girls
                [sudo] password for hala:          
                Adding group `girls' (GID 1002) ...
                Done.
            $ cat /etc/group | grep girls
                girls:x:1002:
        ```

    3. add user to group

        ```bash
        # before adding mnna to girls group
            $ cat /etc/passwd | grep mnna
                mnna:x:1001:1001:mnna ahmd,10,20,30,40:/home/mnna:/bin/bash
            $ groups mnna
                mnna : mnna
        # Adding user
        $ sudo usermod -aG girls mnna 
            Place your finger on the fingerprint reader
            Failed to match fingerprint
            [sudo] password for hala:
        $ groups mnna 
            mnna : mnna girls
        $ cat /etc/group | grep girls
        girls:x:1002:mnna
        ```

2. section2
   1. create group with id 3000

        ```bash
            $ sudo addgroup --gid 3000 gym
                Adding group `gym' (GID 3000) ...
                Done.
            $ cat /etc/group | grep gym
                gym:x:3000:
        ```

   2. Lock any user created account so he can't log in

        ```bash
            sudo usermod -L mnna
        ```

        NOW `!` is added to the password field in `/etc/shadow` file.

        ```bash
            $ sudo cat /etc/shadow | grep mnna
                mnna:!$y$j9T$t9TrWAXz6tgX7YpZYT7w..$4VljCqKzkx420W.dBRsVR.8/8CJKu6OdQLWsZ4467d.:19918:0:99999:7:::

        ```

        if you yried to switch to the user you will get auth failure.

        ```bash
            $ su mnna
                Password: 
                su: Authentication failure
        ```

        to unlock the account.

        ```bash
            hala@lat:~
            $ sudo usermod -U mnna
            hala@lat:~
            $ su mnna
            Password: 
            mnna@lat:~
        ```

   3. Delete user account

        ```bash
            sudo userdel mnna
        ```

   4. Delete group account

        ```bash
        $ sudo delgroup gym
            Removing group `gym' ...
            Done.
        ```

   5. State the difference between adduser and useradd with example shown.

        Useradd is built-in Linux command that can be found on any Linux system. However, creating new users with this low-level is a tedious task because it doesn't create the home directory and user password by default.

        Adduser is not a standard Linux command. It’s essentially a Perl script that uses the useradd command in the background. This high-level utility is more efficient in properly creating new users on Linux. It gives you the option to create the home directory, and set password along with a few more parameters.

        ```bash
        $ sudo useradd mnna
        $ cat /etc/passwd | grep mnna
            mnna:x:30033:30033::/home/mnna:/bin/sh
        ```

        ```bash
            $ sudo adduser mnna
                Adding user `mnna' ...
                Adding new group `mnna' (1001) ...
                Adding new user `mnna' (1001) with group `mnna' ...
                The home directory `/home/mnna' already exists.  Not copying from `/etc/skel'.
                New password: 
                Retype new password: 
                passwd: password updated successfully
                Changing the user information for mnna
                Enter the new value, or press ENTER for the default
                    Full Name []: mnna
                    Room Number []: ahmd
                    Work Phone []: 464
                    Home Phone []: 454
                    Other []: 956
                Is the information correct? [Y/n] y
            $ cat /etc/passwd | grep mnna
                mnna:x:1001:1001:mnna,ahmd,464,454,956:/home/mnna:/bin/bash
        ```
