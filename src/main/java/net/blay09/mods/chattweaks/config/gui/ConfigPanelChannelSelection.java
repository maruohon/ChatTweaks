package net.blay09.mods.chattweaks.config.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.blay09.mods.chattweaks.ChatManager;
import net.blay09.mods.chattweaks.chat.ChatChannel;
import net.blay09.mods.chattweaks.chat.ChatView;
import net.blay09.mods.chattweaks.config.options.ConfigBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

public class ConfigPanelChannelSelection extends ConfigPanelSub
{
    private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
    private final List<ChannelEntry> availableChannels = new ArrayList<>();
    private final List<ChannelEntry> selectedChannels = new ArrayList<>();
    private final List<ChannelEntry> allChannels = new ArrayList<>();
    private final ChatView view;

    public ConfigPanelChannelSelection(String title, ChatTweaksConfigPanel parent, ConfigPanelSub parentSubPanel, ChatView view)
    {
        super(title + " - Select Channels", parent, parentSubPanel);

        this.view = view;
    }

    private void updateLists()
    {
        this.availableChannels.clear();
        this.selectedChannels.clear();
        this.allChannels.clear();
        Set<ChatChannel> selected = new HashSet<>(this.view.getChannels());
        int xAvail = 10;
        int yAvail = 32;
        int xSel = 260;
        int ySel = 32;
        int width = 120;
        int height = 32;

        for (ChatChannel channel : ChatManager.getChatChannels())
        {
            if (selected.contains(channel))
            {
                this.selectedChannels.add(new ChannelEntry(xSel, ySel, width, height, true, channel, this.mc));
                ySel += height + 1;
            }
            else
            {
                this.availableChannels.add(new ChannelEntry(xAvail, yAvail, width, height, false, channel, this.mc));
                yAvail += height + 1;
            }
        }

        this.allChannels.addAll(this.availableChannels);
        this.allChannels.addAll(this.selectedChannels);
    }

    @Override
    protected Collection<ConfigBase> getConfigs()
    {
        return Collections.emptyList();
    }

    @Override
    public void onPanelHidden()
    {
        this.view.clearChannels();

        for (ChannelEntry entry : this.selectedChannels)
        {
            this.view.addChannel(entry.channel);
        }
    }

    @Override
    public void addOptions(ConfigPanelHost host)
    {
        this.updateLists();
    }

    @Override
    public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks)
    {
        //super.drawPanel(host, mouseX, mouseY, partialTicks);
        String str = TextFormatting.UNDERLINE.toString() + TextFormatting.BOLD + I18n.format("chattweaks:config.channels.available");
        this.mc.fontRenderer.drawString(str, 10, 10, 0xFFFFFFFF);

        str = TextFormatting.UNDERLINE.toString() + TextFormatting.BOLD + I18n.format("chattweaks:config.channels.selected");
        this.mc.fontRenderer.drawString(str, 260, 10, 0xFFFFFFFF);

        for (ChannelEntry entry : this.allChannels)
        {
            entry.drawEntry(mouseX, mouseY);
        }
    }

    @Override
    public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        for (ChannelEntry entry : this.allChannels)
        {
            if (entry.mousePressed(mouseX, mouseY))
            {
                if (entry.selected)
                {
                    this.view.removeChannel(entry.channel);
                }
                else
                {
                    this.view.addChannel(entry.channel);
                }

                this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.updateLists();
                return;
            }
        }

        super.mousePressed(host, mouseX, mouseY, mouseButton);
    }

    private class ChannelEntry
    {
        private final Minecraft mc;
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private final boolean selected;
        private final ChatChannel channel;

        private ChannelEntry(int x, int y, int width, int height, boolean selected, ChatChannel channel, Minecraft mc)
        {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.selected = selected;
            this.channel = channel;
            this.mc = mc;
        }

        public boolean mousePressed(int mouseX, int mouseY)
        {
            if (mouseX >= this.x && mouseX <= this.x + 32 &&
                mouseY >= this.y && mouseY <= this.y + 32)
            {
                return true;
            }

            return false;
        }

        public void drawEntry(int mouseX, int mouseY)
        {
            this.mc.getTextureManager().bindTexture(this.channel.getIcon());
            GlStateManager.color(1f, 1f, 1f, 1f);
            Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, 0f, 0f, 32, 32, 32f, 32f);
            this.mc.fontRenderer.drawStringWithShadow(this.channel.getName(), this.x + 34, this.y + 1, 0xFFFFFF);
            this.mc.fontRenderer.drawStringWithShadow(this.channel.getDescription(), this.x + 34, this.y + 12, 0xDDDDDD);

            if (mouseX >= this.x && mouseX <= this.x + this.width &&
                mouseY >= this.y && mouseY <= this.y + this.height)
            {
                this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);
                Gui.drawRect(this.x, this.y, this.x + 32, this.y + 32, 0x44FFFFFF);
                GlStateManager.color(1f, 1f, 1f, 1f);
                int relMouseX = mouseX - this.x;
                float u;
                float v;

                if (this.selected)
                {
                    if (relMouseX < 32)
                    {
                        u = 32f;
                        v = 32f;
                    }
                    else
                    {
                        u = 32f;
                        v =  0f;
                    }
                }
                else
                {
                    if (relMouseX < 32)
                    {
                        u =  0f;
                        v = 32f;
                    }
                    else
                    {
                        u =  0f;
                        v =  0f;
                    }
                }

                Gui.drawModalRectWithCustomSizedTexture(this.x, this.y, u, v, 32, 32, 256f, 256f);
            }
        }
    }
}
