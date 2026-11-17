import com.github.houbb.opencc4j.util.ZhConverterUtil
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.TreeMap
import java.util.zip.ZipFile
import kotlin.collections.filter

fun JsonArray.insert(index: Int, element: JsonElement) {
    if (index == size()) {
        add(element)
        return
    }
    val oldSize = size()
    add(get(oldSize - 1))
    for (i in oldSize - 1 downTo index + 1) {
        set(i, get(i - 1))
    }
    set(index, element)
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.github.houbb:opencc4j:1.14.0")
        classpath("com.google.code.gson:gson:2.13.2")
    }
}

plugins {
    id("java-library")
    id("com.gradleup.shadow") version "9.4.1"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
    maven("https://libraries.minecraft.net")
}

configurations {
    create("shadowInclude")
}

dependencies {
    compileOnly("org.sucraft.suki:suki-api:26.3-R0.1-SNAPSHOT")
    compileOnly("org.sucraft.suki:suki-server:26.3-R0.1-SNAPSHOT")
    compileOnly("com.mojang:datafixerupper:10.0.21")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(25)
}

val serverDir: File = projectDir.resolve("run")
val pluginDir: File = serverDir.resolve("plugins")

val generatedResourcesDir: Provider<Directory> = layout.buildDirectory.dir("tmp/generated_resources")

val generateResources by tasks.registering {
    val outputDir = generatedResourcesDir

    // Declare the entire directory as this task's output.
    outputs.dir(outputDir)

    doLast {
        val dir = outputDir.get().asFile
        dir.mkdirs()

        // Keep track of exactly which files this invocation generates.
        val generatedFiles = mutableSetOf<String>()

        fun writeGeneratedFile(relativePath: String, content: String) {
            generatedFiles += relativePath

            val file = dir.resolve(relativePath)
            file.parentFile.mkdirs()
            file.writeText(content)
        }

        // Example generated files.
        writeGeneratedFile(
            "foo.txt",
            "This is the contents of foo.txt"
        )

        writeGeneratedFile(
            "bar.txt",
            "This is the contents of bar.txt"
        )

        writeGeneratedFile(
            "nested/baz.txt",
            "This is the contents of nested/baz.txt"
        )

        writeGeneratedFile(
            "nested/deeper/qux.txt",
            "This is the contents of nested/deeper/qux.txt"
        )

        // Remove anything that was not generated during this invocation.
        dir.walkTopDown()
            .filter { it.isFile }
            .forEach { file ->
                val relativePath = file.relativeTo(dir).invariantSeparatorsPath

                if (relativePath !in generatedFiles) {
                    file.delete()
                }
            }

        // Remove empty directories left behind by deleted files.
        dir.walkBottomUp()
            .filter { it.isDirectory && it != dir }
            .forEach { directory ->
                if (directory.listFiles()?.isEmpty() == true) {
                    directory.delete()
                }
            }
    }
}

val runDatagen = tasks.register<Exec>("runDatagen") {
    group = "data generation"
    workingDir = file("../../..")
    commandLine("./gradlew", ":plugins:Sushi:datagen:runDatagen") // Windows
}

