package net.blay09.mods.chattweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.gui.GuiNewChat;

@Mixin(GuiNewChat.class)
public interface IMixinGuiNewChat
{
    @Accessor
    int getScrollPos();

    @Accessor
    boolean getIsScrolled();

    @Accessor
    void setScrollPos(int scrollPos);

    @Accessor
    void setIsScrolled(boolean isScrolled);
}
