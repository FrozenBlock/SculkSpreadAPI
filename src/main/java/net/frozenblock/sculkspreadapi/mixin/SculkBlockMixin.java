package net.frozenblock.sculkspreadapi.mixin;

import net.frozenblock.sculkspreadapi.SculkSpreadAPI;
import net.frozenblock.sculkspreadapi.util.SculkSharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SculkBlock.class)
public class SculkBlockMixin {

	@Shadow
	private static boolean canPlaceGrowth(LevelAccessor level, BlockPos pos) {
		return false;
	}

	@Inject(method = "attemptUseCharge", at = @At("HEAD"))
	private void startChargeUse(SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader spreader, boolean spread, CallbackInfoReturnable<Integer> cir) {
		SculkBlock sculk = SculkBlock.class.cast(this);
		SculkSharedConstants.log("Started attemptUseCharge", SculkSharedConstants.UNSTABLE_LOGGING);
		SculkSpreadAPI.START_CHARGE_USE.invoker().attemptSpread(sculk, cursor, level, pos, random, spreader, spread);
	}

	@Inject(method = "attemptUseCharge", at = @At("RETURN"))
	private void endChargeUse(SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader spreader, boolean spread, CallbackInfoReturnable<Integer> cir) {
		SculkBlock sculk = SculkBlock.class.cast(this);
		SculkSharedConstants.log("Ending attemptUseCharge", SculkSharedConstants.UNSTABLE_LOGGING);
		SculkSpreadAPI.END_CHARGE_USE.invoker().attemptSpread(sculk, cursor, level, pos, random, spreader, spread, cursor.getPos());
	}

	@Redirect(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;canPlaceGrowth(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Z"))
	private boolean newWorldgenCharge(LevelAccessor levelAccessor, BlockPos blockPos, SculkSpreader.ChargeCursor charge, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader sculkChargeHandler) {
		return this.canPlaceGrowth(levelAccessor, blockPos, sculkChargeHandler.isWorldGeneration());
	}

	/*@Inject(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;getRandomGrowthState(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/world/level/block/state/BlockState;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private void getRandomGrowthState(SculkSpreader.ChargeCursor charge, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader sculkChargeHandler, boolean spread, CallbackInfoReturnable<Integer> cir, int chargeAmount, BlockPos chargePos, boolean bl, int growthSpawnCost, BlockPos aboveChargePos) {
		SculkBlock block = SculkBlock.class.cast(this);

		BlockState growthState = null;
		boolean isWorldGen = sculkChargeHandler.isWorldGeneration();
		boolean canReturn = false;
		BlockPos newChargePos = new BlockPos(aboveChargePos);

		BlockState stateDown = level.getBlockState(chargePos.below());
		Block blockDown = stateDown.getBlock();
		growthState = SculkSpreadAPI.PLACE_ACTIVATOR.invoker().place(block, charge, level, catalystPos, random, sculkChargeHandler, spread, chargeAmount, chargePos, growthSpawnCost, aboveChargePos);
		if (growthState != null) {
			canReturn = true;
			newChargePos = chargePos.below();
		}

		if (canReturn) {
			level.setBlock(newChargePos, growthState, 3);

			cir.setReturnValue(Math.max(0, chargeAmount - growthSpawnCost));
			level.playSound(null, newChargePos, growthState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
		} else {
			cir.setReturnValue(chargeAmount);
		}
	}*/

	@Unique
	private boolean canPlaceGrowth(LevelAccessor level, BlockPos pos, boolean isWorldGen) {
		SculkBlock sculk = SculkBlock.class.cast(this);
		return SculkSpreadAPI.START_CAN_PLACE.invoker().canPlaceGrowth(sculk, level, pos, isWorldGen)
				|| canPlaceGrowth(level, pos)
				|| SculkSpreadAPI.END_CAN_PLACE.invoker().canPlaceGrowth(sculk, level, pos, isWorldGen);
	}
}