tasks {

    build {
        dependsOn(shadowJar)
    }

    withType<Jar> {
        dependsOn(runDatagen)
        // Include generated resources
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from("datagen/src/main/generated") {
            exclude("assets/**")
            exclude("data/**")
        }
        from("datagen/src/main/generated/assets") {
            into("resource_pack/assets")
        }
        from("datagen/src/main/generated/data") {
            into("data_pack/data")
        }
        includeEmptyDirs = false
        // Don't include data generation sources
        if (name == "shadowJar") {
            exclude("assets/**")
            exclude("data/**")
        }
    }

    shadowJar {
        // dependsOn("jar")
        configurations = listOf(project.configurations.getByName("shadowInclude"))
    }

    abstract class GenerateLangFileTask : DefaultTask() {

        init {
            group = "language file generation"
        }

        @get:OutputFile
        val outputFile = project.objects.fileProperty()

    }

    abstract class GenerateLangFileFromParametrizedLangFileTask : GenerateLangFileTask() {

        @get:InputFile
        val inputFile = project.objects.fileProperty()

        @get:Input
        @get:Optional
        abstract val valuePostProcessFunction: Property<(String, String) -> String>

        @TaskAction
        fun generate() {
            val inputAsString = inputFile.get().asFile.readText()
            val inputAsMap = JsonSlurper().parseText(inputAsString) as Map<String, String>
            val parameters = inputAsMap
                .filter { (key, _) -> key.startsWith("#") }
                .mapKeys { (key, _) -> key.removePrefix("#") }
            val actualValuePostProcessFunction = valuePostProcessFunction.orNull
            val outputAsMap = inputAsMap
                .filter { (key, _) -> !key.startsWith("#") }
                .mapValues { (fullKey, value) ->
                    if (value != "=") return@mapValues value
                    val key = fullKey.substring(fullKey.lastIndexOf('.') + 1)
                    val stringBuilder = TreeMap<Double, MutableList<String>>()
                    var index = 0
                    while (index < key.length) {
                        val bestParameter = parameters
                            .filter { parameter -> key.startsWith(parameter.key, index) }
                            .maxByOrNull { it.key.length }
                        if (bestParameter == null) throw IllegalStateException(
                            "No sufficient parameters to decode parametrized key '$key' (at index $index) in '${inputFile.get().asFile.path}'"
                        )
                        val positionIndex = bestParameter.key.indexOf('<').takeIf { it != -1 }
                            ?: bestParameter.key.indexOf('>')
                        val position = if (positionIndex == -1)
                            0.0
                        else {
                            val positionValue =
                                if (positionIndex == 0) 1.0 else
                                    bestParameter.key.substring(0, positionIndex).toDouble()
                            val positionSign = if (bestParameter.key[positionIndex] == '<') -1.0 else 1.0
                            positionValue * positionSign
                        }
                        stringBuilder.computeIfAbsent(position) { ArrayList() } += bestParameter.value
                        index += bestParameter.key.length
                    }
                    val result = stringBuilder.entries
                        .joinToString("") { (_, value) -> value.joinToString("") }
                    return@mapValues actualValuePostProcessFunction?.let { it(fullKey, result) } ?: result
                }
            val outputAsString = JsonOutput.prettyPrint(JsonOutput.toJson(outputAsMap), true)
            outputFile.get().asFile.apply {
                parentFile.mkdirs()
                writeText(outputAsString)
            }
        }

    }

    abstract class GenerateLangFileByMappingOtherLangFileTask : GenerateLangFileTask() {

        @get:InputFile
        val inputFile = project.objects.fileProperty()

        @get:Input
        abstract val valueFunction: Property<(String, String) -> String>

        @TaskAction
        fun generate() {
            val inputAsString = inputFile.get().asFile.readText()
            val inputAsMap = JsonSlurper().parseText(inputAsString) as Map<String, String>
            val actualValueFunction = valueFunction.get()
            val outputAsMap = inputAsMap.mapValues { (key, value) ->
                actualValueFunction(key, value)
            }
            val outputAsString = JsonOutput.prettyPrint(JsonOutput.toJson(outputAsMap), true)
            outputFile.get().asFile.apply {
                parentFile.mkdirs()
                writeText(outputAsString)
            }
        }

    }

    abstract class GenerateLangFileByCopyingOtherLangFileTask : GenerateLangFileTask() {

        @get:InputFile
        val inputFile = project.objects.fileProperty()

        @TaskAction
        fun generate() {
            outputFile.get().asFile.apply {
                parentFile.mkdirs()
                writeText(inputFile.get().asFile.readText())
            }
        }

    }

    abstract class GenerateLangFileByMergingOtherLangFilesTask : GenerateLangFileTask() {

        @get:InputFile
        val inputFile1 = project.objects.fileProperty()

        @get:InputFile
        val inputFile2 = project.objects.fileProperty()

        @TaskAction
        fun generate() {
            val inputAsString1 = inputFile1.get().asFile.readText()
            val inputAsMap1 = JsonSlurper().parseText(inputAsString1) as Map<String, String>
            val inputAsString2 = inputFile2.get().asFile.readText()
            val inputAsMap2 = JsonSlurper().parseText(inputAsString2) as Map<String, String>
            val outputAsMap = (inputAsMap1.keys + inputAsMap2.keys).associateWith { key ->
                if (inputAsMap2.containsKey(key)) inputAsMap2[key] else inputAsMap1[key]
            }
            val outputAsString = JsonOutput.prettyPrint(JsonOutput.toJson(outputAsMap), true)
            outputFile.get().asFile.apply {
                parentFile.mkdirs()
                writeText(outputAsString)
            }
        }

    }

    fun parametrizedLangFile(locale: String) =
        layout.projectDirectory.file("src/langfiles/parametrized/$locale.json")

    fun overridesLangFile(locale: String) =
        layout.projectDirectory.file("src/langfiles/overrides/$locale.json")

    fun generatedLangFile(locale: String) =
        layout.buildDirectory.file("generated/langfiles/resource_pack/assets/minecraft/lang/$locale.json")

    // Process parameters
    val generateDeDe =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateDeDe") {
            inputFile = parametrizedLangFile("de_de")
            outputFile = generatedLangFile("de_de")
        }
    val generateEnPt =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateEnPt") {
            inputFile = parametrizedLangFile("en_pt")
            outputFile = generatedLangFile("en_pt")
        }
    val generateEnUs =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateEnUs") {
            inputFile = parametrizedLangFile("en_us")
            outputFile = generatedLangFile("en_us")
        }
    val generateFrFr =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateFrFr") {
            inputFile = parametrizedLangFile("fr_fr")
            outputFile = generatedLangFile("fr_fr")
            valuePostProcessFunction = { _, value -> value.replaceFirstChar { it.uppercase() } }
        }
    val generateJaJp =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateJaJp") {
            inputFile = parametrizedLangFile("ja_jp")
            outputFile = generatedLangFile("ja_jp")
        }
    val generateLolUs =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateLolUs") {
            inputFile = parametrizedLangFile("lol_us")
            outputFile = generatedLangFile("lol_us")
        }
    val generateNlNl =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateNlNl") {
            inputFile = parametrizedLangFile("nl_nl")
            outputFile = generatedLangFile("nl_nl")
        }
    val generateTrTr =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateTrTr") {
            inputFile = parametrizedLangFile("tr_tr")
            outputFile = generatedLangFile("tr_tr")
        }
    val generateZhTw =
        register<GenerateLangFileFromParametrizedLangFileTask>("generateZhTw") {
            inputFile = parametrizedLangFile("zh_tw")
            outputFile = generatedLangFile("zh_tw")
        }

    // Substitute
    val generateEnGb =
        register<GenerateLangFileByMergingOtherLangFilesTask>("generateEnGb") {
            dependsOn(generateEnUs)
            inputFile1 = generatedLangFile("en_us")
            inputFile2 = overridesLangFile("en_gb")
            outputFile = generatedLangFile("en_gb")
        }

    // Turn text upside down
    val generateEnUd =
        register<GenerateLangFileByMappingOtherLangFileTask>("generateEnUd") {
            dependsOn(generateEnGb)
            inputFile = generatedLangFile("en_gb")
            outputFile = generatedLangFile("en_ud")
            valueFunction = { _, value ->
                val regularText =
                    "abcdefghijklmnpqrtuvwyABCDEFGJKLMPQRTUVWY12345679.,'\"?!([{<&;"
                val upsidedownText =
                    "ɐqɔpǝɟᵷɥᴉɾʞꞁɯudbɹʇnʌʍʎⱯᗺƆᗡƎℲ⅁ՐꞰꞀWԀꝹᴚ⟘∩ɅM⅄⥝ᘔƐ߈ϛ9ㄥ6˙',„¿¡)]}>⅋⸵"
                value.map {
                    regularText.indexOf(it).takeIf { it != -1 }?.let { upsidedownText[it] }
                        ?: upsidedownText.indexOf(it).takeIf { it != -1 }?.let { regularText[it] }
                        ?: it
                }.joinToString("").reversed()
            }
        }

    // Traditional to simplified
    val generateZhCn =
        register<GenerateLangFileByMappingOtherLangFileTask>("generateZhCn") {
            dependsOn(generateZhTw)
            inputFile = generatedLangFile("zh_tw")
            outputFile = generatedLangFile("zh_cn")
            valueFunction = { _, value -> ZhConverterUtil.toSimple(value) }
        }

    // Simple copy
    val generateEnAu =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateEnAu") {
            dependsOn(generateEnGb)
            inputFile = generatedLangFile("en_gb")
            outputFile = generatedLangFile("en_au")
        }
    val generateEnCa =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateEnCa") {
            dependsOn(generateEnGb)
            inputFile = generatedLangFile("en_gb")
            outputFile = generatedLangFile("en_ca")
        }
    val generateEnNz =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateEnNz") {
            dependsOn(generateEnGb)
            inputFile = generatedLangFile("en_gb")
            outputFile = generatedLangFile("en_nz")
        }
    val generateFrCa =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateFrCa") {
            dependsOn(generateFrFr)
            inputFile = generatedLangFile("fr_fr")
            outputFile = generatedLangFile("fr_ca")
        }
    val generateNlBe =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateNlBe") {
            dependsOn(generateNlNl)
            inputFile = generatedLangFile("nl_nl")
            outputFile = generatedLangFile("nl_be")
        }
    val generateZhHk =
        register<GenerateLangFileByCopyingOtherLangFileTask>("generateZhHk") {
            dependsOn(generateZhTw)
            inputFile = generatedLangFile("zh_tw")
            outputFile = generatedLangFile("zh_hk")
        }

    processResources {

        // Generated lang files
        dependsOn(
            generateDeDe,
            generateEnAu,
            generateEnCa,
            generateEnGb,
            generateEnNz,
            generateEnPt,
            generateEnUs,
            generateFrCa,
            generateFrFr,
            generateJaJp,
            generateLolUs,
            generateNlBe,
            generateNlNl,
            generateTrTr,
            generateZhCn,
            generateZhHk,
            generateZhTw,
            generateEnUd,
            generateZhCn
        )

    }

    processResources {
        val props = mapOf(
            "version" to version,
            "description" to project.description,
            "apiVersion" to "\"${project.providers.gradleProperty("apiVersion").get()}\"",
        )
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }

}

