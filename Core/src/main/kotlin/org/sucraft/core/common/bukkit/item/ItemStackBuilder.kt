package org.sucraft.core.common.bukkit.item

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.*
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*
import org.bukkit.persistence.PersistentDataType
import org.bukkit.potion.PotionData
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionType
import org.sucraft.core.common.bukkit.persistentdata.PersistentDataShortcuts
import org.sucraft.core.common.bukkit.potion.PotionUtils
import org.sucraft.core.common.general.string.StringSplit
import org.sucraft.core.common.sucraft.player.PlayerUUID
import java.util.*
import java.util.function.Function


class ItemStackBuilder private constructor(private var itemStack: ItemStack) {

	init {
		require(itemStack.type != Material.AIR) { "ItemStackBuilder itemStack type cannot be air" }
		itemStack = itemStack.clone()
	}

	val type get() = itemStack.type

	fun setAmount(amount: Int) = also { this.itemStack.amount = amount }

	val amount get() = itemStack.amount

	private fun applyToMeta(metaConsumer: (ItemMeta) -> Unit) = also {
		GuaranteedItemMetaGetter.get(itemStack).also(metaConsumer).also(itemStack::setItemMeta)
	}

	private fun <T> applyToMeta(metaType: Class<T>, metaConsumer: (T) -> Unit) =
		applyToMeta { if (metaType.isInstance(it)) (it as T)?.let(metaConsumer) }

	private fun <V> getFromMeta(metaFunction: (ItemMeta) -> V) =
		GuaranteedItemMetaGetter.get(itemStack).let(metaFunction)

	/**
	 * @return null if the meta type does not match
	 */
	private fun <T, V> getFromMeta(metaType: Class<T>, metaFunction: (T) -> V?): V? = getFromMeta {
		if (!metaType.isInstance(it)) null else metaFunction(it as T)
	}

	fun setCustomModelData(customModelData: Int?) =
		applyToMeta { it.setCustomModelData(customModelData) }

	val customModelData get() =
		getFromMeta { if (!it.hasCustomModelData()) null else it.customModelData }

	fun setDamage(damage: Int) =
		applyToMeta(Damageable::class.java) { it.damage = damage }

	val damage get() =
		getFromMeta(Damageable::class.java) { it.damage }

	fun addItemFlags(vararg flags: ItemFlag) =
		applyToMeta { it.addItemFlags(*flags) }

	val itemFlags get() =
		getFromMeta { Collections.unmodifiableSet(it.itemFlags) }

	fun hasItemFlag(itemFlag: ItemFlag) =
		getFromMeta { it.hasItemFlag(itemFlag) }

	fun hideNoPotionEffects() = also {
		val meta: ItemMeta = GuaranteedItemMetaGetter.get(itemStack)
		if (meta is PotionMeta && !PotionUtils.hasNonBasePotionEffect(meta)) {
			addItemFlags(ItemFlag.HIDE_POTION_EFFECTS)
		}
	}

	@Deprecated("")
	fun setDisplayName(name: String?) =
		applyToMeta { it.setDisplayName(name) }

	fun setDisplayNameComponent(name: Component?) =
		setDisplayNameComponent(true, name)

	fun setDisplayNameComponent(removeItalic: Boolean, name: Component?) =
		applyToMeta { it.displayName(if (name == null || !removeItalic) name else name.decoration(TextDecoration.ITALIC, false)) }

	@get:Deprecated("")
	val displayName	get() =
		getFromMeta { if (it.hasDisplayName()) it.getDisplayName() else null }

	val displayNameComponent get() =
		getFromMeta { if (it.hasDisplayName()) it.displayName() else null }

	fun hasDisplayName() =
		getFromMeta { it.hasDisplayName() }

	@Deprecated("")
	fun setLore(lore: List<String?>?) = applyToMeta { it.lore = lore }

	fun setLore(vararg lore: String?) = setLore(listOf(*lore))

	fun setLore(loreText: String, lorePrefix: String, loreMaxPartLength: Int) =
		setLore(StringSplit.cutStringIntoParts(loreText, lorePrefix, null, loreMaxPartLength))

	fun setLoreComponent(removeItalic: Boolean = true, lore: List<Component>?) =
		applyToMeta { it.lore(if (lore == null || !removeItalic) lore else lore.map { it.decoration(TextDecoration.ITALIC, false) }) }

	fun setLoreComponent(removeItalic: Boolean = true, vararg lore: Component) =
		setLoreComponent(removeItalic, listOf(*lore))

	@Deprecated("")
	fun addLore(lore: List<String?>) =
		applyToMeta { it.setLore((it.getLore() ?: ArrayList()).also { it.addAll(lore) }) }

	@Deprecated("")
	fun addLore(vararg lore: String): ItemStackBuilder {
		return addLore(listOf(*lore))
	}

