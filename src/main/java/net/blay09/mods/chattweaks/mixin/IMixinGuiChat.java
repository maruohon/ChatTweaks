package net.blay09.mods.chattweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.TabCompleter;

@Mixin(GuiChat.class)
public interface IMixinGuiChat
{
    @Accessor
    TabCompleter getTabCompleter();

    @Accessor
    GuiTextField getInputField();

    @Accessor
    String getDefaultInputFieldText();
}
