package net.blay09.mods.chattweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.util.text.TextComponentString;

@Mixin(TextComponentString.class)
public interface IMixinTextComponentString
{
    /*
    @Shadow
    @Final
    @Mutable
    private String text;

    public void setText(String textIn)
    {
        this.text = textIn;
    }
    */

    @Accessor
    void setText(String text);
}