	@Deprecated("")
	fun addLore(loreText: String, lorePrefix: String, loreMaxPartLength: Int): ItemStackBuilder {
		return addLore(StringSplit.cutStringIntoParts(loreText, lorePrefix, null, loreMaxPartLength))
	}

	@Deprecated("")
	fun addLoreWithPossibleSeparatorLine(lore: List<String>) =
		applyToMeta { meta ->
			val existingLore = meta.getLore() ?: ArrayList()
			var hasInformation =
				existingLore.isNotEmpty() ||
						(!meta.enchants.isEmpty() && !meta.itemFlags.contains(ItemFlag.HIDE_ENCHANTS)) ||
						(meta is PotionMeta && PotionUtils.hasNonBasePotionEffect(meta as PotionMeta) && !meta.getItemFlags().contains(ItemFlag.HIDE_POTION_EFFECTS))
			if (hasInformation && lore.isNotEmpty() && lore[0].isNotBlank() && (existingLore.isEmpty() || existingLore[existingLore.size - 1].isNotBlank())) {
				existingLore.add("")
			}
			existingLore.addAll(lore)
			meta.setLore(existingLore)
		}

	@Deprecated("")
	fun addLoreWithPossibleSeparatorLine(vararg lore: String) =
		addLoreWithPossibleSeparatorLine(listOf(*lore))

	@Deprecated("")
	fun addLoreWithPossibleSeparatorLine(loreText: String, lorePrefix: String, loreMaxPartLength: Int) =
		addLoreWithPossibleSeparatorLine(StringSplit.cutStringIntoParts(loreText, lorePrefix, null, loreMaxPartLength))

	fun addLoreComponent(removeItalic: Boolean = true, lore: List<Component>?): ItemStackBuilder {
		if (lore == null) {
			return this
		}
		val newLore: MutableList<Component> = ArrayList(loreComponent)
		newLore.addAll(if (!removeItalic) lore else lore.map { component: Component -> component.decoration(TextDecoration.ITALIC, false) })
		return setLoreComponent(false, newLore)
	}

	fun addLoreComponent(removeItalic: Boolean = true, vararg lore: Component) =
		addLoreComponent(removeItalic, listOf(*lore))

	fun addLoreComponent(unformattedString: String, componentPostprocessing: ((Component) -> Component)?, loreMaxLineLength: Int) =
		addLoreComponent(
			lore = StringSplit.cutStringIntoParts(unformattedString, null, null, loreMaxLineLength)
				.map(Component::text)
				.map(componentPostprocessing ?: { it })
		)

	@get:Deprecated("")
	val lore
		get() = Collections.unmodifiableList(getFromMeta { it.getLore() } ?: emptyList())

	val loreComponent
		get() = Collections.unmodifiableList(getFromMeta { it.lore() } ?: emptyList())

	fun setLeatherColor(color: Color?) =
		applyToMeta(LeatherArmorMeta::class.java) { it.setColor(color) }

	val leatherColor
		get() = getFromMeta(LeatherArmorMeta::class.java) { it.getColor() }

	fun setSkullOwner(skullOwner: OfflinePlayer?) =
		applyToMeta(SkullMeta::class.java) { it.owningPlayer = skullOwner }

	val skullOwner
		get() = getFromMeta(SkullMeta::class.java) { it.owningPlayer }

	/**
	 * @param textures b64 string textures
	 */
	fun setSkullTextures(textures: String) =
		applyToMeta(SkullMeta::class.java) {
			val profile: PlayerProfile = Bukkit.createProfile(UUID.randomUUID())
			val property = ProfileProperty("textures", textures)
			profile.setProperty(property)
			it.playerProfile = profile
		}

	fun setBookTitle(title: String?) =
		applyToMeta(BookMeta::class.java) { it.title = title }

	val bookTitle
		get() = getFromMeta(BookMeta::class.java) { it.title }

	fun setBookAuthor(author: String?) =
		applyToMeta(BookMeta::class.java) { it.author = author }

	val bookAuthor
		get() = getFromMeta(BookMeta::class.java) { it.author }

	@Deprecated("")
	fun setBookPage(pageNumber: Int, page: String) =
		applyToMeta<BookMeta>(BookMeta::class.java) { it.setPage(pageNumber, page) }

	@Deprecated("")
	fun setBookPages(pages: List<String?>) =
		applyToMeta(BookMeta::class.java) { meta: BookMeta -> meta.setPages(pages) }

	@Deprecated("")
	fun setBookPages(vararg pages: String) =
		applyToMeta(BookMeta::class.java) { it.setPages(*pages) }

	@get:Deprecated("")
	val bookPages
		get() = getFromMeta(BookMeta::class.java) { Collections.unmodifiableList(it.getPages()) }

	fun addEnchantment(enchantmentAndLevel: Pair<Enchantment, Int>) =
		addEnchantment(enchantmentAndLevel.first, enchantmentAndLevel.second)

