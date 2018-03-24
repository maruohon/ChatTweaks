package net.blay09.mods.chattweaks.config.options;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.blay09.mods.chattweaks.LiteModChatTweaks;

public class ConfigColor extends ConfigBase
{
    private final String defaultValue;
    private String hexString;
    private int color;

    public ConfigColor(String name, String defaultValue, String comment)
    {
        super(name, comment);

        this.defaultValue = defaultValue;
        this.hexString = defaultValue;
        this.color = getColor(defaultValue, 0);
    }

    @Override
    public ConfigType getType()
    {
        return ConfigType.HEX_STRING;
    }

    public String getDefaultValue()
    {
        return this.defaultValue;
    }

    public int getColor()
    {
        return this.color;
    }

    public void setValue(String value)
    {
        this.hexString = value;
        this.color = getColor(defaultValue, 0);
    }

    @Override
    public String getStringValue()
    {
        return this.hexString;
    }

    @Override
    public void setValueFromString(String value)
    {
        this.setValue(value);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonPrimitive())
            {
                JsonPrimitive primitive = element.getAsJsonPrimitive();
                this.setValue(primitive.getAsString());
            }
            else
            {
                LiteModChatTweaks.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        }
        catch (Exception e)
        {
            LiteModChatTweaks.logger.warn("Failed to read config value for {} from the JSON config", this.getName(), e);
        }
    }

    @Override
    public JsonElement getAsJsonElement()
    {
        return new JsonPrimitive(String.format("#%08X", this.color));
    }

    public static int getColor(String colorStr, int defaultColor)
    {
        Pattern pattern = Pattern.compile("(?:0x|#)([a-fA-F0-9]{1,8})");
        Matcher matcher = pattern.matcher(colorStr);

        if (matcher.matches())
        {
            try
            {
                return (int) Long.parseLong(matcher.group(1), 16);
            }
            catch (NumberFormatException e)
            {
                LiteModChatTweaks.logger.warn("Failed to parse hex color from '{}'", colorStr);
                return defaultColor;
            }
        }

        try
        {
            return Integer.parseInt(colorStr, 10);
        }
        catch (NumberFormatException e)
        {
            LiteModChatTweaks.logger.warn("Failed to parse integer color value from '{}'", colorStr);
            return defaultColor;
        }
    }
}
