package net.blay09.mods.chattweaks.gui.emotes;

import net.blay09.mods.chattweaks.chat.emotes.IEmoteGroup;
import net.blay09.mods.chattweaks.image.renderable.IAnimatedChatRenderable;
import net.blay09.mods.chattweaks.image.renderable.IChatRenderable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonEmoteGroup extends GuiButton {

    private final IChatRenderable image;
    private final IEmoteGroup emoteGroup;

    public GuiButtonEmoteGroup(int buttonId, int x, int y, IChatRenderable image, IEmoteGroup emoteGroup) {
        super(buttonId, x, y, 14, 12, "");
        this.image = image;
        this.emoteGroup = emoteGroup;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            this.hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
            int hoverState = getHoverState(hovered);
            mouseDragged(mc, mouseX, mouseY);
            Gui.drawRect(x, y, x + width, y + height, (hoverState == 2) ? 0x88333333 : 0x44000000);
            if(image.getTextureId() != -1) {
                this.width = (int) (image.getWidth() * image.getScale());
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.bindTexture(image.getTextureId());
                GlStateManager.color(1f, 1f, 1f, 1f);
                GlStateManager.translate(x, y, 100f);
                GlStateManager.scale(image.getScale(), image.getScale(), 1f);
                if(image instanceof IAnimatedChatRenderable) {
                    ((IAnimatedChatRenderable) image).updateAnimation();
                }
                Gui.drawModalRectWithCustomSizedTexture(0, 0, image.getTexCoordX(), image.getTexCoordY(), image.getWidth(), image.getHeight(), image.getSheetWidth(), image.getSheetHeight());
                GlStateManager.popMatrix();
            }
        }
    }

    public IEmoteGroup getEmoteGroup() {
        return emoteGroup;
    }
}
