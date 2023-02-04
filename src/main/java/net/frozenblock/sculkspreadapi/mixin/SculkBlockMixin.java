package net.frozenblock.sculkspreadapi.mixin;

import net.frozenblock.sculkspreadapi.SculkSpreadAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkBlock.class)
public class SculkBlockMixin {

	@Inject(method = "attemptUseCharge", at = @At("HEAD"))
	private void startPlace(SculkSpreader.ChargeCursor cursor, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader spreader, boolean spread, CallbackInfoReturnable<Integer> cir) {
		SculkSpreadAPI.START_CHARGE_USE.invoker().attemptSpread(cursor, level, pos, random, spreader, spread);
	}
}
