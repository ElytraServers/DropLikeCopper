package cn.taskeren.droplikecopper

import net.neoforged.api.distmarker.Dist
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(DropLikeCopper.ID, dist = [Dist.DEDICATED_SERVER])
@EventBusSubscriber
object DropLikeCopper {
	const val ID = "drop_like_copper"

	const val ATO_ID = "alltheores"

	val LOGGER: Logger = LogManager.getLogger(ID)

	init {
		LOGGER.log(Level.INFO, "Copper-ize!")
	}

}
