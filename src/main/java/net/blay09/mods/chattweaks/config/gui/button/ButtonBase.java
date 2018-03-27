package net.blay09.mods.chattweaks.config.gui.button;

import net.minecraft.client.gui.GuiButton;

public abstract class ButtonBase extends GuiButton
{
    public ButtonBase(int id, int x, int y, int width, int height)
    {
        this(id, x, y, width, height, "");
    }

    public ButtonBase(int id, int x, int y, int width, int height, String text)
    {
        super(id, x, y, width, height, text);
    }

    public abstract void onMouseClicked();

    public abstract void onMouseButtonClicked(int mouseButton);
}
