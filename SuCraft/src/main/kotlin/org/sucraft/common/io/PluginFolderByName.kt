/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.io

import java.io.File

val pluginsFolder get() = File("""plugins""")

fun getPluginFolder(pluginName: String) = pluginName inside pluginsFolder