package net.blay09.mods.chattweaks.config.gui;

import java.util.Collection;
import net.blay09.mods.chattweaks.config.Configs;
import net.blay09.mods.chattweaks.config.options.ConfigBase;

public class ConfigPanelTheme extends ConfigPanelSub
{
    public ConfigPanelTheme(ChatTweaksConfigPanel parent)
    {
        super("Theme", parent);
    }

    @Override
    protected Collection<ConfigBase> getConfigs()
    {
        return Configs.Theme.OPTIONS;
    }
}
