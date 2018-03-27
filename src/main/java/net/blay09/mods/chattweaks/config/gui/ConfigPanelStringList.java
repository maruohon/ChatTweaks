package net.blay09.mods.chattweaks.config.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.lwjgl.input.Keyboard;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.blay09.mods.chattweaks.config.gui.button.ButtonGeneric;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerStringListAction;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerStringListAction.Type;
import net.blay09.mods.chattweaks.config.options.ConfigBase;
import net.blay09.mods.chattweaks.config.options.ConfigStringList;
import net.minecraft.util.text.TextFormatting;

public class ConfigPanelStringList extends ConfigPanelSub
{
    protected static final String BUTTON_LABEL_ADD = TextFormatting.DARK_GREEN + "+" + TextFormatting.RESET;
    protected static final String BUTTON_LABEL_REMOVE = TextFormatting.DARK_RED + "-" + TextFormatting.RESET;
    protected static final String BUTTON_LABEL_UP = TextFormatting.YELLOW + "^" + TextFormatting.RESET;
    protected static final String BUTTON_LABEL_DOWN = TextFormatting.YELLOW + "v" + TextFormatting.RESET;
    private final ConfigStringList config;
    private final List<ConfigTextField> textFields = new ArrayList<>();
    private ConfigPanelHost host;

    public ConfigPanelStringList(ConfigStringList config, String title, ChatTweaksConfigPanel parent, ConfigPanelSub parentSub)
    {
        super(title, parent, parentSub);

        this.config = config;
    }

    @Override
    protected Collection<ConfigBase> getConfigs()
    {
        // Dummy - not used
        return Collections.emptyList();
    }

    @Override
    public void onPanelHidden()
    {
        Keyboard.enableRepeatEvents(false);

        super.onPanelHidden();
    }

    @Override
    public void clearOptions()
    {
        super.clearOptions();

        this.textFields.clear();
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
        final int size = this.config.getValues().size();
        int id = 0;
        Keyboard.enableRepeatEvents(true);

        for (int i = 0; i < size; i++)
        {
            String str = this.config.getValues().get(i);
            ConfigTextField field = this.createTextField(i, x, y + 1, fieldWidth, configHeight - 3, 256);
            field.setText(str);

            this.addButton(new ButtonGeneric(id++, x + fieldWidth + 6, y, 20, 20, BUTTON_LABEL_ADD), this.createActionListener(Type.ADD, i));
            this.addButton(new ButtonGeneric(id++, x + fieldWidth + 28, y, 20, 20, BUTTON_LABEL_REMOVE), this.createActionListener(Type.REMOVE, i));
            this.addButton(new ButtonGeneric(id++, x + fieldWidth + 50, y, 20, 20, BUTTON_LABEL_UP), this.createActionListener(Type.MOVE_UP, i));
            this.addButton(new ButtonGeneric(id++, x + fieldWidth + 72, y, 20, 20, BUTTON_LABEL_DOWN), this.createActionListener(Type.MOVE_DOWN, i));

            y += configHeight + 1;
        }

        this.addButton(new ButtonGeneric(id++, x + fieldWidth + 6, y, 20, 20, BUTTON_LABEL_ADD), this.createActionListener(Type.ADD, size));
    }

    private ButtonListenerStringListAction<ButtonGeneric> createActionListener(ButtonListenerStringListAction.Type type, int index)
    {
        return new ButtonListenerStringListAction<>(type, index, this.config, this);
    }

    public void reCreateOptions()
    {
        this.addOptions(this.host);
    }

    public void saveFields()
    {
        this.handleTextFields();
    }

    private ConfigTextField createTextField(int index, int x, int y, int width, int height, int stringMaxLength)
    {
        ConfigTextField field = this.addTextField(index, x, y, width, height);
        field.getNativeTextField().setMaxStringLength(stringMaxLength);
        this.textFields.add(field);
        return field;
    }

    @Override
    protected boolean handleTextFields()
    {
        boolean dirty = false;
        final int size = Math.min(this.textFields.size(), this.config.getValues().size());

        for (int i = 0; i < size; i++)
        {
            ConfigTextField field = this.textFields.get(i);
            String newValue = field.getText();

            if (newValue.equals(this.config.getValues().get(i)) == false)
            {
                this.config.getValues().set(i, newValue);
                dirty = true;
            }
        }

        return dirty;
    }
}
