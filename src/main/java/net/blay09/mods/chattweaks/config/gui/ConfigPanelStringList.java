package net.blay09.mods.chattweaks.config.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import org.lwjgl.input.Keyboard;
import com.google.common.collect.ImmutableList;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.blay09.mods.chattweaks.config.gui.button.ButtonGeneric;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerListAction;
import net.blay09.mods.chattweaks.config.options.ConfigStringList;

public class ConfigPanelStringList extends ConfigPanelListBase<String>
{
    private static final Supplier<String> ENTRY_FACTORY = new Supplier<String>()
    {
        @Override
        public String get()
        {
            return "";
        }
    };
    private final ConfigStringList config;
    private final List<ConfigTextField> textFields = new ArrayList<>();
    private final List<String> originalValues;

    public ConfigPanelStringList(ConfigStringList config, String title, ChatTweaksConfigPanel parent, ConfigPanelSub parentSub)
    {
        super(title, parent, parentSub);

        this.config = config;
        this.originalValues = ImmutableList.copyOf(config.getValues());

        if (parentSub != null)
        {
            this.setConfigSaver(parentSub.getConfigSaver());
        }
    }

    @Override
    protected List<String> getList()
    {
        return this.config.getValues();
    }

    @Override
    public void onPanelHidden()
    {
        Keyboard.enableRepeatEvents(false);

        super.onPanelHidden();
    }

    @Override
    protected boolean handleStringLists()
    {
        return this.config.getValues().equals(this.originalValues) == false;
    }

    @Override
    public void clearOptions()
    {
        super.clearOptions();
        this.textFields.clear();
    }

    @Override
    protected void createListEntry(int index, int x, int y, int width, int height)
    {
        String str = this.getList().get(index);
        ConfigTextField field = this.createTextField(index, x, y + 1, width, height - 3, 256);
        field.setText(str);
    }

    @Override
    public void addOptions(ConfigPanelHost host)
    {
        super.addOptions(host);

        Keyboard.enableRepeatEvents(true);
    }

    protected ButtonListenerListAction<ButtonGeneric, String> createActionListener(ButtonListenerListAction.Type type, int index)
    {
        return new ButtonListenerListAction<>(type, index, this.getList(), ENTRY_FACTORY, this);
    }

    @Override
    public void saveChanges()
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
