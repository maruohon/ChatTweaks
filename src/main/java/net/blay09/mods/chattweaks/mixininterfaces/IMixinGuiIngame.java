package net.blay09.mods.chattweaks.mixininterfaces;

import net.minecraft.client.gui.GuiNewChat;

public interface IMixinGuiIngame
{
    void setPersistentChatGUI(GuiNewChat gui);
}
