# SuCraft

## What is this?

This repository contains the source code for SuCraft. You can help in three ways:
* If there is a bug on the server, please report it, or if there is a feature you want, request it (create a new issue under Issues)
* Change the code yourself, and do a pull request (so I can add your changes)
* If there is a tiny mistake in the code or you think you identified something, you can also just create an issue for it or send me updated code directly

## How to help with the code

What you need to know about: Java (programming language), Bukkit (server software), Eclipse (code editor), Maven (using dependencies). If you don't know some of these, you can figure it out along the way too, that's how I learnt a lot of these things.

### 1. Download the code

Clone this repository. The easiest way is by going to **Code** > **Open with GitHub Desktop**.

### 2. Open it in a code editor

I strongly recommend using **Eclipse** since I will set the dependencies in Eclipse, you can use **File** > **Open Projects from File System...** and then select all projects in the folder (where you downloaded it earlier).

### 3. Start a test server

You should start a new Paper server (download the latest .jar from https://papermc.io/downloads and execute *java -jar paper-1.17.1-somenumber.jar* in cmd or in a .bat file).

Then you should add all the plugins to the */plugins* folder. In Eclipse you can easily compile a plugin (**Run** > **Run As** > **Maven install**). To have it automatically placed into the test server plugins folder, you can define the environment variable **SUCRAFT_PLUGIN_PATH** on your computer. For example, set **SUCRAFT_PLUGIN_PATH** to 'C:/Users/Martijn/Desktop/SuCraft/plugins/'. ([How to add environment variables on Windows](https://docs.oracle.com/en/database/oracle/machine-learning/oml4r/1.5.1/oread/creating-and-modifying-environment-variables-on-windows.html#GUID-DD6F9982-60D5-48F6-8270-A27EC53807D0))

### Now you can change the code and also test the changes!
