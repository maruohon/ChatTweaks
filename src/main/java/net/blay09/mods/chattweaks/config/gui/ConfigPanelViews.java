package net.blay09.mods.chattweaks.config.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.blay09.mods.chattweaks.ChatViewManager;
import net.blay09.mods.chattweaks.chat.ChatView;
import net.blay09.mods.chattweaks.config.gui.button.ButtonGeneric;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerListAction;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerPanelSelection;
import net.minecraft.util.text.TextFormatting;

public class ConfigPanelViews extends ConfigPanelListBase<ChatView>
{
    private static final Supplier<ChatView> ENTRY_FACTORY = new Supplier<ChatView>()
    {
        @Override
        public ChatView get()
        {
            return new ChatView("new view");
        }
    };
    private final List<ChatView> list = new ArrayList<>();

    public ConfigPanelViews(ChatTweaksConfigPanel parent)
    {
        super("Views", parent, null);

        this.list.clear();
        this.list.addAll(ChatViewManager.getViews());
    }

    @Override
    protected List<ChatView> getList()
    {
        return this.list;
    }

    @Override
    public void reCreateOptions()
    {
        ChatViewManager.removeAllChatViews();

        for (ChatView view : this.list)
        {
            if (ChatViewManager.getChatView(view.getName()) != null)
            {
                StringBuilder sb = new StringBuilder();
                sb.append(view.getName()).append("-");

                for (int i = 0; i < 128; i++)
                {
                    String newName = sb.toString();

                    if (ChatViewManager.getChatView(newName) == null)
                    {
                        view.setName(newName);
                        ChatViewManager.addChatView(view);
                        break;
                    }

                    sb.append("-");
                }
            }
            else
            {
                ChatViewManager.addChatView(view);
            }
        }

        super.reCreateOptions();
    }

    @Override
    public void saveChanges()
    {
    }

    protected ButtonListenerListAction<ButtonGeneric, ChatView> createActionListener(ButtonListenerListAction.Type type, int index)
    {
        return new ButtonListenerListAction<>(type, index, this.list, ENTRY_FACTORY, this);
    }

    @Override
    protected void createListEntry(int index, int x, int y, int width, int height)
    {
        ChatView view = this.getList().get(index);
        String label = view.getName() + TextFormatting.AQUA + " [" + view.getMessageStyle().toString() + "]" + TextFormatting.RESET;
        ConfigPanelSub panelViewSettings = new ConfigPanelViewSettings(this.parentPanel, this, view.getName());
        ButtonListenerPanelSelection<ButtonGeneric> listener = new ButtonListenerPanelSelection<>(this.parentPanel, panelViewSettings);
        this.addButton(new ButtonGeneric(index, x, y, 360, 20, label), listener);
    }
}
