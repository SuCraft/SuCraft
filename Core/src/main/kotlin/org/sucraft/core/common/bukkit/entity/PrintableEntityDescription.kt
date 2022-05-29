/*
 * Copyright (c) SuCraft 2021 sucraft.org
 */

package org.sucraft.core.common.bukkit.entity

import org.bukkit.entity.*
import org.bukkit.projectiles.BlockProjectileSource
import org.sucraft.core.common.bukkit.item.ItemNBTUtils

object PrintableEntityDescription {

    private fun getShortAndLongBuilderCommonBegin(entity: Entity): StringBuilder {

        // Initialize the builder
        val builder = StringBuilder("")

        // Basic prefix for players and entities
        if (entity is Player) {
            builder.append("Player{")
            builder.append(entity.name)
        } else {
            builder.append("Entity{")
            builder.append(entity.type.name)
        }
        builder.append(' ')

        // Whether the entity is valid
        if (!entity.isValid) {
            builder.append("(NOT VALID!)")
            builder.append(' ')
        }

        // Return the builder
        return builder

    }

    private fun addUUIDAndLocation(entity: Entity, builder: StringBuilder) {

        // The entity's UUID
        builder.append("{${entity.uniqueId}")
        builder.append(' ')

        // The entity's location
        builder.append("at ${entity.location}")

    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun getShort(entity: Entity): String {

        // Initialize the builder
        val builder = getShortAndLongBuilderCommonBegin(entity)

        // The entity's UUID and location
        addUUIDAndLocation(entity, builder)

        // Prefix
        builder.append('}')

        // Build the result
        return builder.toString()

    }

    fun get(entity: Entity, doNotPrintOtherEntities: Boolean = false): String {

        // Initialize the builder
        val builder = getShortAndLongBuilderCommonBegin(entity)

        // Whether the entity is dead
        if (entity.isDead) {
            builder.append("(DEAD!)")
            builder.append(' ')
        }

        // The entity's UUID anad location
        addUUIDAndLocation(entity, builder)

        // Whether the entity is invulnerable
        if (entity.isInvulnerable) {
            builder.append("(INVULNERABLE)")
            builder.append(' ')
        }

        // The non-player entity's custom name
        if (entity !is Player) {
            @Suppress("Deprecation")
            if (entity.customName != null) {
                builder.append("(custom name: '${entity.customName}')")
                builder.append(' ')
            }
        }

        // The armor stand's equipment
        if (entity is ArmorStand) {
            builder.append("(equipment: ${entity.equipment})")
            builder.append(' ')
        }

        // The arrow's item
        if (entity is AbstractArrow) {
            builder.append("(item: ${ItemNBTUtils.getAmountAndTypeAndFullNBTString(entity.itemStack)})")
            builder.append(' ')
        }

        // The boat's wood type
        if (entity is Boat) {
            builder.append("(wood type: ${entity.woodType})")
            builder.append(' ')
        }

        // The cat's type and collar color
        if (entity is Cat) {
            builder.append("(type: ${entity.catType}, collar color: ${entity.collarColor})")
            builder.append(' ')
        }

        // The creeper's powered state
        if (entity is Creeper) {
            builder.append("(powered: ${entity.isPowered})")
            builder.append(' ')
        }

        // The fox's type
        if (entity is Fox) {
            builder.append("(type: ${entity.foxType})")
            builder.append(' ')
        }

        // The horse's color and style
        if (entity is Horse) {
            builder.append("(color: ${entity.color}, style: ${entity.style})")
            builder.append(' ')
        }

        // The dropped item's contained item
        if (entity is Item) {
            builder.append("(item: ${ItemNBTUtils.getAmountAndTypeAndFullNBTString(entity.itemStack)})")
            builder.append(' ')
        }

        // The item frame's item
        if (entity is ItemFrame) {
            builder.append("(item: ${ItemNBTUtils.getAmountAndTypeAndFullNBTString(entity.item)})")
            builder.append(' ')
        }

        // The llama's color
        if (entity is Llama) {
            builder.append("(color: ${entity.color})")
            builder.append(' ')
        }

        // The mooshroom's variant
        if (entity is MushroomCow) {
            builder.append("(variant: ${entity.variant})")
            builder.append(' ')
        }

        // The ocelot's type
        if (entity is Ocelot) {
            builder.append("(type: ${entity.catType})")
            builder.append(' ')
        }

        // The panda's genes
        if (entity is Panda) {
            builder.append("(main gene: ${entity.mainGene}, hidden gene: ${entity.hiddenGene})")
            builder.append(' ')
        }

        // The parrot's variant
        if (entity is Parrot) {
            builder.append("(variant: ${entity.variant})")
            builder.append(' ')
        }

        // The projectile's shooter
        if (entity is Projectile) {
            builder.append("(shooter: ${entity.shooter?.let { shooter ->
                if (shooter is Entity) {
                    return@let if (doNotPrintOtherEntities) getShort(shooter) else get(shooter, true)
                } else if (shooter is BlockProjectileSource) {
                    return@let "block of type ${shooter.block.type}"
                }
                "<shooter that is not an entity or block>"
            } ?: "<no shooter>"})")
            builder.append(' ')
        }

