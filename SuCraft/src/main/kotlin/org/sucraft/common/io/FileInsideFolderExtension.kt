/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.io

import java.io.File
import java.nio.file.Path

infix fun String.inside(folder: File): File = Path.of(folder.path, this).toFile()