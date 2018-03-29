package net.blay09.mods.chattweaks.config.gui;

public interface IConfigPanelFactory
{
    /**
     * Creates a new ConfigPanelSub instance, with the given parent panel.
     * The String returned by getPanelTitle() will be used for the panel selection button.
     * @param parent
     * @return
     */
    ConfigPanelSub createConfigPanel(ChatTweaksConfigPanel parent);
}
