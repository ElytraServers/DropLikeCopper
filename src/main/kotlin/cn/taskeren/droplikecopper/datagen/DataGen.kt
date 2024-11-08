package cn.taskeren.droplikecopper.datagen

import cn.taskeren.droplikecopper.DropLikeCopper
import net.minecraft.data.DataGenerator
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.EventBusSubscriber.Bus
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import net.neoforged.neoforge.data.event.GatherDataEvent

@EventBusSubscriber(modid = DropLikeCopper.ID, bus = Bus.MOD)
object DataGen {

	@SubscribeEvent
	fun gatherData(e: GatherDataEvent) {
		DropLikeCopper.LOGGER.info("Generating data")

		val g = e.generator
		val o = g.packOutput
		val f = e.existingFileHelper

		if(e.includeServer()) {
			g.addProvider(
				true,
				LootTableProvider(
					o,
					emptySet(),
					listOf(LootTableProvider.SubProviderEntry(::ATOLootTables, LootContextParamSets.BLOCK)),
					e.lookupProvider
				)
			)
		}

		if(e.includeClient()) {
			g.addProvider(true, ItemModels(g, f))
		}
	}

	private fun rl(name: String) = ResourceLocation.fromNamespaceAndPath(DropLikeCopper.ID, name)

	class ItemModels(g: DataGenerator, f: ExistingFileHelper) : ItemModelProvider(g.packOutput, DropLikeCopper.ID, f) {
		override fun registerModels() {
			val handheld = ResourceLocation.withDefaultNamespace("item/handheld")

			withExistingParent("drop_test_tool", handheld).texture("layer0", rl("item/drop_test_tool"))
		}
	}

}
