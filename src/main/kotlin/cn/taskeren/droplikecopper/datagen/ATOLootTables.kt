package cn.taskeren.droplikecopper.datagen

import net.allthemods.alltheores.blocks.AOreBlock
import net.allthemods.alltheores.blocks.BlockList
import net.allthemods.alltheores.blocks.OtherOreBlock
import net.minecraft.core.HolderLookup
import net.minecraft.core.registries.Registries
import net.minecraft.data.loot.packs.VanillaBlockLoot
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.enchantment.Enchantments
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LiquidBlock
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.entries.LootItem
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator

class ATOLootTables(p: HolderLookup.Provider) : VanillaBlockLoot(p) {

	override fun generate() {
		knownBlocks.forEach { dropRaw(it) }
	}

	override fun getKnownBlocks(): Iterable<Block> {
		return BlockList.BLOCKS.entries
			.map { it.get() }
			.filter { it !is LiquidBlock }
	}

	private fun dropRaw(block: Block) {
		if(block !is AOreBlock && block !is OtherOreBlock) {
			return dropSelf(block)
		}

		val type = block.name.toString()
		if("aluminum" in type) {
			add(block) { createOreDrops(block, BlockList.ALUMINUM_RAW.get()) }
		}
		if("lead" in type) {
			add(block) { createOreDrops(block, BlockList.LEAD_RAW.get()) }
		}
		if("iridium" in type) {
			add(block) { createOreDrops(block, BlockList.IRIDIUM_RAW.get()) }
		}
		if("nickel" in type) {
			add(block) { createOreDrops(block, BlockList.NICKEL_RAW.get()) }
		}
		if("osmium" in type) {
			add(block) { createOreDrops(block, BlockList.OSMIUM_RAW.get()) }
		}
		if("platinum" in type) {
			add(block) { createOreDrops(block, BlockList.PLATINUM_RAW.get()) }
		}
		if("silver" in type) {
			add(block) { createOreDrops(block, BlockList.SILVER_RAW.get()) }
		}
		if("tin_" in type) {
			add(block) { createOreDrops(block, BlockList.TIN_RAW.get()) }
		}
		if("uranium" in type) {
			add(block) { createOreDrops(block, BlockList.URANIUM_RAW.get()) }
		}
		if("zinc" in type) {
			add(block) { createOreDrops(block, BlockList.ZINC_RAW.get()) }
		}
		if("coal" in type) {
			add(block) { createOreDrops(block, Items.COAL) }
		}
		if("copper" in type) {
			add(block) { createOreDrops(block, Items.RAW_COPPER) }
		}
		if("diamond" in type) {
			add(block) { createOreDrops(block, Items.DIAMOND) }
		}
		if("emerald" in type) {
			add(block) { createOreDrops(block, Items.EMERALD) }
		}
		if("gold" in type) {
			add(block) { createOreDrops(block, Items.RAW_GOLD) }
		}
		if("iron" in type) {
			add(block) { createOreDrops(block, Items.RAW_IRON) }
		}
		if("lapis" in type) {
			add(block) { createOreDrops(block, Items.LAPIS_LAZULI) }
		}
		if("quartz" in type) {
			add(block) { createOreDrops(block, Items.QUARTZ) }
		}
		if("redstone" in type) {
			add(block) { createOreDrops(block, Items.REDSTONE) }
		}
		if("ruby" in type) {
			add(block) { createOreDrops(block, BlockList.RUBY.get()) }
		}
		if("sapphire" in type) {
			add(block) { createOreDrops(block, BlockList.SAPPHIRE.get()) }
		}
		if("peridot" in type) {
			add(block) { createOreDrops(block, BlockList.PERIDOT.get()) }
		}
		if("fluorite" in type) {
			add(block) { createOreDrops(block, BlockList.FLUORITE.get()) }
		}
		if("cinnabar" in type) {
			add(block) { createOreDrops(block, BlockList.CINNABAR.get()) }
		}
		if("sulfur" in type) {
			add(block) { createOreDrops(block, BlockList.SULFUR.get()) }
		}
		if("salt" in type) {
			add(block) { createOreDrops(block, BlockList.SALT.get()) }
		}
	}

	/**
	 * Create ore drops like copper, which NORMALLY gives 2-5 items
	 */
	private fun createOreDrops(block: Block, item: Item): LootTable.Builder {
		val registryLookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT)
		return createSilkTouchDispatchTable(
			block,
			applyExplosionDecay(
				block,
				LootItem.lootTableItem(item)
					.apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
					.apply(ApplyBonusCount.addOreBonusCount(registryLookup.getOrThrow(Enchantments.FORTUNE)))
			)
		)
	}
}
