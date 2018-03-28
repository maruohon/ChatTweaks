package net.blay09.mods.chattweaks.config.options;

public interface IConfigOptionListEntry<T extends IConfigOptionListEntry<T>>
{
    String getStringValue();

    String getDisplayName();

    int getOrdinalValue();

    T cycle(boolean forward);

    T fromString(String value);
}
