package io.github.apace100.origins.mixin.forge;

import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.edwinmindcraft.apoli.common.power.ModifyPlayerSpawnPower;
import io.github.edwinmindcraft.apoli.common.power.configuration.ModifyPlayerSpawnConfiguration;
import io.github.edwinmindcraft.origins.api.origin.IOriginCallbackPower;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModifyPlayerSpawnPower.class)
public abstract class ModifyPlayerSpawnPowerMixin implements IOriginCallbackPower<ModifyPlayerSpawnConfiguration> {
	@Shadow(remap = false)
	public abstract void teleportToModifiedSpawn(ConfiguredPower<ModifyPlayerSpawnConfiguration, ?> configuration, Entity entity);

	public <F extends PowerFactory<ModifyPlayerSpawnConfiguration>> void onChosen(ConfiguredPower<ModifyPlayerSpawnConfiguration, F> configuration, Entity living, boolean isOrb) {
		if (!isOrb) //This is IMO a better way to do this.
			this.teleportToModifiedSpawn(configuration, living);
	}
}
