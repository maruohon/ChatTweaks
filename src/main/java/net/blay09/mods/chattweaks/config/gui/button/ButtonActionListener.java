package net.blay09.mods.chattweaks.config.gui.button;

import com.mumfrey.liteloader.modconfig.AbstractConfigPanel.ConfigOptionListener;

public interface ButtonActionListener<T extends ButtonBase> extends ConfigOptionListener<T>
{
    void actionPerformedWithButton(T control, int mouseButton);
}
