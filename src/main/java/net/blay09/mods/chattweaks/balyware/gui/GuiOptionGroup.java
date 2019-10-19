package net.blay09.mods.chattweaks.balyware.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiButton;

public class GuiOptionGroup {

    private final List<GuiCheckBox> options = new ArrayList<>();

    public GuiOptionGroup(GuiCheckBox... options) {
        Collections.addAll(this.options, options);
    }

    public void actionPerformed(@Nullable GuiButton button) {
        if(button instanceof GuiCheckBox && options.contains(button)) {
            for(GuiCheckBox option : options) {
                if(option != button) {
                    option.setIsChecked(false);
                }
            }
        }
    }

}
