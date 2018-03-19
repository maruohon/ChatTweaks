package net.blay09.mods.chattweaks.config.gui;

import net.blay09.mods.chattweaks.config.FeatureToggle;

public class ConfigPanelTweakToggles extends ConfigPanelSub
{
    public ConfigPanelTweakToggles(ChatTweaksConfigPanel parent)
    {
        super("Tweak Toggles", parent);
    }

    @Override
    protected FeatureToggle[] getConfigs()
    {
        return FeatureToggle.values();
    }
}
