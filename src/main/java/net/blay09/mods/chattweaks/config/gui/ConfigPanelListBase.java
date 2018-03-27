package net.blay09.mods.chattweaks.config.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.blay09.mods.chattweaks.config.gui.button.ButtonGeneric;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerListAction;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerListAction.Type;
import net.blay09.mods.chattweaks.config.options.ConfigBase;
import net.minecraft.util.text.TextFormatting;

public abstract class ConfigPanelListBase<T> extends ConfigPanelSub
{
    protected static final String BUTTON_LABEL_ADD = TextFormatting.DARK_GREEN + "+" + TextFormatting.RESET;
    protected static final String BUTTON_LABEL_REMOVE = TextFormatting.DARK_RED + "-" + TextFormatting.RESET;
    protected static final String BUTTON_LABEL_UP = TextFormatting.YELLOW + "^" + TextFormatting.RESET;
    protected static final String BUTTON_LABEL_DOWN = TextFormatting.YELLOW + "v" + TextFormatting.RESET;
    protected ConfigPanelHost host;

    public ConfigPanelListBase(String title, ChatTweaksConfigPanel parent, ConfigPanelSub parentSubPanel)
    {
        super(title, parent, parentSubPanel);
    }

    protected abstract List<T> getList();

    protected abstract void createListEntry(int index, int x, int y, int width, int height);

    protected abstract ButtonListenerListAction<ButtonGeneric, T> createActionListener(ButtonListenerListAction.Type type, int index);

    @Override
    protected Collection<ConfigBase> getConfigs()
    {
        // Dummy - not used
        return Collections.emptyList();
    }

    public void reCreateOptions()
    {
        this.addOptions(this.host);
    }

    public void saveChanges()
    {
    }

    @Override
    public void addOptions(ConfigPanelHost host)
    {
        this.clearOptions();

        this.host = host;

        int x = 10;
        int y = 10;
        int configHeight = 20;
        int fieldWidth = 360;
        final int size = this.getList().size();
        int id = 0;

        for (int i = 0; i < size; i++)
        {
            this.createListEntry(i, x, y, fieldWidth, configHeight);

            this.addButton(new ButtonGeneric(id++, x + fieldWidth + 6, y, 20, 20, BUTTON_LABEL_ADD), this.createActionListener(Type.ADD, i));
            this.addButton(new ButtonGeneric(id++, x + fieldWidth + 28, y, 20, 20, BUTTON_LABEL_REMOVE), this.createActionListener(Type.REMOVE, i));
            this.addButton(new ButtonGeneric(id++, x + fieldWidth + 50, y, 20, 20, BUTTON_LABEL_UP), this.createActionListener(Type.MOVE_UP, i));
            this.addButton(new ButtonGeneric(id++, x + fieldWidth + 72, y, 20, 20, BUTTON_LABEL_DOWN), this.createActionListener(Type.MOVE_DOWN, i));

            y += configHeight + 1;
        }

        this.addButton(new ButtonGeneric(id++, x + fieldWidth + 6, y, 20, 20, BUTTON_LABEL_ADD), this.createActionListener(Type.ADD, size));
    }
}
