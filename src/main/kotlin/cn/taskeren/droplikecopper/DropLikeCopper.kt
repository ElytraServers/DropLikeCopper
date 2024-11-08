package cn.taskeren.droplikecopper

import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.registries.DeferredRegister
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.getValue

/**
 * Main mod class.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(DropLikeCopper.ID)
object DropLikeCopper {
	const val ID = "drop_like_copper"

	const val ATO_ID = "alltheores"

	val LOGGER: Logger = LogManager.getLogger(ID)

	init {
		LOGGER.log(Level.INFO, "Copper-ize!")
		ModItems.REGISTRY.register(MOD_BUS)
	}

	object ModItems {
		val REGISTRY = DeferredRegister.createItems(ID)

		val DROP_TEST_TOOL by REGISTRY.register("drop_test_tool") { -> DropTestToolItem }
	}
}
