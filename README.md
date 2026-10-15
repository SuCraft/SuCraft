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

1. Install Java JDK 25\
   (*must be JDK*, and versions other than 25 may not work)
   * Windows: in Windows Terminal or PowerShell run\
     `winget install EclipseAdoptium.Temurin.25.JDK`
   * Linux:\
     `sudo apt-get install -y openjdk-25-jdk`
   * macOS:\
     `brew install openjdk@25`\
\
   To verify Java 25 is installed, run\
   `java -version`\
   It should show something a bit like this:
   ```
   openjdk 25 2025-09-16
   OpenJDK Runtime Environment (build 25+36-3489)
   OpenJDK 64-Bit Server VM (build 25+36-3489, mixed mode, sharing)
   ```
1. Install Git
   * Windows: [Download](https://gitforwindows.org/)\
     _Note_: you should also have Git Bash. The download link above includes it.
   * Linux:\
     `sudo apt install git`
   * macOS:\
     `brew install git`\
     \
     To verify Git is installed, run\
     `git -v`\
     It should show something a bit like this:
   ```
   git version 2.54.0
   ```
1. Install Maven
    * Windows: in Windows Terminal or PowerShell run\
      `winget install Apache.Maven`
    * Linux:\
      `sudo apt install maven`
    * macOS:\
      `brew install maven`\
      \
      To verify Maven is installed, run\
      `mvn -v`\
      It should show something a bit like this:
   ```
   Apache Maven 3.10.0 (c43a36b8d67be7e0805a411bc0898af1a51f5472)
   Maven home: C:\Program Files\Apache\Maven\apache-maven-3.10.0
   Java version: 25.0.4.1, vendor: Oracle Corporation
   Default locale: en_US, platform encoding: UTF-8
   OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
   ```
1. Install **[IntelliJ IDEA](https://www.jetbrains.com/idea/download)**
1. Clone the source code:\
   * In IntelliJ, click **File** > **New** > **Project from Version Control...**
   * In the **URL**, enter `https://github.com/SuCraft/Sucraft.git`
   * In the **Directory**, choose a folder to clone the code to\
     (for example: `C:/Users/Martijn/Documents/SuCraft`)
   * Click **Clone**
   * When a popup appears, click **Trust Project**
1. Wait for the project to be imported.\
   Importing is indicated by a loading bar in the bottom-right of the screen
   that says *Importing ...*
1. Open the Gradle window by clicking the Gradle icon on the right side.\
   It is the icon of the small elephant.
1. Press the 🔄 button in the Gradle window (**Sync All Gradle Projects**)
   and wait for the process to complete
1. In the Gradle window, open **Suki** > **Tasks** > **patching**
   and double-click **applyAllPatches**
1. A terminal window opens in the bottom.\
   Wait until it completes (this may take 5 minutes).
1. In the Gradle window, open **scaffolding** > **Tasks** > **scaffolding**
   and double-click **updateAndRun**
1. Wait for the server to start (this may take 3 minutes).\
   The server is read when you see one of the last lines saying something like
   ```
   Done (59.437s)! For help, type "help"
   ```
1. You can now connect to the server with any Minecraft client!\
   The ip is: `localhost`
1. To stop the server, type `stop` into the terminal window
   (the same window that shows the server logs) and press &lt;Enter&gt; on your keyboard.
   After a few seconds and more lines appearing, press &lt;Enter&gt; on your keyboard again.\
   *Alternatively*, to terminate the server unsafely,
   click the red square button in the top-left of the terminal window.

## How to modify

`scaffolding` creates the entire server structure inside the `run` folder.

[//]: # (After making changes &#40;to a plugin or something else&#41;, you can re-run `:scaffolding:update`)

[//]: # (to update the `run` folder with your new changes.)

[//]: # ()
[//]: # (Then, to start everything, run the script located at `run/start_all.sh`.)

[//]: # ()
[//]: # (By default, this script assumes you have Linux with a GNOME terminal, macOS, or Windows with Git Bash installed.)


