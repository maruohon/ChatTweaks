package net.blay09.mods.chattweaks.config.gui.button;

import java.util.List;
import net.blay09.mods.chattweaks.config.gui.ChatTweaksConfigPanel;
import net.blay09.mods.chattweaks.config.gui.ConfigPanelStringList;
import net.blay09.mods.chattweaks.config.gui.ConfigPanelSub;
import net.blay09.mods.chattweaks.config.options.ConfigStringList;

public class ConfigOptionListeners
{
    public static class ButtonListenerPanelSelection<T extends ButtonBase> implements ButtonActionListener<T>
    {
        private final ChatTweaksConfigPanel mainPanel;
        private final ConfigPanelSub subPanel;

        public ButtonListenerPanelSelection(ChatTweaksConfigPanel mainPanel, ConfigPanelSub subPanel)
        {
            this.mainPanel = mainPanel;
            this.subPanel = subPanel;
        }

        @Override
        public void actionPerformed(T control)
        {
            this.mainPanel.setSelectedSubPanel(this.subPanel);
        }

        @Override
        public void actionPerformedWithButton(T control, int mouseButton)
        {
            this.actionPerformed(control);
        }
    }

    public static class ConfigOptionListenerDirtyChecker<T extends ButtonBase> implements ButtonActionListener<T>
    {
        private boolean dirty;

        @Override
        public void actionPerformed(T control)
        {
            this.dirty = true;
        }

        @Override
        public void actionPerformedWithButton(T control, int mouseButton)
        {
            this.actionPerformed(control);
        }

        public boolean isDirty()
        {
            return this.dirty;
        }

        public void resetDirty()
        {
            this.dirty = false;
        }
    }

    public static class ButtonListenerStringList<T extends ButtonBase> implements ButtonActionListener<T>
    {
        private final ChatTweaksConfigPanel mainPanel;

        public ButtonListenerStringList(ChatTweaksConfigPanel mainPanel)
        {
            this.mainPanel = mainPanel;
        }

        @Override
        public void actionPerformed(T control)
        {
            this.mainPanel.setSelectedSubPanel(control.id);
        }

        @Override
        public void actionPerformedWithButton(T control, int mouseButton)
        {
            this.actionPerformed(control);
        }
    }

    public static class ButtonListenerStringListAction<T extends ButtonBase> implements ButtonActionListener<T>
    {
        private final ConfigPanelStringList panel;
        private final Type type;
        private final ConfigStringList config;
        private final int index;

        public ButtonListenerStringListAction(Type type, int index, ConfigStringList config, ConfigPanelStringList panel)
        {
            this.type = type;
            this.index = index;
            this.config = config;
            this.panel = panel;
        }

        @Override
        public void actionPerformed(T control)
        {
            this.panel.saveFields();
            List<String> list = this.config.getValues();

            if (this.type == Type.ADD)
            {
                list.add(this.index, "");
            }
            else if (type == Type.REMOVE)
            {
                list.remove(this.index);
            }
            else if (type == Type.MOVE_UP)
            {
                if (this.index > 0)
                {
                    String prev = list.get(this.index - 1);
                    list.set(this.index - 1, list.get(this.index));
                    list.set(this.index, prev);
                }
            }
            else if (type == Type.MOVE_DOWN)
            {
                if (this.index < list.size() - 1)
                {
                    String next = list.get(this.index + 1);
                    list.set(this.index + 1, list.get(this.index));
                    list.set(this.index, next);
                }
            }

            this.panel.reCreateOptions();
        }

        @Override
        public void actionPerformedWithButton(T control, int mouseButton)
        {
            this.actionPerformed(control);
        }

        public enum Type
        {
            ADD,
            REMOVE,
            MOVE_UP,
            MOVE_DOWN;
        }
    }

    public static class ButtonListenerDummy<T extends ButtonBase> implements ButtonActionListener<T>
    {
        public ButtonListenerDummy()
        {
        }

        @Override
        public void actionPerformed(T control)
        {
        }

        @Override
        public void actionPerformedWithButton(T control, int mouseButton)
        {
        }
    }
}