// Include some vanilla client files, potentially modified
val vanillaClientURL = providers.gradleProperty("vanillaClientURL").get()
val vanillaClientDownloadFolder = layout.buildDirectory.dir("vanilla-client/downloads").get()
val vanillaClientDownloadFile = vanillaClientDownloadFolder.file("${providers.gradleProperty("apiVersion").get()}.jar")
val vanillaClientAssetsOutputFolder = layout.buildDirectory.dir("resources/main/resource_pack/assets").get()
val vanillaClientDataOutputFolder = layout.buildDirectory.dir("resources/main/data_pack/data").get()

val downloadMinecraftClient: TaskProvider<Task> = tasks.register("downloadMinecraftClient") {
    group = "vanilla client files"

    outputs.dir(vanillaClientDownloadFolder)
    doLast {
        val url = vanillaClientURL
            ?: throw IllegalArgumentException("No vanillaClientURL configured; skipping direct download.")
        vanillaClientDownloadFolder.asFile.mkdirs()
        val target = vanillaClientDownloadFile.asFile
        if (!target.exists()) {
            logger.lifecycle("Downloading Minecraft client from $url -> $target")
            URL(url).openStream().use { input ->
                Files.copy(input, target.toPath(), StandardCopyOption.REPLACE_EXISTING)
            }
        }
    }
}

sourceSets {
    main {
        java.srcDir(layout.projectDirectory.dir("common-with-datagen/src/main/java"))
        resources.srcDir(layout.projectDirectory.dir("common-with-datagen/src/main/resources"))
        resources.srcDir(layout.buildDirectory.dir("generated/langfiles"))
        java.srcDir(layout.projectDirectory.dir("src/fake-fabric-stubs/java"))
        resources.srcDir(layout.projectDirectory.dir("src/fake-fabric-stubs/resources"))
    }
}

