package net.blay09.mods.chattweaks.config.gui.button;

import javax.annotation.Nullable;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerListAction;

public interface ConfigOptionListenerCallback<L>
{
    /**
     * Callback for when a list item is operated on.
     * @param action The type of action that happened
     * @param listEntry The list entry that was operated on
     */
    void onListAction(ButtonListenerListAction.Type action, @Nullable L listEntry);
}
