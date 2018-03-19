package net.blay09.mods.chattweaks.config.gui;

import net.blay09.mods.chattweaks.config.ConfigOption;
import net.blay09.mods.chattweaks.config.interfaces.IConfig;

public class ConfigPanelGeneric extends ConfigPanelSub
{
    public ConfigPanelGeneric(ChatTweaksConfigPanel parent)
    {
        super("Generic", parent);
    }

    @Override
    protected IConfig[] getConfigs()
    {
        return ConfigOption.values();
    }
}
