package net.blay09.mods.chattweaks.config.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import javax.annotation.Nullable;
import org.lwjgl.input.Keyboard;
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.blay09.mods.chattweaks.config.Configs;
import net.blay09.mods.chattweaks.config.gui.button.ButtonActionListener;
import net.blay09.mods.chattweaks.config.gui.button.ButtonBase;
import net.blay09.mods.chattweaks.config.gui.button.ButtonEntry;
import net.blay09.mods.chattweaks.config.gui.button.ButtonGeneric;
import net.blay09.mods.chattweaks.config.gui.button.ConfigButtonBoolean;
import net.blay09.mods.chattweaks.config.gui.button.ConfigButtonOptionList;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerPanelSelection;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerStringList;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ConfigOptionListenerDirtyChecker;
import net.blay09.mods.chattweaks.config.options.ConfigBase;
import net.blay09.mods.chattweaks.config.options.ConfigBoolean;
import net.blay09.mods.chattweaks.config.options.ConfigOptionList;
import net.blay09.mods.chattweaks.config.options.ConfigStringList;
import net.blay09.mods.chattweaks.config.options.ConfigType;
import net.minecraft.client.resources.I18n;

public abstract class ConfigPanelSub extends AbstractConfigPanel
{
    protected final ButtonListenerStringList<ButtonBase> listenerStringList;
    protected final ChatTweaksConfigPanel parentPanel;
    private final ConfigPanelSub parentSubPanel;
    private final IdentityHashMap<ConfigBase, ConfigTextField> textFields = new IdentityHashMap<>();
    private final ConfigOptionListenerDirtyChecker<ButtonBase> listenerDirtyChecker = new ConfigOptionListenerDirtyChecker<>();
    private final List<ButtonEntry<?>> buttons = new ArrayList<>();
    private final List<HoverInfo> configComments = new ArrayList<>();
    private final String title;
    protected int nextElementY;

    public ConfigPanelSub(String title, ChatTweaksConfigPanel parent)
    {
        this(title, parent, null);
    }

    public ConfigPanelSub(String title, ChatTweaksConfigPanel parent, @Nullable ConfigPanelSub parentSubPanel)
    {
        this.title = title;
        this.parentPanel = parent;
        this.parentSubPanel = parentSubPanel;
        this.listenerStringList = new ButtonListenerStringList<>(parent);
    }

    protected abstract Collection<ConfigBase> getConfigs();

    @Override
    public String getPanelTitle()
    {
        return this.title;
    }

    @Override
    public void onPanelHidden()
    {
        boolean dirty = false;

        if (this.listenerDirtyChecker.isDirty())
        {
            dirty = true;
            this.listenerDirtyChecker.resetDirty();
        }

        dirty |= this.handleTextFields();

        if (dirty)
        {
            Configs.save();
            Configs.load();
        }
    }

    @Override
    public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        for (ButtonEntry<?> entry : this.buttons)
        {
            if (entry.mousePressed(this.mc, mouseX, mouseY, mouseButton))
            {
                // Don't call super if the button press got handled
                return;
            }
        }

