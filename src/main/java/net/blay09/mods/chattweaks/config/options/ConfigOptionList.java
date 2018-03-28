package net.blay09.mods.chattweaks.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.blay09.mods.chattweaks.LiteModChatTweaks;

public class ConfigOptionList<T extends IConfigOptionListEntry<T>> extends ConfigBase
{
    private final T defaultValue;
    private T value;

    public ConfigOptionList(String name, T defaultValue, String comment)
    {
        super(ConfigType.OPTION_LIST, name, comment);

        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public T getValue()
    {
        return this.value;
    }

    public T getDefaultValue()
    {
        return this.defaultValue;
    }

    public void setValue(T value)
    {
        this.value = value;
    }

    @Override
    public String getStringValue()
    {
        return this.value.getStringValue();
    }

    @Override
    public void setValueFromString(String value)
    {
        try
        {
            this.value = this.value.fromString(value);
        }
        catch (Exception e)
        {
            LiteModChatTweaks.logger.warn("Failed to set value for config '{}' from the string '{}'", this.getName(), value, e);
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
                this.value = this.value.fromString(primitive.getAsString());
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
        return new JsonPrimitive(this.getStringValue());
    }
}
