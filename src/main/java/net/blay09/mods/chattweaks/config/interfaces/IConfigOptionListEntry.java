package net.blay09.mods.chattweaks.config.interfaces;

public interface IConfigOptionListEntry
{
    String getStringValue();

    String getDisplayName();

    int getOrdinalValue();

    IConfigOptionListEntry cycle(boolean forward);

    IConfigOptionListEntry fromString(String value);
}
