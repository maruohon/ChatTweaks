package net.blay09.mods.chattweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.gui.GuiTextField;

@Mixin(GuiTextField.class)
public interface IMixinGuiTextField
{
    @Accessor
    int getWidth();

    @Accessor
    int getHeight();

    @Accessor
    void setWidth(int width);
}
