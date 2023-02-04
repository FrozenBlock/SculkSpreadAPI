package net.frozenblock.sculkspreadapi.util;

import net.frozenblock.lib.entrypoint.api.CommonEventEntrypoint;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Class containing all different behaviors for sculk spreading
 */
public final class SculkSpreadBehavior {

	@FunctionalInterface
	public interface StartChargeUse extends CommonEventEntrypoint {
		void attemptSpread(SculkBlock block, SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader spreader, boolean spread);
	}

	@FunctionalInterface
	public interface EndChargeUse extends CommonEventEntrypoint {
		void attemptSpread(SculkBlock block, SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader spreader, boolean spread, BlockPos chargePos);
	}

	@FunctionalInterface
	public interface PlaceActivator extends CommonEventEntrypoint {
		BlockState place(
				SculkBlock block, SculkSpreader.ChargeCursor cursor, LevelAccessor level,
				BlockPos catalystPos, RandomSource random, SculkSpreader spreader,
				boolean spread, int chargeAmount, BlockPos chargePos,
				int growthSpawnCost, BlockPos aboveChargePos
		);
	}

	@FunctionalInterface
	public interface StartCanPlace extends CommonEventEntrypoint {
		boolean canPlaceGrowth(SculkBlock block, LevelAccessor level, BlockPos pos, boolean isWorldGen);
	}

	@FunctionalInterface
	public interface EndCanPlace extends CommonEventEntrypoint {
		boolean canPlaceGrowth(SculkBlock block, LevelAccessor level, BlockPos pos, boolean isWorldGen);
	}
}
