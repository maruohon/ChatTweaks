package net.blay09.mods.chattweaks.config.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.blay09.mods.chattweaks.ChatViewManager;
import net.blay09.mods.chattweaks.chat.ChatView;
import net.blay09.mods.chattweaks.config.gui.button.ButtonGeneric;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListenerCallback;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerListAction;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerListAction.Type;
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
    }

    @Override
    protected List<ChatView> getList()
    {
        return this.list;
    }

    @Override
    public void clearOptions()
    {
        this.list.clear();
        this.list.addAll(ChatViewManager.getViews());

        super.clearOptions();
    }

    @Override
    public void saveChanges()
    {
    }

    protected ButtonListenerListAction<ButtonGeneric, ChatView> createActionListener(ButtonListenerListAction.Type type, int index)
    {
        return new ButtonListenerListAction<>(type, index, this.list, ENTRY_FACTORY, this, new ViewCallback(this));
    }

    @Override
    protected void createListEntry(int index, int x, int y, int width, int height)
    {
        if (index < this.list.size() && this.list.get(index) != null)
        {
            ChatView view = this.list.get(index);
            String label = view.getName() + TextFormatting.AQUA + " [" + view.getMessageStyle().toString() + "]" + TextFormatting.RESET;
            ConfigPanelSub panelViewSettings = new ConfigPanelViewSettings(this.parentPanel, this, view);
            ButtonListenerPanelSelection<ButtonGeneric> listener = new ButtonListenerPanelSelection<>(this.parentPanel, panelViewSettings);
            this.addButton(new ButtonGeneric(index, x, y, 360, 20, label), listener);
        }
    }

    private static class ViewCallback implements ConfigOptionListenerCallback<ChatView>
    {
        private final ConfigPanelViews panel;

        public ViewCallback(ConfigPanelViews panel)
        {
            this.panel = panel;
        }

        @Override
        public void onListAction(ButtonListenerListAction.Type action, ChatView view)
        {
            if (view != null)
            {
                if (action == Type.ADD)
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

                    ChatViewManager.save();
                }
                else if (action == Type.REMOVE)
                {
                    ChatViewManager.removeChatView(view);
                    ChatViewManager.save();
                }
                else if (action == Type.MOVE_DOWN || action == Type.MOVE_UP)
                {
                    ChatViewManager.removeAllChatViews();

                    for (ChatView v : this.panel.getList())
                    {
                        ChatViewManager.addChatView(v);
                    }

                    ChatViewManager.save();
                }
            }
        }
    }
}
