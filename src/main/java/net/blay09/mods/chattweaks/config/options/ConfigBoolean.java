package net.blay09.mods.chattweaks.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.blay09.mods.chattweaks.LiteModChatTweaks;

public class ConfigBoolean extends ConfigBase
{
    private final boolean defaultValue;
    private boolean value;

    public ConfigBoolean(String name, boolean defaultValue, String comment)
    {
        super(ConfigType.BOOLEAN, name, comment);

        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public boolean getValue()
    {
        return this.value;
    }

    public boolean getDefaultValue()
    {
        return this.defaultValue;
    }

    public void setValue(boolean value)
    {
        this.value = value;
    }

    @Override
    public String getStringValue()
    {
        return String.valueOf(this.value);
    }

    @Override
    public void setValueFromString(String value)
    {
        this.value = Boolean.parseBoolean(value);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonPrimitive())
            {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                this.value = primitive.getAsBoolean();
            }
            else
            {
                LiteModChatTweaks.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        }
        catch (Exception e)
        {
            LiteModChatTweaks.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }

    @Override
    public JsonElement getAsJsonElement()
    {
        return new JsonPrimitive(this.value);
    }
}
