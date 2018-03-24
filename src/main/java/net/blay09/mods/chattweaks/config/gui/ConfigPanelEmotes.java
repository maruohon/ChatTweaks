package net.blay09.mods.chattweaks.config.gui;

import java.util.Collection;
import net.blay09.mods.chattweaks.config.Configs;
import net.blay09.mods.chattweaks.config.options.ConfigBase;

public class ConfigPanelEmotes extends ConfigPanelSub
{
    public ConfigPanelEmotes(ChatTweaksConfigPanel parent)
    {
        super("Emotes", parent);
    }

    @Override
    protected Collection<ConfigBase> getConfigs()
    {
        return Configs.Emotes.OPTIONS;
    }
}
