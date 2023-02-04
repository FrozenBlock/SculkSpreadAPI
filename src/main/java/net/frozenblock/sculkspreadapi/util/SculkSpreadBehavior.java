package net.frozenblock.sculkspreadapi.util;

import net.frozenblock.lib.entrypoint.api.CommonEventEntrypoint;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkSpreader;

/**
 * Entrypoint for a mod to define behavior
 */
public final class SculkSpreadBehavior {

	public interface StartPlace {
		void attemptSpread(SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader spreader, boolean spread);
	}
}