        super.mousePressed(host, mouseX, mouseY, mouseButton);
    }

    protected <T extends ButtonBase> void addButton(T button, ButtonActionListener<T> listener)
    {
        this.buttons.add(new ButtonEntry<>(button, listener));
        this.addControl(button, listener);
    }

    protected boolean handleTextFields()
    {
        boolean dirty = false;

        for (ConfigBase config : this.getConfigs())
        {
            ConfigType type = config.getType();

            if (type == ConfigType.STRING ||
                type == ConfigType.HEX_STRING ||
                type == ConfigType.INTEGER ||
                type == ConfigType.DOUBLE)
            {
                ConfigTextField field = this.getTextFieldFor(config);

                if (field != null)
                {
                    String newValue = field.getText();

                    if (newValue.equals(config.getStringValue()) == false)
                    {
                        config.setValueFromString(newValue);
                        dirty = true;
                    }
                }
            }
        }

        return dirty;
    }

    @Override
    public void addOptions(ConfigPanelHost host)
    {
        this.clearOptions();

        int x = 10;
        int y = 10;
        int configHeight = 20;
        int buttonWidth = 204;
        int labelWidth = this.getMaxLabelWidth(this.getConfigs()) + 10;
        int id = 0;

        for (ConfigBase config : this.getConfigs())
        {
            this.addLabel(id++, x, y + 7, labelWidth, 8, 0xFFFFFFFF, config.getName());

            String comment = config.getComment();
            ConfigType type = config.getType();

            if (comment != null)
            {
                this.addConfigComment(x, y + 2, labelWidth, 10, comment);
            }

            if (type == ConfigType.BOOLEAN)
            {
                this.addButton(new ConfigButtonBoolean(id++, x + labelWidth, y, buttonWidth, configHeight, (ConfigBoolean) config), this.listenerDirtyChecker);
            }
            else if (type == ConfigType.OPTION_LIST)
            {
                ConfigButtonOptionList<?> button = new ConfigButtonOptionList<>(id++, x + labelWidth, y, buttonWidth, configHeight, (ConfigOptionList<?>) config);
                this.addButton(button, this.listenerDirtyChecker);
            }
            else if (type == ConfigType.STRING ||
                     type == ConfigType.HEX_STRING ||
                     type == ConfigType.INTEGER ||
                     type == ConfigType.DOUBLE)
            {
                ConfigTextField field = this.addTextField(id++, x + labelWidth, y + 1, buttonWidth - 4, configHeight - 3);
                field.setText(config.getStringValue());
                field.getNativeTextField().setMaxStringLength(128);
                this.addTextField(config, field);
            }
            else if (type == ConfigType.STRING_LIST)
            {
                ConfigStringList configStringList = (ConfigStringList) config;
                String panelTitle = this.getPanelTitle() + " - " + config.getName();
                ConfigPanelSub panelStringList = new ConfigPanelStringList(configStringList, panelTitle, this.parentPanel, this);
                ButtonListenerPanelSelection<ButtonGeneric> listener = new ButtonListenerPanelSelection<>(this.parentPanel, panelStringList);
                this.addButton(new ButtonGeneric(id++, x + labelWidth, y, buttonWidth, configHeight, configStringList.getButtonDisplayString(34)), listener);
            }

            y += configHeight + 1;
        }

        this.nextElementY = y;
    }

    @Override
    public void clearOptions()
    {
        super.clearOptions();
        this.buttons.clear();
    }

    @Override
    public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks)
    {
        super.drawPanel(host, mouseX, mouseY, partialTicks);

        for (HoverInfo label : this.configComments)
        {
            if (label.isMouseOver(mouseX, mouseY))
            {
                this.drawHoveringText(label.getLines(), mouseX + 6, mouseY + 10);
                break;
            }
        }
    }

    protected void addTextField(ConfigBase config, ConfigTextField field)
    {
        this.textFields.put(config, field);
    }

    protected ConfigTextField getTextFieldFor(ConfigBase config)
    {
        return this.textFields.get(config);
    }

    protected void addConfigComment(int x, int y, int width, int height, String comment)
    {
        HoverInfo info = new HoverInfo(x, y, width, height);
        info.addLines(comment);
        this.configComments.add(info);
    }

    protected int getMaxLabelWidth(Collection<ConfigBase> entries)
    {
        int maxWidth = 0;

        for (ConfigBase entry : entries)
        {
            maxWidth = Math.max(maxWidth, this.mc.fontRenderer.getStringWidth(entry.getName()));
        }

        return maxWidth;
    }

    @Override
    public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode)
    {
        if (keyCode == Keyboard.KEY_ESCAPE)
        {
            if (this.parentSubPanel != null)
            {
                this.parentPanel.setSelectedSubPanel(this.parentSubPanel);
            }
            else
            {
                this.parentPanel.setSelectedSubPanel(null);
            }

            return;
        }

        super.keyPressed(host, keyChar, keyCode);
    }

    public static class HoverInfo
    {
        protected final List<String> lines;
        protected int x;
        protected int y;
        protected int width;
        protected int height;

        public HoverInfo(int x, int y, int width, int height)
        {
            this.lines = new ArrayList<>();
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void addLines(String... lines)
        {
            for (String line : lines)
            {
                line = I18n.format(line);
                String[] split = line.split("\n");

                for (String str : split)
                {
                    this.lines.add(str);
                }
            }
        }

        public List<String> getLines()
        {
            return this.lines;
        }

        public boolean isMouseOver(int mouseX, int mouseY)
        {
            return mouseX >= this.x && mouseX <= (this.x + this.width) && mouseY >= this.y && mouseY <= (this.y + this.height);
        }
    }
}
