package cn.taskeren.droplikecopper

import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import net.minecraft.world.item.TooltipFlag
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import kotlin.time.measureTimedValue

object DropTestToolItem : Item(Properties().stacksTo(16).rarity(Rarity.RARE)) {

	/**
	 * Get the simulation iterating count from the stack size of the item.
	 */
	internal fun ItemStack.getSimulationIteratingCount(): Int {
		return if(item == DropLikeCopper.ModItems.DROP_TEST_TOOL) {
			1 shl (count - 1) // 2^count
		} else {
			0
		}
	}

	/**
	 * Simulate drops with given context.
	 */
	internal fun simulateDrops(context: UseOnContext, iterationCount: Int): Map<ItemStack, Double> {
		require(iterationCount >= 0) { "Iteration Count must be greater than 0" }
		if(iterationCount == 0) return emptyMap()

		val level = context.level

		if(level !is ServerLevel) return emptyMap() // don't run on the client side

		val blockPos = context.clickedPos
		val blockState = level.getBlockState(context.clickedPos)
		val blockEntity = level.getBlockEntity(blockPos)

		val player = context.player ?: return emptyMap() // player must not be null
		val offhandItem = player.offhandItem

		return List(iterationCount) { Block.getDrops(blockState, level, blockPos, blockEntity, player, offhandItem) }
			.flatten() // merge all simulations into one list
			.groupBy { ItemStack.hashItemAndComponents(it) } // grouping itemstacks by its hash
			.values // get only itemstack instances with different hash
			.associate { itemLists ->
				itemLists.first() to (itemLists.sumOf { it.count }.toDouble() / iterationCount)
				// itemstack instance => average drop count
			}
	}

	override fun useOn(context: UseOnContext): InteractionResult {
		// need to be held in the main hand
		if(context.hand != InteractionHand.MAIN_HAND) return InteractionResult.PASS

		val level = context.level
		val player = context.player ?: return InteractionResult.FAIL
		if(level is ServerLevel) {
			val iterCount = context.itemInHand.getSimulationIteratingCount()
			val (value, duration) = measureTimedValue { simulateDrops(context, iterCount) }
			player.sendSystemMessage(
				Component.translatable(
					"item.drop_like_copper.drop_test_tool.message.title",
					iterCount,
					duration.toString()
				)
			)
			for((dropItem, count) in value) {
				player.sendSystemMessage(
					Component.translatable(
						"item.drop_like_copper.drop_test_tool.message.drop",
						dropItem.displayName,
						count
					)
				)
			}
			return InteractionResult.SUCCESS
		}

		return super.useOn(context)
	}

	override fun appendHoverText(
		stack: ItemStack,
		context: TooltipContext,
		tooltipComponents: MutableList<Component>,
		tooltipFlag: TooltipFlag
	) {
		super.appendHoverText(stack, context, tooltipComponents, tooltipFlag)
		tooltipComponents += Component.translatable("item.drop_like_copper.drop_test_tool.tooltip.1")
		tooltipComponents += Component.translatable("item.drop_like_copper.drop_test_tool.tooltip.2")
		tooltipComponents += Component.translatable(
			"item.drop_like_copper.drop_test_tool.tooltip.3",
			stack.getSimulationIteratingCount()
		)
	}
}
