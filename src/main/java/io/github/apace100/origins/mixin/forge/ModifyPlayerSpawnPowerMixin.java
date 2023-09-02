package io.github.apace100.origins.mixin.forge;

import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import io.github.edwinmindcraft.apoli.common.power.ModifyPlayerSpawnPower;
import io.github.edwinmindcraft.apoli.common.power.configuration.ModifyPlayerSpawnConfiguration;
import io.github.edwinmindcraft.origins.api.origin.IOriginCallbackPower;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModifyPlayerSpawnPower.class)
public abstract class ModifyPlayerSpawnPowerMixin implements IOriginCallbackPower<ModifyPlayerSpawnConfiguration> {

    @Shadow public abstract void teleportToModifiedSpawn(ConfiguredPower<?, ?> configuration, Entity entity);

    @Override
	public <F extends PowerFactory<ModifyPlayerSpawnConfiguration>> void onChosen(@NotNull ConfiguredPower<ModifyPlayerSpawnConfiguration, F> configuration, @NotNull Entity living, boolean isOrb) {
        if (configuration.getFactory() instanceof IOriginCallbackPower && !isOrb) { //This is IMO a better way to do this.
            this.teleportToModifiedSpawn(configuration, living);
        }
    }
}
