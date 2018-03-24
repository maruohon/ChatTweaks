package net.blay09.mods.chattweaks.config.gui.button;

import net.blay09.mods.chattweaks.config.options.ConfigOptionList;
import net.minecraft.client.Minecraft;

public class ConfigButtonOptionList extends ConfigButtonBase
{
    private final ConfigOptionList config;

    public ConfigButtonOptionList(int id, int x, int y, int width, int height, ConfigOptionList config)
    {
        super(id, x, y, width, height);
        this.config = config;

        this.updateDisplayString();
    }

    @Override
    public void onMouseClicked()
    {
    }

    @Override
    public void onMouseButtonClicked(int mouseButton)
    {
        this.config.setValue(this.config.getValue().cycle(mouseButton == 0));
        this.updateDisplayString();
        this.playPressSound(Minecraft.getMinecraft().getSoundHandler());
    }

    private void updateDisplayString()
    {
        this.displayString = String.valueOf(this.config.getValue().getDisplayName());
    }
}
