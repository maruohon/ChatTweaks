package net.blay09.mods.chattweaks.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.blay09.mods.chattweaks.LiteModChatTweaks;
import net.blay09.mods.chattweaks.mixininterfaces.IMixinGuiIngame;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;

@Mixin(GuiIngame.class)
public class MixinGuiIngame extends Gui implements IMixinGuiIngame
{
    @Shadow
    @Mutable
    @Final
    private GuiNewChat persistantChatGUI;

    @Override
    public void setPersistentChatGUI(GuiNewChat gui)
    {
        this.persistantChatGUI = gui;
    }

    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    public void onRenderGameOverlayPost(float partialTicks, CallbackInfo ci)
    {
        LiteModChatTweaks.onRenderGameOverlayPost(partialTicks);
    }
}
