package net.blay09.mods.chattweaks.config.gui.button;

import java.util.List;
import java.util.function.Supplier;
import net.blay09.mods.chattweaks.config.gui.ChatTweaksConfigPanel;
import net.blay09.mods.chattweaks.config.gui.ConfigPanelListBase;
import net.blay09.mods.chattweaks.config.gui.ConfigPanelSub;

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

    public static class ButtonListenerListAction<T extends ButtonBase, L> implements ButtonActionListener<T>
    {
        private final ConfigPanelListBase<L> panel;
        private final Type type;
        private final List<L> list;
        private final Supplier<L> factory;
        private final int index;

        public ButtonListenerListAction(Type type, int index, List<L> list, Supplier<L> factory, ConfigPanelListBase<L> panel)
        {
            this.type = type;
            this.index = index;
            this.list = list;
            this.factory = factory;
            this.panel = panel;
        }

        @Override
        public void actionPerformed(T control)
        {
            this.panel.saveChanges();
            List<L> list = this.list;

            if (this.type == Type.ADD)
            {
                list.add(this.index, this.factory.get());
            }
            else if (type == Type.REMOVE)
            {
                list.remove(this.index);
            }
            else if (type == Type.MOVE_UP)
            {
                if (this.index > 0)
                {
                    L prev = list.get(this.index - 1);
                    list.set(this.index - 1, list.get(this.index));
                    list.set(this.index, prev);
                }
            }
            else if (type == Type.MOVE_DOWN)
            {
                if (this.index < list.size() - 1)
                {
                    L next = list.get(this.index + 1);
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