        // The rabbit's type
        if (entity is Rabbit) {
            builder.append("(type: ${entity.type})")
            builder.append(' ')
        }

        // The sheep's color and sheared status
        if (entity is Sheep) {
            builder.append("(color: ${entity.color ?: "<no color>"}, sheared: ${entity.isSheared})")
            builder.append(' ')
        }

        // The slime's size
        if (entity is Slime) {
            builder.append("(size: ${entity.size})")
            builder.append(' ')
        }

        // Whether this entity is saddled
        if (entity is Steerable) {
            builder.append("(saddled: ${entity.hasSaddle()})")
            builder.append(' ')
        }

        // The thrown potion's contained item
        if (entity is ThrownPotion) {
            builder.append("(item: ${ItemNBTUtils.getAmountAndTypeAndFullNBTString(entity.item)})")
            builder.append(' ')
        }

        // The primed TNT's source
        if (entity is TNTPrimed) {
            builder.append("(source: ${entity.source?.let { if (doNotPrintOtherEntities) getShort(it) else get(it, true) } ?: "<no source>"})")
            builder.append(' ')
        }

        // The tropical fish's color and pattern
        if (entity is TropicalFish) {
            builder.append("(body color: ${entity.patternColor}, pattern: ${entity.pattern}, pattern color: ${entity.bodyColor})")
            builder.append(' ')
        }

        // The villager's profession, type and level
        if (entity is Villager) {
            builder.append("(profession: ${entity.profession}, type: ${entity.villagerType}, level: ${entity.villagerLevel})")
            builder.append(' ')
        }

        // The wolf's angry state and collar color
        if (entity is Wolf) {
            builder.append("(angry: ${entity.isAngry}, collar color: ${entity.collarColor})")
            builder.append(' ')
        }

        // The zombie villager's profession and type
        if (entity is ZombieVillager) {
            builder.append("(profession: ${entity.villagerProfession}, type: ${entity.villagerType})")
            builder.append(' ')
        }

        // The vehicle the entity is in
        if (entity.isInsideVehicle) {
            builder.append("(vehicle: ${entity.vehicle!!.type} {${entity.vehicle!!.uniqueId})")
            builder.append(' ')
        }

        // The passengers this entity is carrying
        if (entity.passengers.isNotEmpty()) {
            builder.append("(passengers: ${entity.passengers.asSequence().map { if (doNotPrintOtherEntities) getShort(it) else get(it, true) }})")
            builder.append(' ')
        }

        // The leash holder of this entity
        if (entity is LivingEntity) {
            if (entity.isLeashed) {
                builder.append("(leash holder: ${if (doNotPrintOtherEntities) getShort(entity.leashHolder) else get(entity.leashHolder, true)})")
                builder.append(' ')
            }
        }

        // The owner of this entity
        // TODO enable when added
        /*
        val nonBlockThingOwner: Optional<PlayerIdentifier> = StoredInformationManager.instance().getGeneralOwner(entity)
        if (nonBlockThingOwner.isPresent()) {
            builder.append("(non-block thing owner: " + nonBlockThingOwner.get().getNameOrUUIDString(false).toString() + ")")
            builder.append(' ')
        }
         */

        // Prefix
        if (builder[builder.length - 1] == ' ') {
            builder.setCharAt(builder.length - 1, '}')
        } else {
            builder.append('}')
        }

        // Build the result
        return builder.toString()

    }

}