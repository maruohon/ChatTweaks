package net.blay09.mods.chattweaks.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.blay09.mods.chattweaks.LiteModChatTweaks;

public class ConfigInteger extends ConfigBase
{
    private final int defaultValue;
    private int value;

    public ConfigInteger(String name, int defaultValue, String comment)
    {
        super(name, comment);

        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public ConfigType getType()
    {
        return ConfigType.INTEGER;
    }

    public int getValue()
    {
        return this.value;
    }

    public int getDefaultValue()
    {
        return this.defaultValue;
    }

    public void setValue(int value)
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
        try
        {
            this.value = Integer.parseInt(value);
        }
        catch (Exception e)
        {
            LiteModChatTweaks.logger.warn("Failed to set config value for {} from the string '{}'", this.getName(), value, e);
        }
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonPrimitive())
            {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                this.value = primitive.getAsInt();
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
