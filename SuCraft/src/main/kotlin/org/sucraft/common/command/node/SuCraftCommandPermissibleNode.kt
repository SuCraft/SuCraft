/*
 * Copyright (c) SuCraft 2022 sucraft.org
 */

package org.sucraft.common.command.node

import org.sucraft.common.permission.SuCraftPermission

/**
 * @param P The type returned from the [withPermission] method and its derived methods.
 * @param E The type returned from the [executesInternally] method and its derived methods.
 */
interface SuCraftCommandPermissibleNode<P : SuCraftCommandPermissibleNode<P, E>,
		E : SuCraftCommandNode<E>> : SuCraftCommandNode<E> {

	fun withPermission(permission: SuCraftPermission): P

}