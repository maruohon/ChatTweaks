package net.blay09.mods.chattweaks.config.gui;

import java.util.Collection;
import net.blay09.mods.chattweaks.config.Configs;
import net.blay09.mods.chattweaks.config.options.ConfigBase;

public class ConfigPanelGeneric extends ConfigPanelSub
{
    public ConfigPanelGeneric(ChatTweaksConfigPanel parent)
    {
        super("Generic", parent);
    }

    @Override
    protected Collection<ConfigBase> getConfigs()
    {
        return Configs.Generic.OPTIONS;
    }
}