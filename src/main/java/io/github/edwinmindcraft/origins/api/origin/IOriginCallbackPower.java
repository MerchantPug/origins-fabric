package io.github.edwinmindcraft.origins.api.origin;

import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import net.minecraft.world.entity.Entity;

public interface IOriginCallbackPower<T extends IDynamicFeatureConfiguration> {
	@SuppressWarnings("unchecked")
	default <F extends PowerFactory<T>> void onChosen(ConfiguredPower<T, F> power, Entity living, boolean isOrb) {
		if (power.getFactory() instanceof IOriginCallbackPower) {
            ((IOriginCallbackPower<T>) power.getFactory()).onChosen(power.getConfiguration(), living, isOrb);
        }
	}

	void onChosen(T configuration, Entity entity, boolean isOrb);
}
