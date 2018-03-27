package net.blay09.mods.chattweaks.config.gui;

import java.util.Collection;
import java.util.Collections;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.blay09.mods.chattweaks.config.options.ConfigBase;

public class ConfigPanelViewSettings extends ConfigPanelSub
{
    public ConfigPanelViewSettings(ChatTweaksConfigPanel parent, ConfigPanelSub parentSub, String viewName)
    {
        super("Views - " + viewName, parent, parentSub);
    }

    @Override
    protected Collection<ConfigBase> getConfigs()
    {
        return Collections.emptyList();
    }

    @Override
    public void addOptions(ConfigPanelHost host)
    {
        this.clearOptions();
    }

    @Override
    public void onPanelHidden()
    {
    }
}
