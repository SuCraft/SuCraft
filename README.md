# SuCraft

## What is this?

This repository contains the source code for SuCraft. You can help in three ways:
* If there is a bug on the server, please report it, or if there is a feature you want, request it (create a new issue under Issues)
* Change the code yourself, and make a pull request (so I can add your changes)
* If there is a tiny mistake in the code, or you think you identified something, you can also just create an issue for it or send me updated code directly

## How to help with the code

What you need to know about: Java <sup>[Tutorial](https://www.youtube.com/watch?v=GoXwIVyNvX0)</sup> and Kotlin <sup>[Tutorial](https://www.youtube.com/watch?v=F9UC9DY-vIU)</sup> (programming languages), Paper <sup>[Tutorial](https://www.youtube.com/playlist?list=PLfu_Bpi_zcDNEKmR82hnbv9UxQ16nUBF7)</sup> (server software), [IntelliJ](https://www.jetbrains.com/idea/download/) (code editor), Git  with GitHub <sup>[Tutorial](https://docs.github.com/en/get-started/quickstart/hello-world)</sup> (collaboration tool), [Lombok](https://projectlombok.org/features/all) (extension of Java), Maven (using dependencies). If you don't know some of these, don't worry too much, you can figure it out along the way too, that's how I learnt a lot of these things. For questions, you can ask Martijn in [#development](https://discord.gg/pbsPkpUjG4).

### 1. Download the code

Clone this repository. The easiest way is by going to:

* **Fork** (in the right top)

Then go to the forked version (*YourGitHubName/SuCraft* in your repositories) and do:

* **Code** > **Open with GitHub Desktop**

Download the GitHubDesktop program, and clone the code to wherever you like (for example *C:/Users/Martijn/Desktop/SuCraft/code*).

### 2. Open it in a code editor

Of course, you must have installed [Java JDK 17](https://www.oracle.com/java/technologies/downloads/) (not Java 16, and not Java JRE 17, you can check your Java JDK version by opening *cmd* and typing *javac -version* (not *java -version)). To edit the code, I recommend using the [IntelliJ](https://www.jetbrains.com/idea/download/) since it comes with all the languages and tools used. Then you can import all the SuCraft code at once:

* **File** > **New** > **Project from Existing Sources...** > Select the directory you put the code in (e.g. *C:/Users/Martijn/Desktop/SuCraft/code*) > **Import project from external model** > **Maven** > **Finish**

### 3. Start a test server

You should start a new Paper server (download the latest .jar from the [Paper website](https://papermc.io/downloads) and place it where you want to run the test server (for example *C:/Users/Martijn/Desktop/SuCraft/testserver*)). Rename the jar you downloaded to *server.jar*.

Create a new file in the same folder, called *server.bat*, and open it with Notepad, and type

> java -jar server.jar

Now you can run *server.bat* and it will start the server. One time, you will have to open *eula.txt* and set it to *true*. The first time the server has started, it will generate a folder called *plugins*.

Then you should add all the plugins to the */plugins* folder. In IntelliJ, you can easily compile all plugins. The first time, you must do this:

* To have the plugin .jars automatically placed into the test server plugins folder, you have to create a new file called *sucraft.properties* in the root folder (next to the *LICENSE* and *README.md* files). In the file, type (replace the *C:/Users/Martijn/Desktop/SuCraft/testserver* with your own test server path, please use / instead of \\):
  > exportPluginsPath=C:/Users/Martijn/Desktop/SuCraft/testserver/plugins

* After saving that file, click on **Maven** on the vertical tab on the right side of the screen > **SuCraft** > **Lifecycle** > Right-click *install* > **Execute After Build**

To compile all plugins and place them in your plugins folder automatically, you can now:

* Click the green play button in the top-right (*Run Maven Build*)

After the first time, you can probably also click the green hammer button in the top toolbar (*Build Project*).

### Now you can change the code yourself and also test the changes!

For questions, you can ask Martijn in [#development](https://discord.gg/pbsPkpUjG4).
