package net.frozenblock.sculkspreadapi.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.sculkspreadapi.util.SculkSharedConstants;

public class FrozenLibIntegration extends ModIntegration {
	public FrozenLibIntegration() {
		super("frozenlib");
	}

	@Override
	public void init() {
		SculkSharedConstants.log("FrozenLib integration ran!", SculkSharedConstants.UNSTABLE_LOGGING);
	}
}
