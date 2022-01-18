# SuCraft

## What is this?

This repository contains the source code for SuCraft.

## What can I do with this?

You can
- Help with the code by proposing changes or finding mistakes
- Run some of the plugins on your own server
- Use the code as an example to learn programming

## How do I download the plugins for my own server?

Click on [Actions](https://github.com/SuCraft/SuCraft/actions), choose the latest item with a ✅ checkmark next to it, and under **Artifacts** click **plugins**: it's a zip file containing all the plugin jars.

Some plugins depend on others, and all of them depend on Core. For example, you can see which plugins are required for MysteryBoxes by opening (in this repository) *MysteryBoxes/src/main/resources/plugin.yml* and checking the list after **depend**.

## What do I need to know to understand the code?

The code is currently fully written in the Kotlin programming language. Minecraft itself is written in Java. You need to know both to understand and change this code.

But don't fear, it's not super hard, and programming is very useful and this is a very fun way to learn! If you don't know some of these, don't worry too much, you can figure it out along the way too, that's how most people learn these.

Here are some tutorials that anyone can follow:

   [Minecraft plugins](https://www.youtube.com/playlist?list=PLfu_Bpi_zcDNEKmR82hnbv9UxQ16nUBF7)

   [Java](https://www.youtube.com/watch?v=GoXwIVyNvX0)

   [Kotlin](https://www.youtube.com/watch?v=F9UC9DY-vIU)

For questions, you can ask on Discord in [#development](https://discord.gg/pbsPkpUjG4).

## What if I find a mistake?

You can either:
- Create an issue on GitHub
- Fix the mistake in code and propose the change

## What do I need to know to make a change?

If you think you've made an improvement to the code somewhere, you can suggest it! The best way is simply to make a pull request on GitHub. If you're not familiar with Git, [here](https://docs.github.com/en/get-started/quickstart/hello-world)'s a tutorial! If the change is very small you can also send a snippet of code in a message. Optionally, if you want to add a whole new plugin, you need to know [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html).

## How to get the code working on your computer

### 1. Get the following software

- [Java JDK 17](https://www.oracle.com/java/technologies/downloads/)
  Make sure you have Java JDK 17, not Java 16 or Java JRE 17. You can check by opening *cmd* and typing *javac -version* (not *java -version*) and it must show version 17.
- [IntelliJ](https://www.jetbrains.com/idea/download/)
- A Git program, for example [GitHub Desktop](https://desktop.github.com/) or [SourceTree](https://www.sourcetreeapp.com/)

### 2. Download the code

Clone this repository. The easiest way is by going to:

   **Fork** (in the right top)

Then go to the forked version (*YourGitHubName/SuCraft* in your repositories) and do:

   **Code** > **Open with GitHub Desktop**

Using your Git program (like GitHub Desktop), clone the code to wherever you like (for example *C:/Users/Martijn/Desktop/SuCraft/code*).

### 3. Open it in the code editor

You can import all the SuCraft code in IntelliJ at once:

   **File** > **New** > **Project from Existing Sources...**

   > Select the directory you put the code in (e.g. *C:/Users/Martijn/Desktop/SuCraft/code*)

   > **Import project from external model** > **Maven** > **Finish**

### 3. Create a Paper server

You should start a new Paper server (download the latest .jar from the [Paper website](https://papermc.io/downloads) and place it where you want to run the test server (for example *C:/Users/Martijn/Desktop/SuCraft/testserver*)). Rename the jar you downloaded to *server.jar* so you can easily replace it with a newer version later without updating anything else.

Create a new file in the same folder, called *server.bat*, and open it with a text editor, and type

> java -jar server.jar

Now you can run *server.bat* and it will start the server. One time, you will have to open *eula.txt* and set it to *true*. The first time the server has started, it will generate a folder called *plugins*.

### 4. Set up automatically copying the plugins to the server

Then, we must add all the plugins to the server's *plugins* folder. In IntelliJ, you can easily compile all plugins and automatically place them in the server *plugins* folder. The first time, you must do this:

- To have the plugin .jars automatically placed into the test server *plugins* folder, you have to create a new file called *sucraft.properties* in the root folder of the code (next to the *LICENSE* and *README.md* files). In the file, type (replace the *C:/Users/Martijn/Desktop/SuCraft/testserver* with your own test server path, please use / instead of \\):
  > exportPluginsPath=C:/Users/Martijn/Desktop/SuCraft/testserver/plugins
- After saving that file, click on **Maven** on the vertical tab on the right side of the screen > **SuCraft** > **Lifecycle** > Right-click *install* > **Execute After Build**

You can now compile all plugins and place them in your server's *plugins* folder automatically, by clicking the green play button in the top-right (**Run Maven Build**).
(After the first time, you can probably also click the green hammer button in the top toolbar (**Build Project**).)

### 5. Now you can change the code yourself and also test the changes immediately!

For questions, you can ask Martijn in [#development](https://discord.gg/pbsPkpUjG4).
