package io.github.edwinmindcraft.origins.client.screen;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.apace100.origins.Origins;
import io.github.apace100.origins.screen.OriginDisplayScreen;
import io.github.edwinmindcraft.apoli.api.ApoliAPI;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.origins.api.origin.IOriginCallbackPower;
import io.github.edwinmindcraft.origins.common.OriginsCommon;
import io.github.edwinmindcraft.origins.common.network.C2SFinalizeNowReadyPowers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class WaitForPowersScreen extends OriginDisplayScreen {

    private static final ResourceLocation WAITING_WINDOW = new ResourceLocation(Origins.MODID, "textures/gui/wait_for_powers.png");
    private final Set<ResourceKey<ConfiguredPower<?, ?>>> waitingFor = Sets.newHashSet();
    private final boolean wasOrb;

    public WaitForPowersScreen(boolean showDirtBackground, Set<ResourceKey<ConfiguredPower<?, ?>>> waitingFor, boolean wasOrb) {
        super(Component.empty(), showDirtBackground);
        this.waitingFor.addAll(waitingFor);
        this.wasOrb = wasOrb;
    }

    @Override
    public void render(@NotNull PoseStack matrices, int mouseX, int mouseY, float delta) {
        this.time += delta;
        this.renderBackground(matrices);
        RenderSystem.setShaderTexture(0, WAITING_WINDOW);
        this.blit(matrices, this.guiLeft, this.guiTop, 0, 0, windowWidth, windowHeight);
        this.renderWaitingText(matrices);
    }

    @Override
    public void tick() {
        if (this.areAllPowersReadyToGo()) {
            OriginsCommon.CHANNEL.sendToServer(new C2SFinalizeNowReadyPowers(this.waitingFor, this.wasOrb));
            this.onClose();
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private boolean areAllPowersReadyToGo() {
        return waitingFor.stream().map(key -> ApoliAPI.getPowers().get(key)).allMatch(p -> p == null || !(p.getFactory() instanceof IOriginCallbackPower callbackPower) || callbackPower.isReady(p, this.minecraft.player, this.wasOrb));
    }

    private void renderWaitingText(@NotNull PoseStack matrices) {
        MutableComponent component = Component.translatable("origins.gui.waiting_for_powers");
        int dotAmount = (int) (this.time * 40 % 4);
        for (int i = 0; i < dotAmount; ++i) {
            component.append(".");
        }
        int x = this.guiLeft + 18;
        int y = this.guiTop + 50;
        drawCenteredString(matrices, this.font, component, x, y, 0xffffff);
    }
}
