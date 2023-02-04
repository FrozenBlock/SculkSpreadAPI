package net.frozenblock.sculkspreadapi;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.loader.api.ModContainer;
import net.frozenblock.lib.event.api.FrozenEvents;
import net.frozenblock.sculkspreadapi.mod_compat.SculkIntegrations;
import net.frozenblock.sculkspreadapi.util.SculkSpreadBehavior;
import net.frozenblock.sculkspreadapi.util.SculkSharedConstants;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixerBuilder;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.api.QuiltDataFixes;

public class SculkSpreadAPI implements ModInitializer {

	public static final Event<SculkSpreadBehavior.StartPlace> START_CHARGE_USE = FrozenEvents.createEnvironmentEvent(SculkSpreadBehavior.StartPlace.class,
			callbacks -> (cursor, level, pos, random, spreader, spread) -> {
				for (var callback : callbacks) {
					callback.attemptSpread(cursor, level, pos, random, spreader, spread);
				}
			});

	@Override
	public void onInitialize() {
		SculkSharedConstants.startMeasuring(this);
		applyDataFixes(SculkSharedConstants.MOD_CONTAINER);

		SculkIntegrations.init();

		SculkSharedConstants.stopMeasuring(this);
	}

	private static void applyDataFixes(final @NotNull ModContainer mod) {
		SculkSharedConstants.log("Applying DataFixes for Sculk Spread API with Data Version " + SculkSharedConstants.DATA_VERSION, true);

		var builder = new QuiltDataFixerBuilder(SculkSharedConstants.DATA_VERSION);
		builder.addSchema(0, QuiltDataFixes.BASE_SCHEMA);

		QuiltDataFixes.buildAndRegisterFixer(mod, builder);
		SculkSharedConstants.log("DataFixes for Sculk Spread API have been applied", true);
	}
}
