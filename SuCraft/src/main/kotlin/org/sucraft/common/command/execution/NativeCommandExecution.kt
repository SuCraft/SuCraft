/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.execution

typealias SyncSuCraftCommandExecutor<Callee> = Callee.(SuCraftCommandContext<Callee>) -> Unit

typealias AsyncSuCraftCommandExecutor<Callee> = suspend Callee.(SuCraftCommandContext<Callee>) -> Unit