package net.blay09.mods.chattweaks.config;

import net.blay09.mods.chattweaks.config.gui.KeybindMulti;
import net.blay09.mods.chattweaks.config.interfaces.ConfigType;
import net.blay09.mods.chattweaks.config.interfaces.IConfigBoolean;
import net.blay09.mods.chattweaks.config.interfaces.IHotkey;
import net.blay09.mods.chattweaks.config.interfaces.IKeybind;

public enum FeatureToggle implements IConfigBoolean, IHotkey
{
    TWEAK_FAST_BLOCK_PLACEMENT      ("tweakFastBlockPlacement",             false, "X,F", "Enables fast/convenient block placement when moving the cursor over new blocks"),
    TWEAK_FLEXIBLE_BLOCK_PLACEMENT  ("tweakFlexibleBlockPlacement",         false, "X,L", "Enables placing blocks in different orientations while holding down the keybind"),
    TWEAK_HOTBAR_SWAP               ("tweakHotbarSwap",                     false, "X,H", "Enables the hotbar swapping feature"),
    TWEAK_NO_FALLING_BLOCK_RENDER   ("tweakNoFallingBlockEntityRendering",  false, "X,R", "If enabled, then falling block entities won't be rendered at all"),
    TWEAK_NO_ITEM_SWITCH_COOLDOWN   ("tweakNoItemSwitchRenderCooldown",     false, "X,I", "If true, then there won't be any cooldown/equip\nanimation when switching the held item or using the item."),
    TWEAK_NO_LIGHT_UPDATES          ("tweakNoLightUpdates",                 false, "X,N", "If enabled, disables client-side light updates"),
    TWEAK_SHULKERBOX_STACKING       ("tweakEmptyShulkerBoxesStack",         false, "X,S", "Enables empty Shulker Boxes stacking up to 64");

    private final String name;
    private final String comment;
    private final String toggleMessage;
    private boolean valueBoolean;
    private IKeybind keybind;

    private FeatureToggle(String name, boolean defaultValue, String defaultHotkey, String comment)
    {
        this.name = name;
        this.valueBoolean = defaultValue;
        this.comment = comment;
        this.toggleMessage = "Toggled " + splitCamelCase(this.name.substring(5)) + " %s";
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey);
    }

    @Override
    public ConfigType getType()
    {
        return ConfigType.BOOLEAN;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getStringValue()
    {
        return String.valueOf(this.valueBoolean);
    }

    @Override
    public String getComment()
    {
        return comment != null ? comment : "";
    }

    public String getToggleMessage()
    {
        return this.toggleMessage;
    }

    @Override
    public IKeybind getKeybind()
    {
        return this.keybind;
    }

    @Override
    public void setKeybind(IKeybind keybind)
    {
        this.keybind = keybind;
    }

    @Override
    public boolean getBooleanValue()
    {
        return this.valueBoolean;
    }

    @Override
    public void setBooleanValue(boolean value)
    {
        this.valueBoolean = value;
    }

    // https://stackoverflow.com/questions/2559759/how-do-i-convert-camelcase-into-human-readable-names-in-java
    public static String splitCamelCase(String str)
    {
        return str.replaceAll(
           String.format("%s|%s|%s",
              "(?<=[A-Z])(?=[A-Z][a-z])",
              "(?<=[^A-Z])(?=[A-Z])",
              "(?<=[A-Za-z])(?=[^A-Za-z])"
           ),
           " "
        );
     }
}
