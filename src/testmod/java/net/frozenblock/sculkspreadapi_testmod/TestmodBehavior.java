package net.frozenblock.sculkspreadapi_testmod;

import net.frozenblock.sculkspreadapi.util.SculkSharedConstants;
import net.frozenblock.sculkspreadapi.util.SculkSpreadBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;

public final class TestmodBehavior {

	public static class StartCharge implements SculkSpreadBehavior.StartChargeUse {

		@Override
		public void attemptSpread(SculkBlock block, SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader spreader, boolean spread) {
			SculkSharedConstants.log("Testmod StartCharge behavior ran", SculkSharedConstants.UNSTABLE_LOGGING);
			level.setBlock(cursor.getPos().above(2), Blocks.BEDROCK.defaultBlockState(), 3);
		}
	}

	public static class EndCharge implements SculkSpreadBehavior.EndChargeUse {

		@Override
		public void attemptSpread(SculkBlock block, SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader spreader, boolean spread, BlockPos chargePos) {
			SculkSharedConstants.log("Testmod EndCharge behavior ran", SculkSharedConstants.UNSTABLE_LOGGING);
			level.playSound(null, catalystPos, SoundEvents.HORSE_DEATH, SoundSource.BLOCKS, 1.0F, 1.0F);
		}
	}

	public static class AddActivator implements SculkSpreadBehavior.PlaceActivator {

		@Override
		public BlockState place(SculkBlock block, SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader spreader, boolean spread, int chargeAmount, BlockPos chargePos, int growthSpawnCost, BlockPos aboveChargePos) {
			SculkSharedConstants.log("Testmod AddActivator behavior ran", SculkSharedConstants.UNSTABLE_LOGGING);
			return null;
		}
	}
}
