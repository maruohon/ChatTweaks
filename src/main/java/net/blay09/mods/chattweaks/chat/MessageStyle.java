package net.blay09.mods.chattweaks.chat;

import net.blay09.mods.chattweaks.config.options.IConfigOptionListEntry;

public enum MessageStyle implements IConfigOptionListEntry<MessageStyle>
{
    Hidden,
    Chat,
    Side,
    Bottom;

    @Override
    public String getStringValue()
    {
        return this.name().toLowerCase();
    }

    @Override
    public String getDisplayName()
    {
        return this.toString();
    }

    @Override
    public int getOrdinalValue()
    {
        return this.ordinal();
    }

    @Override
    public MessageStyle cycle(boolean forward)
    {
        int id = this.ordinal();

        if (forward)
        {
            if (++id >= values().length)
            {
                id = 0;
            }
        }
        else
        {
            if (--id < 0)
            {
                id = values().length - 1;
            }
        }

        return values()[id % values().length];
    }

    @Override
    public MessageStyle fromString(String name)
    {
        return fromStringStatic(name);
    }

    public static MessageStyle fromStringStatic(String name)
    {
        for (MessageStyle al : MessageStyle.values())
        {
            if (al.name().equalsIgnoreCase(name))
            {
                return al;
            }
        }

        return MessageStyle.Chat;
    }
}