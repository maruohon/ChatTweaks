package net.blay09.mods.chattweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.blay09.mods.chattweaks.LiteModChatTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

@Mixin(Minecraft.class)
public class MixinMinecraft
{
    @ModifyVariable(method = "displayGuiScreen", at = @At("HEAD"), argsOnly = true)
    private GuiScreen onDisplayGuiScreen(GuiScreen gui)
    {
        return LiteModChatTweaks.onOpenGui(gui);
    }
}
