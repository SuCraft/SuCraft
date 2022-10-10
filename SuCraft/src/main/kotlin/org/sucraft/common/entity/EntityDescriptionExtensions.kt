/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.common.entity

import io.papermc.paper.entity.CollarColorable
import org.bukkit.entity.*
import org.bukkit.material.Colorable
import org.bukkit.projectiles.BlockProjectileSource
import org.sucraft.common.block.description
import org.sucraft.common.collection.arrayOfNotNull
import org.sucraft.common.itemstack.amountAndTypeAndNBT
import org.sucraft.common.location.LocationCoordinates.Companion.getCoordinates
import org.sucraft.modules.harmlessentities.HarmlessEntities
import org.sucraft.modules.harmlessentities.isHarmless
import org.sucraft.modules.harmlessentities.isTransitivelyHarmless

private val Entity.shortDescriptionMapEntries
	get() = listOfNotNull(
		(this as? Player)?.run { "playerName" to name },
		"entityType" to type,
		"valid" to isValid,
		"dead" to isDead,
		"uuid" to uniqueId,
		"location" to location.getCoordinates()
	)

val Entity.shortDescriptionMap
	get() = shortDescriptionMapEntries.toMap()

val Entity.shortDescription
	get() = "${if (this is Player) "Player" else "Entity"}{$shortDescriptionMap}"

private fun Entity.getLongDescriptionMapEntries(printSubEntitiesAsShort: Boolean) =
	shortDescriptionMapEntries + listOfNotNull(
		"invulnerable" to isInvulnerable,
		takeIf { this !is Player }?.run { "customName" to customName() },
		(this as? ArmorStand)?.run { "equipment" to equipment },
		(this as? AbstractArrow)?.run { "arrowItem" to itemStack.amountAndTypeAndNBT },
		(this as? Boat)?.run { "woodType" to boatType },
		(this as? Cat)?.run { "catType" to catType },
		(this as? CollarColorable)?.run { "collarColor" to collarColor },
		(this as? Colorable)?.run { "color" to color },
		(this as? Creeper)?.run { "charged" to isPowered },
		(this as? Fox)?.run { "foxType" to foxType },
		*(this as? Horse)?.run {
			arrayOf(
				"horseColor" to color,
				"horseStyle" to style
			)
		} ?: emptyArray(),
		(this as? Item)?.run { "itemStack" to itemStack.amountAndTypeAndNBT },
		(this as? ItemFrame)?.run { "itemStack" to item.amountAndTypeAndNBT },
		(this as? Llama)?.run { "llamaColor" to color },
		(this as? MushroomCow)?.run { "mooshroomVariant" to variant },
		(this as? Ocelot)?.run { "catType" to catType },
		*(this as? Panda)?.run {
			arrayOf(
				"mainGene" to mainGene,
				"hiddenGene" to hiddenGene
			)
		} ?: emptyArray(),
		(this as? Parrot)?.run { "parrotVariant" to variant },
		(this as? Projectile)?.run {
			"shooter" to shooter?.run {
				(this as? Entity)?.run { getDescription(printSubEntitiesAsShort, true) }
					?: (this as? BlockProjectileSource)?.run { block.description }
					?: "<shooter that is not an entity or block>"
			}
		},
		(this as? Rabbit)?.run { "rabbitType" to type },
		(this as? Sheep)?.run { "isSheared" to isSheared },
		(this as? Slime)?.run { "size" to size },
		(this as? Steerable)?.run { "hasSaddle" to hasSaddle() },
		(this as? ThrownPotion)?.run { "thrownPotionItem" to item.amountAndTypeAndNBT },
		(this as? TNTPrimed)?.run {
			"source" to source?.getDescription(printSubEntitiesAsShort, true)
		},
		*(this as? TropicalFish)?.run {
			arrayOf(
				"bodyColor" to bodyColor,
				"pattern" to pattern,
				"patternColor" to patternColor
			)
		} ?: emptyArray(),
		*(this as? Villager)?.run {
			arrayOf(
				"profession" to profession,
				"villagerType" to villagerType,
				"villagerLevel" to villagerLevel
			)
		} ?: emptyArray(),
		(this as? Wolf)?.run { "isAngry" to isAngry },
		*(this as? ZombieVillager)?.run {
			arrayOf(
				"profession" to villagerProfession,
				"villagerType" to villagerType
			)
		} ?: emptyArray(),
		"vehicle" to takeIf { isInsideVehicle }
			?.vehicle?.getDescription(printSubEntitiesAsShort, true),
		"passengers" to passengers.map { it.getDescription(printSubEntitiesAsShort, true) },
		*(this as? LivingEntity)?.run {
			arrayOfNotNull(
				takeIf { isLeashed }?.run {
					"leashHolder" to leashHolder.getDescription(printSubEntitiesAsShort, true)
				},
				*takeIf { HarmlessEntities.hasInitializationFinished }?.run {
					arrayOf(
						"isHarmless" to isHarmless,
						"isTransitivelyHarmless" to isTransitivelyHarmless
					)
				} ?: emptyArray()
			)
		} ?: emptyArray()
	)

fun Entity.getLongDescriptionMap(printSubEntitiesAsShort: Boolean): Map<String, Any?> =
	getLongDescriptionMapEntries(printSubEntitiesAsShort).toMap()

val Entity.longDescriptionMap
	get() = getLongDescriptionMap(false)

fun Entity.getLongDescription(printSubEntitiesAsShort: Boolean) =
	"${if (this is Player) "Player" else "Entity"}{${getLongDescriptionMap(printSubEntitiesAsShort)}}"

val Entity.longDescription
	get() = getLongDescription(false)

fun Entity.getDescription(short: Boolean) =
	if (short) shortDescription else longDescription

fun Entity.getDescription(short: Boolean, printSubEntitiesAsShort: Boolean) =
	if (short) shortDescription else getLongDescription(printSubEntitiesAsShort)

// The owner of this entity
// TODO enable when added
/*
val nonBlockThingOwner: Optional<PlayerIdentifier> = StoredInformationManager.instance().getGeneralOwner(entity)
if (nonBlockThingOwner.isPresent()) {
	builder.append("(non-block thing owner: " + nonBlockThingOwner.get().getNameOrUUIDString(false).toString() + ")")
	builder.append(' ')
}
*/