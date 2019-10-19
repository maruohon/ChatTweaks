package net.blay09.mods.chattweaks.gui;

import net.blay09.mods.chattweaks.chat.ChatMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class BottomChatRenderer {

    private static final float MESSAGE_TIME = 80;
    private static final float SCALE = 0.8f;

    private ChatMessage chatMessage;
    private float timeLeft;

    public void setMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
        this.timeLeft = MESSAGE_TIME;
    }

    public void onRenderGameOverlayPost(Minecraft mc, ScaledResolution scaledResolution, float partialTicks) {
        if(chatMessage == null) {
            return;
        }
        timeLeft -= partialTicks;
        int alpha = (int) (255f * (timeLeft / MESSAGE_TIME));
        if(timeLeft <= 0) {
            chatMessage = null;
            return;
        }
        GlStateManager.pushMatrix();
        GlStateManager.translate(scaledResolution.getScaledWidth() / 2, scaledResolution.getScaledHeight() - 64, 0f);
        GlStateManager.scale(SCALE, SCALE, 1f);
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        String formattedText = chatMessage.getTextComponent().getFormattedText();
        int textWidth = fontRenderer.getStringWidth(formattedText);
        int boxMarginX = 4;
        int boxMarginY = 3;
        int x = -textWidth / 2;
        int y = 0;
        int backgroundColor = 0x110111 + (alpha << 24);
        int borderColor2 = 0x28007F + (alpha << 24);
        Gui.drawRect(x - boxMarginX - 1, y - boxMarginY - 1, x + textWidth + boxMarginX + 1, y - boxMarginY, borderColor2);
        Gui.drawRect(x - boxMarginX - 1, y + fontRenderer.FONT_HEIGHT + boxMarginY, x + textWidth + boxMarginX + 1, y + fontRenderer.FONT_HEIGHT + boxMarginY + 1, borderColor2);
        Gui.drawRect(x - boxMarginX - 1, y - boxMarginY, x - boxMarginX, y + fontRenderer.FONT_HEIGHT + boxMarginY, borderColor2);
        Gui.drawRect(x + textWidth + boxMarginX, y - boxMarginY, x + textWidth + boxMarginX + 1, y + fontRenderer.FONT_HEIGHT + boxMarginY, borderColor2);
        Gui.drawRect(x - boxMarginX, y - boxMarginY, x + textWidth + boxMarginX, y + fontRenderer.FONT_HEIGHT + boxMarginY, backgroundColor);
        if(alpha > 12) {
            GlStateManager.enableBlend();
            fontRenderer.drawString(formattedText, x, y, 0xFFFFFF + (alpha << 24), true);
        }

        GlStateManager.popMatrix();
    }
}
