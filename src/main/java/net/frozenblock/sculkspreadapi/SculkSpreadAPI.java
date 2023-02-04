package net.frozenblock.sculkspreadapi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.frozenblock.lib.event.api.FrozenEvents;
import net.frozenblock.sculkspreadapi.util.SculkSpreadBehavior;
import net.minecraft.world.level.block.state.BlockState;
import java.util.concurrent.atomic.AtomicReference;

public class SculkSpreadAPI implements ModInitializer {

	public static final Event<SculkSpreadBehavior.StartChargeUse> START_CHARGE_USE = FrozenEvents.createEnvironmentEvent(SculkSpreadBehavior.StartChargeUse.class,
			callbacks -> (block, cursor, level, pos, random, spreader, spread) -> {
				for (var callback : callbacks) {
					callback.attemptSpread(block, cursor, level, pos, random, spreader, spread);
				}
			});

	public static final Event<SculkSpreadBehavior.EndChargeUse> END_CHARGE_USE = FrozenEvents.createEnvironmentEvent(SculkSpreadBehavior.EndChargeUse.class,
			callbacks -> (block, cursor, level, pos, random, spreader, spread, chargePos) -> {
				for (var callback : callbacks) {
					callback.attemptSpread(block, cursor, level, pos, random, spreader, spread, chargePos);
				}
			});

	public static final Event<SculkSpreadBehavior.PlaceActivator> PLACE_ACTIVATOR = FrozenEvents.createEnvironmentEvent(SculkSpreadBehavior.PlaceActivator.class,
			callbacks -> ((block, cursor, level, catalystPos, random, spreader, spread, chargeAmount, chargePos, growthSpawnCost, aboveChargePos) -> {
				BlockState state = null;
				for (var callback : callbacks) {
					var newState = callback.place(block, cursor, level, catalystPos, random, spreader, spread, chargeAmount, chargePos, growthSpawnCost, aboveChargePos);
					if (newState != null) {
						state = newState;
					}
				}
				return state;
			}));

	public static final Event<SculkSpreadBehavior.StartCanPlace> START_CAN_PLACE = FrozenEvents.createEnvironmentEvent(SculkSpreadBehavior.StartCanPlace.class,
			callbacks -> (block, level, pos, isWorldGen) -> {
				for (var callback : callbacks) {
					if (!callback.canPlaceGrowth(block, level, pos, isWorldGen)) {
						return false;
					}
				}
				return true;
			});

	public static final Event<SculkSpreadBehavior.EndCanPlace> END_CAN_PLACE = FrozenEvents.createEnvironmentEvent(SculkSpreadBehavior.EndCanPlace.class,
			callbacks -> (block, level, pos, isWorldGen) -> {
				for (var callback : callbacks) {
					if (!callback.canPlaceGrowth(block, level, pos, isWorldGen)) {
						return false;
					}
				}
				return true;
			});

	@Override
	public void onInitialize() {
		//SculkSharedConstants.startMeasuring(this);

		//SculkSharedConstants.stopMeasuring(this);
	}
}
