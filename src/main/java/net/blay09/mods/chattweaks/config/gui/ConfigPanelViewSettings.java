package net.blay09.mods.chattweaks.config.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.blay09.mods.chattweaks.ChatViewManager;
import net.blay09.mods.chattweaks.chat.ChatChannel;
import net.blay09.mods.chattweaks.chat.ChatView;
import net.blay09.mods.chattweaks.chat.MessageStyle;
import net.blay09.mods.chattweaks.config.gui.button.ButtonGeneric;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerPanelSelection;
import net.blay09.mods.chattweaks.config.options.ConfigBase;
import net.blay09.mods.chattweaks.config.options.ConfigBoolean;
import net.blay09.mods.chattweaks.config.options.ConfigOptionList;
import net.blay09.mods.chattweaks.config.options.ConfigString;
import net.blay09.mods.chattweaks.config.options.ConfigStringList;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public class ConfigPanelViewSettings extends ConfigPanelSub
{
    private final ChatView view;
    private ConfigBoolean exclusive;
    private ConfigBoolean muted;
    private ConfigString name;
    private ConfigString filter;
    private ConfigString outputFormat;
    private ConfigString outgoingPrefix;
    private ConfigOptionList<MessageStyle> messageStyle;

    public ConfigPanelViewSettings(ChatTweaksConfigPanel parent, ConfigPanelSub parentSub, ChatView view)
    {
        super("Views - " + view.getName(), parent, parentSub);

        this.view = view;
        this.createOptions(this.view);
    }

    private void createOptions(ChatView view)
    {
        this.exclusive      = new ConfigBoolean("Exclusive", view.isExclusive(), "Are messages exclusive to this view");
        this.muted          = new ConfigBoolean("Muted", view.isMuted(), "Should messages in this view be muted ie. not printed");

        this.name           = new ConfigString("Name", view.getName(), "The name of this view");
        this.filter         = new ConfigString("Filter Pattern", view.getFilterPattern(), "A filter for the messages to be matched against.\nAn empty filter matches everything.");
        this.outputFormat   = new ConfigString("Output Format", view.getOutputFormat(), "The formatting string for the output messages");
        this.outgoingPrefix = new ConfigString("Outgoing Prefix", view.getOutgoingPrefix(), "A prefix for any outgoing messages in this view");

        this.messageStyle   = new ConfigOptionList<>("Message Style", view.getMessageStyle(), "The type of message output");
    }

    @Override
    protected Collection<ConfigBase> getConfigs()
    {
        return ImmutableList.of(
                this.name,
                this.filter,
                this.outputFormat,
                this.outgoingPrefix,
                this.exclusive,
                this.muted,
                this.messageStyle);
    }

    @Override
    public void onPanelHidden()
    {
        boolean dirty = false;

        if (this.getButtonListener().isDirty())
        {
            dirty = true;
            this.getButtonListener().resetDirty();
        }

        // This reads the textField contents back to the ConfigBase instances
        dirty |= this.handleTextFields();

        this.view.setExclusive(this.exclusive.getValue());
        this.view.setMuted(this.muted.getValue());
        this.view.setFilterPattern(this.filter.getStringValue());
        this.view.setOutputFormat(this.outputFormat.getStringValue());
        this.view.setOutgoingPrefix(this.outgoingPrefix.getStringValue());
        this.view.setMessageStyle(this.messageStyle.getValue());

        String oldName = this.view.getName();

        if (this.name.getStringValue().equals(oldName) == false)
        {
            this.view.setName(this.name.getStringValue());
        }

        if (dirty)
        {
            ChatViewManager.save();
        }
    }

    @Override
    public void addOptions(ConfigPanelHost host)
    {
        super.addOptions(host);

        List<String> list = new ArrayList<>();

        for (ChatChannel channel : this.view.getChannels())
        {
            if (channel != null)
            {
                list.add(channel.getName());
            }
        }

        ConfigPanelSub panelChannelSelection = new ConfigPanelChannelSelection(this.getPanelTitle(), this.parentPanel, this, this.view);
        ButtonListenerPanelSelection<ButtonGeneric> listener = new ButtonListenerPanelSelection<>(this.parentPanel, panelChannelSelection);

        String label;

        if (list.isEmpty())
        {
            label = TextFormatting.RED + I18n.format("chattweaks:gui.error.button.chat_views.no_channels") + TextFormatting.RESET;
        }
        else
        {
            label = ConfigStringList.getClampedDisplayStringOf(list, 34, "[ ", " ]");
        }

        int id = this.getConfigs().size();
        int x = 10;
        int labelWidth = this.getMaxLabelWidth(this.getConfigs()) + 10;
        this.addLabel(id++, x, this.nextElementY + 7, labelWidth, 8, 0xFFFFFFFF, "Channels");
        this.addButton(new ButtonGeneric(id, x + labelWidth, this.nextElementY, 204, 20, label), listener);
    }
}
