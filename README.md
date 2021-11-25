# SuCraft

## What is this?

This repository contains the source code for SuCraft. You can help in three ways:
* If there is a bug on the server, please report it, or if there is a feature you want, request it (create a new issue under Issues)
* Change the code yourself, and do a pull request (so I can add your changes)
* If there is a tiny mistake in the code or you think you identified something, you can also just create an issue for it or send me updated code directly

## How to help with the code

What you need to know about: Java (programming language), Bukkit (server software), Eclipse (code editor), Maven (using dependencies). If you don't know some of these, you can figure it out along the way too, that's how I learnt a lot of these things.

### 1. Download the code

Clone this repository. The easiest way is by going to:

> **Code** > **Open with GitHub Desktop**

Place it wherever you like (for example *C:/Users/Martijn/Desktop/SuCraft/code*).

### 2. Open it in a code editor

Of course you must have installed [Java 16](https://www.java.com/) (I strongly recommend using [**Eclipse** IDE](https://www.eclipse.org/downloads/) since I will set the Maven dependencies in Eclipse, you can use:

> **File** > **Import...** > **Existing Maven Projects** > select the directory you put the code in (e.g. *C:/Users/Martijn/Desktop/SuCraft/code*) > Check everything > **Finish**

### 3. Start a test server

You should start a new Paper server (download the latest .jar from the [PaperMC website](https://papermc.io/downloads) and place it where you want to run the test server (for example *C:/Users/Martijn/Desktop/SuCraft/testserver*). Rename the jar you downloaded to *server.jar*.

Create a new file in the same folder, called *server.bat*, and open it with Notepad, and type

> java -jar server.jar

Now you can run *server.bat* and it will start the server. One time, you will have to open *eula.txt* and set it to *true*. The first time the server has started, it will generated a folder called *plugins*.

Then you should add all the plugins to the */plugins* folder. In Eclipse you can easily compile all plugins (Right-click on the *sucraft* project > **Run As** > **Maven install**). To have it automatically placed into the test server plugins folder, you have to create a new file called *sucraft.properties* inside the *sucraft* project folder (next to the *LICENSE* and *README.md* files). In the file, type (replace the *C:/Users/Martijn/Desktop/SuCraft/testserver* with your own test server path):

> exportPluginsPath=C:/Users/Martijn/Desktop/SuCraft/testserver/plugins

### Now you can change the code and also test the changes!
