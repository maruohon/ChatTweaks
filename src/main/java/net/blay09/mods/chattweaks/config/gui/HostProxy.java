package net.blay09.mods.chattweaks.config.gui;

import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.blay09.mods.chattweaks.LiteModChatTweaks;

public class HostProxy implements ConfigPanelHost
{
    private final ChatTweaksConfigGui parent;

    public HostProxy(ChatTweaksConfigGui parent)
    {
        this.parent = parent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <TModClass extends LiteMod> TModClass getMod()
    {
        return (TModClass) LiteModChatTweaks.getInstance();
    }

    @Override
    public int getWidth()
    {
        return this.parent.getInnerWidth();
    }

    @Override
    public int getHeight()
    {
        return this.parent.getInnerHeight();
    }

    @Override
    public void close()
    {
        this.parent.close();
    }
}