data class IncludedVanillaFile(
    val inputPath: String,
    val outputPath: String = inputPath,
    val contentFunction: ((String) -> String)? = null
)

fun includedVanillaJSONFile(
    inputPath: String,
    outputPath: String = inputPath,
    contentFunction: (JsonElement) -> JsonElement
) = IncludedVanillaFile(inputPath, outputPath) {
    GsonBuilder().setPrettyPrinting().create().toJson(contentFunction(JsonParser.parseString(it)))
}

fun includedVanillaJSONObjectFile(
    inputPath: String,
    outputPath: String = inputPath,
    contentFunction: (JsonObject) -> JsonObject
) = includedVanillaJSONFile(inputPath, outputPath) {
    contentFunction(it.asJsonObject)
}

fun includedVanillaJSONObjectFileMutated(
    inputPath: String,
    outputPath: String = inputPath,
    contentFunction: JsonObject.() -> Unit
) = includedVanillaJSONObjectFile(inputPath, outputPath, { it: JsonObject ->
    it.apply {
        contentFunction()
    }
})

fun includedVanillaJSONArrayFile(
    inputPath: String,
    outputPath: String = inputPath,
    contentFunction: (JsonArray) -> JsonArray
) = includedVanillaJSONFile(inputPath, outputPath) {
    contentFunction(it.asJsonArray)
}

fun includedVanillaJSONArrayFileMutated(
    inputPath: String,
    outputPath: String = inputPath,
    contentFunction: JsonArray.() -> Unit
) = includedVanillaJSONArrayFile(inputPath, outputPath, { it: JsonArray ->
    it.apply {
        contentFunction()
    }
})

