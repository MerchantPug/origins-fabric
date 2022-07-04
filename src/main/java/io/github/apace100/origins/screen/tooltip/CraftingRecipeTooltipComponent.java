package io.github.apace100.origins.screen.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.apace100.origins.Origins;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link ClientTooltipComponent} used for {@link io.github.apace100.origins.badge.CraftingRecipeBadge}
 * Draws a snapshot of a 3x3 crafting recipe in the tooltip
 */
@OnlyIn(Dist.CLIENT)
public class CraftingRecipeTooltipComponent implements ClientTooltipComponent {
	private final NonNullList<ItemStack> inputs;
	private final ItemStack output;
	private static final ResourceLocation TEXTURE = Origins.identifier("textures/gui/tooltip/recipe_tooltip.png");

	public CraftingRecipeTooltipComponent(NonNullList<ItemStack> inputs, ItemStack output) {
		this.inputs = inputs;
		this.output = output;
	}

	@Override
	public int getHeight() {
		return 68;
	}

	@Override
	public int getWidth(@NotNull Font pFont) {
		return 130;
	}

	@Override
	public void renderImage(@NotNull Font pFont, int pMouseX, int pMouseY, @NotNull PoseStack pPoseStack, @NotNull ItemRenderer pItemRenderer, int pBlitOffset) {
		this.drawBackGround(pPoseStack, pMouseX, pMouseY, pBlitOffset);
		for (int column = 0; column < 3; ++column) {
			for (int row = 0; row < 3; ++row) {
				int index = column + row * 3;
				int slotX = pMouseX + 8 + column * 18;
				int slotY = pMouseY + 8 + row * 18;
				ItemStack stack = this.inputs.get(index);
				pItemRenderer.renderAndDecorateItem(stack, slotX, slotY, index);
				pItemRenderer.renderGuiItemDecorations(pFont, stack, slotX, slotY);
			}
		}
		pItemRenderer.renderAndDecorateItem(this.output, pMouseX + 101, pMouseY + 25, 10);
	}

	public void drawBackGround(PoseStack matrices, int x, int y, int z) {
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		Gui.blit(matrices, x, y, z, 0, 0, 130, 86, 256, 256);
	}
}
