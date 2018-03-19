package net.blay09.mods.chattweaks.config.interfaces;

public interface IConfig extends INamed
{
    ConfigType getType();

    String getStringValue();
}
