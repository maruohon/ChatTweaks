package net.blay09.mods.chattweaks.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.blay09.mods.chattweaks.LiteModChatTweaks;

public class ConfigString extends ConfigBase
{
    private final String defaultValue;
    private String value;

    public ConfigString(String name, String defaultValue, String comment)
    {
        super(ConfigType.STRING, name, comment);

        if (defaultValue == null)
        {
            defaultValue = "";
        }

        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public String getValue()
    {
        return this.value;
    }

    public String getDefaultValue()
    {
        return this.defaultValue;
    }

    public void setValue(String value)
    {
        this.value = value != null ? value : "";
    }

    @Override
    public String getStringValue()
    {
        return this.value;
    }

    @Override
    public void setValueFromString(String value)
    {
        this.value = value != null ? value : "";
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonPrimitive())
            {
                this.setValue(element.getAsJsonPrimitive().getAsString());
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