	fun addEnchantment(enchantment: Enchantment, level: Int) =
		also { itemStack.addUnsafeEnchantment(enchantment, level) }

	val enchantments get() = Collections.unmodifiableMap(itemStack.enchantments)

	fun setPotionColor(color: Color?) =
		applyToMeta(PotionMeta::class.java) { it.color = color }

	val potionColor get() =
		getFromMeta(PotionMeta::class.java) { it.color }

	fun setBasePotionData(basePotionData: PotionData) =
		applyToMeta(PotionMeta::class.java) { it.basePotionData = basePotionData }

	val basePotionData get() =
		getFromMeta(PotionMeta::class.java) { it.basePotionData }

	fun addCustomPotionEffect(potionEffect: PotionEffect, overwrite: Boolean) =
		applyToMeta(PotionMeta::class.java) { it.addCustomEffect(potionEffect, overwrite) }

	val customPotionEffects get() =
		getFromMeta(PotionMeta::class.java) { it.customEffects }

	// TODO uncomment when added
	/*
	fun setUnusablePersistentData() =
		setPersistentData(UnusableItemManager.unusablePersistentDataNamespacedKey, UnusableItemManager.unusablePersistentDataType, UnusableItemManager.unusablePersistentDataValue)
	 */

	fun removePersistentData(namespacedKey: NamespacedKey) =
		PersistentDataShortcuts.remove(itemStack, namespacedKey)

	fun <T, Z> setPersistentData(namespacedKey: NamespacedKey, type: PersistentDataType<T, Z>, value: Z) =
		applyToMeta { it.persistentDataContainer.set<T, Z>(namespacedKey, type, value) }

	fun <T, Z> getPersistentData(namespacedKey: NamespacedKey, type: PersistentDataType<T, Z>): Z? =
		getFromMeta { it.persistentDataContainer.get(namespacedKey, type) }

	fun setPersistentDataPlayerUUID(namespacedKey: NamespacedKey, value: PlayerUUID) {
		PersistentDataShortcuts.PlayerUUID[itemStack, namespacedKey] = value
	}

	fun getPersistentDataPlayerUUID(namespacedKey: NamespacedKey) =
		PersistentDataShortcuts.PlayerUUID[itemStack, namespacedKey]

	fun setPersistentDataBoolean(namespacedKey: NamespacedKey, value: Boolean) {
		PersistentDataShortcuts.Boolean[itemStack, namespacedKey] = value
	}

	fun getPersistentDataBoolean(namespacedKey: NamespacedKey) =
		PersistentDataShortcuts.Boolean[itemStack, namespacedKey]

	fun setPersistentDataTag(namespacedKey: NamespacedKey) =
		PersistentDataShortcuts.Tag.set(itemStack, namespacedKey)

	fun getPersistentDataTag(namespacedKey: NamespacedKey) =
		PersistentDataShortcuts.Tag[itemStack, namespacedKey]

	fun build() = itemStack.clone()

	companion object {

		fun create(itemStack: ItemStack) =
			ItemStackBuilder(itemStack)

		fun create(type: Material, amount: Int) =
			create(ItemStack(type, amount))

		fun create(type: Material) =
			create(ItemStack(type))

		fun createWaterBottle() =
			create(Material.POTION).setBasePotionData(PotionData(PotionType.WATER))

		// TODO uncomment when added
		/*
		fun createTypicalForInterfaceInventory(itemStack: ItemStack) =
			create(itemStack).addItemFlags(ItemFlag.HIDE_ATTRIBUTES).hideNoPotionEffects().setUnusablePersistentData()

		fun createUnusable(type: Material, amount: Int): ItemStackBuilder =
			createTypicalForInterfaceInventory(ItemStack(type, amount))

		fun createUnusable(type: Material): ItemStackBuilder =
			createTypicalForInterfaceInventory(ItemStack(type))
		 */

		private const val loreNewLine = "%NEWLINE%"
		fun translateLoreNewLines(lore: Array<String>, startWithNewLine: Boolean, endWithNewLine: Boolean) =
			translateLoreNewLines(listOf(*lore), startWithNewLine, endWithNewLine).toTypedArray()

		fun translateLoreNewLines(lore: List<String>, startWithNewLine: Boolean, endWithNewLine: Boolean): List<String> {
			val translatedLore: MutableList<String> = ArrayList()
			var shouldPlaceLineWhenSeeingText = false
			if (startWithNewLine) {
				shouldPlaceLineWhenSeeingText = true
			}
			for (line in lore) {
				if (line == loreNewLine) {
					if (translatedLore.isNotEmpty()) {
						shouldPlaceLineWhenSeeingText = true
					}
				} else {
					if (shouldPlaceLineWhenSeeingText) {
						translatedLore.add("")
						shouldPlaceLineWhenSeeingText = false
					}
					translatedLore.add(line)
				}
			}
			if (endWithNewLine) {
				translatedLore.add("")
			}
			return translatedLore
		}
	}

	init {
	}

}