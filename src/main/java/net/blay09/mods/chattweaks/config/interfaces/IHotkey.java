package net.blay09.mods.chattweaks.config.interfaces;

public interface IHotkey extends INamed
{
    IKeybind getKeybind();

    void setKeybind(IKeybind keybind);
}
