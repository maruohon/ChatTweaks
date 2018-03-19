package net.blay09.mods.chattweaks.mixin;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

@Mixin(GuiScreen.class)
public interface IMixinGuiScreen
{
    @Accessor
    List<GuiButton> getButtonList();

    @Invoker
    <T extends GuiButton> T invokeAddButton(T buttonIn);
}