fun extractVanillaFiles(
    folderName: String,
    outputFolder: File,
    files: List<IncludedVanillaFile>
) {
    outputFolder.mkdirs()
    val sourceJar: File = vanillaClientDownloadFile.asFile

    logger.lifecycle("Extracting $folderName from ${sourceJar.absolutePath}")

    val filesByInputName = files.groupBy { file -> file.inputPath }
    val inputNamesYetToSee = filesByInputName.keys.toMutableSet()

    ZipFile(sourceJar).use { zip ->
        val entries = zip.entries()
        while (entries.hasMoreElements()) {
            val entry = entries.nextElement()

            // We only want specific entries
            if (!entry.name.startsWith("$folderName/")) continue
            val inputName = entry.name.removePrefix("$folderName/")
            val filesForInputName = filesByInputName[inputName]
            if (filesForInputName.isNullOrEmpty()) continue
            inputNamesYetToSee.remove(inputName)

            for (file in filesForInputName) {
                val outFile = outputFolder.resolve(file.outputPath)
                if (entry.isDirectory) {
                    outFile.mkdirs()
                } else {
                    outFile.parentFile?.mkdirs()
                    zip.getInputStream(entry).use { input ->
                        if (file.contentFunction == null) {
                            Files.copy(input, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
                        } else {
                            outFile.writeText(file.contentFunction(String(input.readAllBytes())))
                        }
                    }
                }
            }
        }
    }

    if (!inputNamesYetToSee.isEmpty()) {
        throw IllegalStateException("Did not encounter all input names: $folderName / $inputNamesYetToSee")
    }
}

val extractVanillaAssets: TaskProvider<Task> = tasks.register("extractVanillaAssets") {
    group = "vanilla client files"

    dependsOn(downloadMinecraftClient, "processResources")
    outputs.dir(vanillaClientAssetsOutputFolder)

    doLast {
        extractVanillaFiles(
            "assets",
            vanillaClientAssetsOutputFolder.asFile,
            (listOf(
                "black",
                "blue",
                "brown",
                "cyan",
                "gray",
                "green",
                "light_blue",
                "light_gray",
                "lime",
                "magenta",
                "orange",
                "pink",
                "purple",
                "red",
                "white",
                "yellow"
            ).flatMap { listOf("${it}_concrete", "${it}_wool") } + listOf(
                "tuff"
            )).flatMap { listOf("${it}_slab", "${it}_stairs") }
                .flatMap {
                    listOf(
                        IncludedVanillaFile("minecraft/blockstates/$it.json", "sucraft/blockstates/$it.json"),
                        IncludedVanillaFile("minecraft/items/$it.json", "sucraft/items/$it.json")
                    )
                }
        )
    }
}

data class StructureSet(
    val name: String,
    val separationDelta: Int = 0,
    val spacingDelta: Int = 0
)

val extractVanillaData: TaskProvider<Task> = tasks.register("extractVanillaData") {
    group = "vanilla client files"

    dependsOn(downloadMinecraftClient, "processResources")
    outputs.dir(vanillaClientDataOutputFolder)

    doLast {
        extractVanillaFiles(
            "data",
            vanillaClientDataOutputFolder.asFile,
            listOf(
                includedVanillaJSONArrayFileMutated("minecraft/block_transformer/axe.json") {
                    add(JsonObject().apply {
                        addProperty("item_damage_per_use", 1)
                        addProperty("sound", "minecraft:item.axe.strip")
                        add("block_state_provider", JsonObject().apply {
                            addProperty("type", "minecraft:rule_based")
                            add("rules", JsonArray().apply {
                                listOf(
                                    Triple("acacia", false, true),
                                    Triple("birch", false, true),
                                    Triple("cherry", false, true),
                                    Triple("crimson", true, true),
                                    Triple("dark_oak", false, true),
                                    Triple("jungle", false, true),
                                    Triple("mangrove", false, true),
                                    Triple("oak", false, true),
                                    Triple("pale_oak", false, true),
                                    Triple("poplar", false, true),
                                    Triple("spruce", false, true),
                                    Triple("warped", true, true),
                                    Triple("frost", false, false)
                                ).forEach { (woodType, nether, vanilla) ->
                                    listOf(false, true).forEach { wood ->
                                        listOfNotNull(
                                            ("" to JsonObject().apply {
                                                addProperty("axis", "y")
                                            }).takeIf { !vanilla },
                                            ("_slab" to JsonObject().apply {
                                                addProperty("type", "bottom")
                                                addProperty("waterlogged", "false")
                                            }),
                                            ("_stairs" to JsonObject().apply {
                                                addProperty("facing", "north")
                                                addProperty("half", "bottom")
                                                addProperty("shape", "straight")
                                                addProperty("waterlogged", "false")
                                            }),
                                            ("_wall" to JsonObject().apply {
                                                addProperty("up", "true")
                                                addProperty("north", "none")
                                                addProperty("east", "none")
                                                addProperty("south", "none")
                                                addProperty("west", "none")
                                                addProperty("waterlogged", "false")
                                            }).takeIf { wood },
                                            ("_fence" to JsonObject().apply {
                                                addProperty("north", "false")
                                                addProperty("east", "false")
                                                addProperty("south", "false")
                                                addProperty("west", "false")
                                                addProperty("waterlogged", "false")
                                            }).takeIf { wood },
                                            ("_fence_gate" to JsonObject().apply {
                                                addProperty("facing", "north")
                                                addProperty("open", "false")
                                                addProperty("powered", "false")
                                                addProperty("in_wall", "false")
                                            }).takeIf { wood },
                                            ("_button" to JsonObject().apply {
                                                addProperty("facing", "north")
                                                addProperty("powered", "false")
                                                addProperty("face", "wall")
                                            }).takeIf { wood },
                                            ("_pressure_plate" to JsonObject().apply {
                                                addProperty("powered", "false")
                                            }).takeIf { wood }
                                        ).forEach { (shape, properties) ->
                                            val unstrippedPath =
                                                "${woodType}_${if (wood) (if (nether) "hyphae" else "wood") else (if (nether) "stem" else "log")}$shape"
                                            val strippedPath = "stripped_$unstrippedPath"
                                            add(JsonObject().apply {
                                                add("if_true", JsonObject().apply {
                                                    addProperty("type", "minecraft:matching_blocks")
                                                    addProperty(
                                                        "blocks",
                                                        "sucraft:$unstrippedPath"
                                                    )
                                                })
                                                add("then", JsonObject().apply {
                                                    addProperty("type", "minecraft:copy_properties")
                                                    add("source", JsonObject().apply {
                                                        addProperty("id", "sucraft:$strippedPath")
                                                        add("properties", properties)
                                                    })
                                                })
                                            })
                                        }
                                    }
                                }
                            })
                        })
                    })
                }
            ) + listOf(
                StructureSet("abandoned_camp", -1, -10),
                StructureSet("ancient_cities", -1, -6),
                StructureSet("buried_treasures", 0, 0),
                StructureSet("desert_pyramids", -1, -3),
                StructureSet("end_cities", -2, -3),
                StructureSet("igloos", -2, -14),
                StructureSet("jungle_temples", -1, -9),
                StructureSet("mineshafts", 0, 0),
                StructureSet("nether_complexes", 0, -7),
                StructureSet("nether_fossils", 0, 0),
                StructureSet("ocean_monuments", 0, -5),
                StructureSet("ocean_ruins", -2, -6),
                StructureSet("pillager_outposts", -1, -6),
                StructureSet("ruined_portals", -4, -10),
                StructureSet("shipwrecks", 0, -5),
                StructureSet("strongholds", 0, 0),
                StructureSet("swamp_huts", -2, -8),
                StructureSet("trail_ruins", -3, -22),
                StructureSet("trial_chambers", -2, -10),
                StructureSet("villages", -7, -6),
                StructureSet("woodland_mansions", -4, -48)
            ).map { (name, separationDelta, spacingDelta) ->
                includedVanillaJSONObjectFileMutated("minecraft/worldgen/structure_set/$name.json") {
                    getAsJsonObject("placement").apply {
                        // Change salt to make seed cracking more difficult
                        addProperty("salt", get("salt").asInt + name.chars().sum())
                        // Make structures more common
                        if (separationDelta != 0) {
                            addProperty("separation", get("separation").asInt + separationDelta)
                        }
                        if (spacingDelta != 0) {
                            addProperty("spacing", get("spacing").asInt + spacingDelta)
                        }
                        if (name == "strongholds") {
                            addProperty("separation", 24)
                            addProperty("spacing", 36)
                            // Change strongholds to generate everywhere
                            addProperty("type", "minecraft:random_spread")
                        }
                    }
                    // Add surface strongholds
                    if (name == "strongholds") {
                        getAsJsonArray("structures").add(JsonObject().apply {
                            addProperty("structure", "sucraft:stronghold_surface")
                            addProperty("weight", 1)
                        })
                    }
                }
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/structure/stronghold.json",
                "sucraft/worldgen/structure/stronghold_surface.json"
            ) {
                addProperty("biomes", "#sucraft:has_structure/stronghold_surface")
                addProperty("terrain_adaptation", "none")
                addProperty("is_surface", true)
            } + includedVanillaJSONObjectFileMutated("minecraft/advancement/story/follow_ender_eye.json") {
                getAsJsonObject("criteria").getAsJsonObject("in_stronghold")
                    .getAsJsonObject("conditions").getAsJsonObject("player")
                    .getAsJsonObject("predicate").getAsJsonObject("minecraft:location").apply {
                        val existing = get("structures").asString
                        add("structures", JsonArray().apply {
                            add(existing)
                            add("sucraft:stronghold_surface")
                        })
                    }
            } + listOf("stronghold_corridor", "stronghold_crossing").map { source ->
                includedVanillaJSONObjectFileMutated("minecraft/loot_table/chests/$source.json") {
                    getAsJsonArray("pools").add(JsonObject().apply {
                        add("entries", JsonArray().apply {
                            add(JsonObject().apply {
                                addProperty("type", "minecraft:empty")
                                addProperty("weight", 4)
                            })
                            add(JsonObject().apply {
                                addProperty("type", "minecraft:item")
                                addProperty("name", "minecraft:ender_pearl")
                            })
                        })
                        addProperty("rolls", 3)
                    })
                }
            } + includedVanillaJSONObjectFileMutated("minecraft/worldgen/placed_feature/monster_room.json") {
                getAsJsonArray("placement").first().asJsonObject.addProperty("count", 16)
            } + includedVanillaJSONObjectFileMutated("minecraft/worldgen/placed_feature/monster_room_deep.json") {
                getAsJsonArray("placement").first().asJsonObject.addProperty("count", 10)
            } + includedVanillaJSONObjectFileMutated("minecraft/worldgen/placed_feature/amethyst_geode.json") {
                getAsJsonArray("placement").first().asJsonObject.addProperty("chance", 15)
            } + listOf(
                "frozen_peaks",
                "jagged_peaks",
                "snowy_slopes"
            ).map { name ->
                includedVanillaJSONObjectFileMutated("minecraft/worldgen/biome/$name.json") {
                    getAsJsonArray("features").get(4).asJsonArray
                        .insert(0, JsonPrimitive("sucraft:trees_frost_high_elevation"))
                }
            } + includedVanillaJSONObjectFileMutated("minecraft/worldgen/biome/ice_spikes.json") {
                getAsJsonArray("features").apply {
                    forEach {
                        it.takeIf { it.isJsonArray }?.asJsonArray?.run {
                            if (contains(JsonPrimitive("minecraft:ice_spike"))) {
                                insert(0, JsonPrimitive("sucraft:trees_frost_ice_spikes"))
                            }
                        }
                    }
                }
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/biome/ice_spikes.json",
                "sucraft/worldgen/biome/frost_forest.json"
            ) {
                getAsJsonArray("features").apply {
                    forEach {
                        it.takeIf { it.isJsonArray }?.asJsonArray?.run {
                            if (contains(JsonPrimitive("minecraft:ice_spike"))) {
                                set(
                                    indexOf(JsonPrimitive("minecraft:ice_spike")),
                                    JsonPrimitive("sucraft:ice_spike_frost_forest")
                                )
                                insert(0, JsonPrimitive("sucraft:trees_frost_forest"))
                            } else if (contains(JsonPrimitive("minecraft:monster_room"))) {
                                insert(0, JsonPrimitive("minecraft:fossil_upper"))
                                insert(1, JsonPrimitive("minecraft:fossil_lower"))
                            }
                        }
                    }
                }
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/placed_feature/ice_spike.json",
                "sucraft/worldgen/placed_feature/ice_spike_frost_forest.json"
            ) {
                getAsJsonArray("placement").add(JsonObject().apply {
                    addProperty("type", "minecraft:rarity_filter")
                    addProperty("chance", 32)
                })
            } + IncludedVanillaFile(
                "minecraft/worldgen/material_rule/overworld/biome_surface/ice_spikes.json",
                "sucraft/worldgen/material_rule/overworld/biome_surface/frost_forest.json"
            ) + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/biome/dappled_forest.json",
                "sucraft/worldgen/biome/orange_forest.json"
            ) {
                addProperty("temperature", get("temperature").asDouble + 0.1)
                getAsJsonObject("effects").apply {
                    remove("foliage_color")
                    addProperty("grass_color", "#a34d0f")
                }
                getAsJsonArray("features").apply {
                    forEach {
                        it.takeIf { it.isJsonArray }?.asJsonArray?.run {
                            if (contains(JsonPrimitive("minecraft:trees_dappled_forest"))) {
                                set(
                                    indexOf(JsonPrimitive("minecraft:trees_dappled_forest")),
                                    JsonPrimitive("sucraft:trees_orange_forest")
                                )
                            }
                            if (contains(JsonPrimitive("minecraft:patch_grass_forest"))) {
                                set(
                                    indexOf(JsonPrimitive("minecraft:patch_grass_forest")),
                                    JsonPrimitive("sucraft:patch_grass_orange_forest")
                                )
                                add("minecraft:patch_tall_grass_2")
                                add("minecraft:patch_large_fern")
                                add("minecraft:patch_pumpkin")
                                add("minecraft:patch_berry_rare")
                                add("sucraft:patch_dead_bush_orange_forest")
                                add("sucraft:patch_fern_orange_forest")
                            }
                        }
                    }
                }
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/placed_feature/patch_grass_forest.json",
                "sucraft/worldgen/placed_feature/patch_grass_orange_forest.json"
            ) {
                getAsJsonArray("placement")
                    .first { it.asJsonObject.get("type").asString == "minecraft:count" }
                    .asJsonObject.addProperty("count", 12)
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/placed_feature/patch_grass_forest.json",
                "sucraft/worldgen/placed_feature/patch_fern_orange_forest.json"
            ) {
                addProperty("feature", "sucraft:fern")
                getAsJsonArray("placement")
                    .first { it.asJsonObject.get("type").asString == "minecraft:count" }
                    .asJsonObject.addProperty("count", 4)
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/placed_feature/patch_dead_bush_2.json",
                "sucraft/worldgen/placed_feature/patch_dead_bush_orange_forest.json"
            ) {
                getAsJsonArray("placement")
                    .first { it.asJsonObject.get("type").asString == "minecraft:count" }
                    .asJsonObject.addProperty("count", 8)
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/placed_feature/trees_dappled_forest.json",
                "sucraft/worldgen/placed_feature/trees_orange_forest.json"
            ) {
                addProperty("feature", "sucraft:trees_orange_forest")
                getAsJsonArray("placement")
                    .first { it.asJsonObject.get("type").asString == "minecraft:count" }
                    .asJsonObject.addProperty("count", 13)
            } + listOf(
                "red", "orange", "yellow"
            ).flatMap { color ->
                listOf(
                    includedVanillaJSONObjectFileMutated(
                        "minecraft/worldgen/feature/fancy_oak_bees_0002_leaf_litter.json",
                        "sucraft/worldgen/feature/fancy_${color}_maple.json"
                    ) {
                        getAsJsonObject("foliage_provider").apply {
                            addProperty("id", "sucraft:${color}_maple_leaves")
                        }
                    },
                    includedVanillaJSONObjectFileMutated(
                        "minecraft/worldgen/placed_feature/fancy_oak_bees_0002_leaf_litter.json",
                        "sucraft/worldgen/placed_feature/fancy_${color}_maple.json"
                    ) {
                        addProperty("feature", "sucraft:fancy_${color}_maple")
                        getAsJsonArray("placement").apply {
                            first().asJsonObject.getAsJsonObject("predicate").apply {
                                addProperty("state", "sucraft:${color}_maple_sapling")
                            }
                        }
                    }
                )
            } + includedVanillaJSONObjectFileMutated("minecraft/worldgen/biome/sunflower_plains.json") {
                getAsJsonArray("features").map { it.asJsonArray }
                    .first { it.contains(JsonPrimitive("minecraft:patch_sunflower")) }.apply {
                        add(JsonPrimitive("sucraft:patch_sunflower_very_sparse"))
                        add(JsonPrimitive("sucraft:patch_sunflower_sparse"))
                        add(JsonPrimitive("sucraft:patch_sunflower_mid_dense"))
                        add(JsonPrimitive("sucraft:patch_sunflower_dense"))
                    }
            } + includedVanillaJSONObjectFileMutated("minecraft/worldgen/placed_feature/patch_sunflower.json") {
                getAsJsonArray("placement").apply {
                    first { it.asJsonObject.get("type").asString == "minecraft:rarity_filter" }
                        .asJsonObject.addProperty("chance", 2)
                }
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/placed_feature/patch_sunflower.json",
                "sucraft/worldgen/placed_feature/patch_sunflower_sparse.json"
            ) {
                getAsJsonArray("placement").apply {
                    first { it.asJsonObject.get("type").asString == "minecraft:rarity_filter" }
                        .asJsonObject.addProperty("chance", 1)
                    first { it.asJsonObject.get("type").asString == "minecraft:count" }
                        .asJsonObject.addProperty("count", 28)
                    first { it.asJsonObject.get("type").asString == "minecraft:offset" }
                        .asJsonObject.apply {
                            listOf("x", "y", "z").forEach { dimension ->
                                getAsJsonObject(dimension).apply {
                                    addProperty("min", (get("min").asInt * 5 / 4).coerceAtLeast(-16))
                                    addProperty("max", (get("max").asInt * 5 / 4).coerceAtMost(16))
                                }
                            }
                        }
                    removeAll { it.asJsonObject.get("type").asString == "minecraft:biome" }
                }
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/placed_feature/patch_sunflower.json",
                "sucraft/worldgen/placed_feature/patch_sunflower_very_sparse.json"
            ) {
                getAsJsonArray("placement").apply {
                    first { it.asJsonObject.get("type").asString == "minecraft:rarity_filter" }
                        .asJsonObject.addProperty("chance", 1)
                    first { it.asJsonObject.get("type").asString == "minecraft:count" }
                        .asJsonObject.addProperty("count", 13)
                    first { it.asJsonObject.get("type").asString == "minecraft:offset" }
                        .asJsonObject.apply {
                            listOf("x", "y", "z").forEach { dimension ->
                                getAsJsonObject(dimension).apply {
                                    addProperty("min", (get("min").asInt * 5 / 2).coerceAtLeast(-16))
                                    addProperty("max", (get("max").asInt * 5 / 2).coerceAtMost(16))
                                }
                            }
                        }
                    removeAll { it.asJsonObject.get("type").asString == "minecraft:biome" }
                }
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/placed_feature/patch_sunflower.json",
                "sucraft/worldgen/placed_feature/patch_sunflower_mid_dense.json"
            ) {
                getAsJsonArray("placement").apply {
                    first { it.asJsonObject.get("type").asString == "minecraft:rarity_filter" }
                        .asJsonObject.addProperty("chance", 3)
                    first { it.asJsonObject.get("type").asString == "minecraft:count" }
                        .asJsonObject.addProperty("count", 44)
                }
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/placed_feature/patch_sunflower.json",
                "sucraft/worldgen/placed_feature/patch_sunflower_dense.json"
            ) {
                getAsJsonArray("placement").apply {
                    first { it.asJsonObject.get("type").asString == "minecraft:rarity_filter" }
                        .asJsonObject.addProperty("chance", 3)
                    first { it.asJsonObject.get("type").asString == "minecraft:count" }
                        .asJsonObject.addProperty("count", 95)
                    first { it.asJsonObject.get("type").asString == "minecraft:offset" }
                        .asJsonObject.apply {
                            listOf("x", "y", "z").forEach { dimension ->
                                getAsJsonObject(dimension).apply {
                                    addProperty("min", (get("min").asInt * 4 / 5).coerceAtLeast(-16))
                                    addProperty("max", (get("max").asInt * 4 / 5).coerceAtMost(16))
                                }
                            }
                        }
                }
            } + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/biome/plains.json",
                "sucraft/worldgen/biome/lavender_plains.json"
            ) {
                addProperty("temperature", get("temperature").asDouble + 0.05)
                getAsJsonArray("features").map { it.asJsonArray }.apply {
                    forEach {
                        it.remove(JsonPrimitive("minecraft:lake_lava_surface"))
                        if (it.contains(JsonPrimitive("minecraft:flower_plains"))) {
                            it.removeAll { true }
                            it.add("minecraft:glow_lichen")
                            it.add("minecraft:patch_firefly_bush_near_water")
                            it.add("sucraft:lavender_flowers_tall_patch")
                            it.add("sucraft:lavender_flowers_dense_cover")
                            it.add("sucraft:lavender_flowers_sparse_cover")
                        }
                    }
                }
            } + includedVanillaJSONObjectFileMutated("minecraft/worldgen/material_rule/overworld/biome_surface.json") {
                getAsJsonArray("sequence").apply {
                    insert(0, JsonObject().apply {
                        addProperty("type", "minecraft:condition")
                        add("if_true", JsonObject().apply {
                            addProperty("type", "minecraft:biome")
                            addProperty("biome_is", "sucraft:frost_forest")
                        })
                        addProperty("then_run", "sucraft:overworld/biome_surface/frost_forest")
                    })
                    insert(0, JsonObject().apply {
                        addProperty("type", "minecraft:condition")
                        add("if_true", JsonObject().apply {
                            addProperty("type", "minecraft:biome")
                            addProperty("biome_is", "sucraft:orange_forest")
                        })
                        addProperty("then_run", "sucraft:overworld/biome_surface/orange_forest")
                    })
                }
            } + includedVanillaJSONObjectFileMutated("minecraft/worldgen/density_function/overworld/preliminary_surface_level.json") {
                getAsJsonObject("density").getAsJsonObject("left").apply {
                    getAsJsonObject("second").getAsJsonObject("alpha").apply {
                        addProperty("from_coordinate", 270)
                        addProperty("to_coordinate", 296)
                    }
                }
            } + includedVanillaJSONObjectFileMutated("minecraft/worldgen/density_function/overworld/final_density.json") {
                getAsJsonObject("left").getAsJsonObject("left")
                    .getAsJsonObject("input").getAsJsonObject("input")
                    .getAsJsonObject("left").getAsJsonObject("input")
                    .getAsJsonObject("second").getAsJsonObject("alpha").apply {
                        addProperty("from_coordinate", 270)
                        addProperty("to_coordinate", 296)
                    }
            }/* + includedVanillaJSONObjectFileMutated(
                "minecraft/worldgen/density_function/overworld/factor.json"
            ) {
                fun multiplyValues(obj: JsonElement, factor: Double) {
                    if (obj.isJsonObject) {
                        obj.asJsonObject.keySet().forEach { key ->
                            val value = obj.asJsonObject.get(key)
                            if (value.isJsonObject) {
                                multiplyValues(value, factor)
                            } else if (value.isJsonArray) {
                                multiplyValues(value, factor)
                            } else if (value.isJsonPrimitive && key == "value") {
                                if (value.asJsonPrimitive.isNumber) {
                                    obj.asJsonObject.addProperty(key, value.asJsonPrimitive.asDouble * factor)
                                }
                            }
                        }
                    } else if (obj.isJsonArray) {
                        obj.asJsonArray.forEach { multiplyValues(it, factor) }
                    }
                }

                val points = getAsJsonObject("input").getAsJsonObject("second")
                    .getAsJsonObject("spline").getAsJsonArray("points")
                for (i in 2 until points.size()) {
                    multiplyValues(points[i], 0.85)
                }
            }*/
        )
    }
}

tasks.named("jar") {
    dependsOn(extractVanillaAssets)
    dependsOn(extractVanillaData)
}
tasks.named("shadowJar") {
    dependsOn(extractVanillaAssets)
    dependsOn(extractVanillaData)
}
