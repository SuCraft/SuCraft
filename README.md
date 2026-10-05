# SuCraft

This repository contains the full SuCraft source code.

It consists of multiple modules in their own directory, with different purposes:

- `bukkit-plugins`\
  The code for the Bukkit plugins used on the server.
- `minecraft-server-software`\
  The customized Minecraft server software.
- `scaffolding`\
  Scripts to set up, update and start the full server infrastructure from scratch,
  which includes all configuration files (such as for plugins).

## Setting up

It is strongly recommended to do everything in IntelliJ IDEA.\
You can open this project in IntelliJ easily:
* Click *File* > *New* > *Project from Version Control...*
* Enter `https://github.com/SuCraft/Sucraft.git`
* Choose a destination directory
* Click *Clone*

Before starting, you (probably) need to have Git and the Java 25 JDK installed,
although installing IntelliJ will usually do that for you.

To set up the entire infrastructure for testing, simply run the command:
```bash
./gradlew :scaffolding:update
```

This creates the entire directory structure and all binaries inside the `run` folder.\
After making changes (to a plugin or something else), you can re-run `:scaffolding:update`
to update the `run` folder with your new changes.

Then, run this command to start everything:
```bash
./gradlew :scaffolding:run
```
