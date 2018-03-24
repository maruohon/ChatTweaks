package net.blay09.mods.chattweaks.config.options;

import com.google.gson.JsonElement;
import net.blay09.mods.chattweaks.LiteModChatTweaks;
import net.blay09.mods.chattweaks.util.JsonUtils;

public class ConfigStringArray extends ConfigBase
{
    private final String[] defaultValue;
    private String[] value;

    public ConfigStringArray(String name, String[] defaultValue, String comment)
    {
        super(name, comment);

        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public ConfigType getType()
    {
        return ConfigType.STRING_ARRAY;
    }

    public String[] getValue()
    {
        return this.value;
    }

    public String[] getDefaultValue()
    {
        return this.defaultValue;
    }

    public void setValue(String[] value)
    {
        this.value = value;
    }

    @Override
    public String getStringValue()
    {
        return "";
    }

    @Override
    public void setValueFromString(String value)
    {
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonArray())
            {
                this.value = JsonUtils.getAsStringArray(element.getAsJsonArray());
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
        return JsonUtils.getAsJsonArray(this.value);
    }
}
