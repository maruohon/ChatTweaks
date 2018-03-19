package net.blay09.mods.chattweaks.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.blay09.mods.chattweaks.LiteModChatTweaks;
import net.blay09.mods.chattweaks.config.interfaces.ConfigType;
import net.blay09.mods.chattweaks.config.interfaces.IConfigBoolean;
import net.blay09.mods.chattweaks.config.interfaces.IConfigGeneric;
import net.blay09.mods.chattweaks.config.interfaces.IConfigOptionList;
import net.blay09.mods.chattweaks.config.interfaces.IConfigOptionListEntry;
import net.blay09.mods.chattweaks.util.JsonUtils;

public class ConfigOption implements IConfigGeneric, IConfigBoolean, IConfigOptionList
{
    private final String name;
    private final ConfigType type;
    private String comment;
    private boolean valueBoolean;
    private int valueInteger;
    private String valueString;
    private String[] valueStringArray;
    private IConfigOptionListEntry valueOptionList;

    public ConfigOption(String name, boolean defaultValue, String comment)
    {
        this.type = ConfigType.BOOLEAN;
        this.name = name;
        this.valueBoolean = defaultValue;
        this.comment = comment;
    }

    public ConfigOption(String name, int defaultValue, String comment)
    {
        this.type = ConfigType.INTEGER;
        this.name = name;
        this.valueInteger = defaultValue;
        this.comment = comment;
    }

    public ConfigOption(String name, String defaultValue, boolean isColor, String comment)
    {
        this.type = ConfigType.HEX_STRING;
        this.name = name;
        this.valueString = defaultValue;
        this.valueInteger = isColor ? getColor(defaultValue, 0) : 0;
        this.comment = comment;
    }

    public ConfigOption(String name, String[] defaultValue, String comment)
    {
        this.type = ConfigType.STRING_ARRAY;
        this.name = name;
        this.valueStringArray = defaultValue;
        this.comment = comment;
    }

    public ConfigOption(String name, IConfigOptionListEntry defaultValue, String comment)
    {
        this.type = ConfigType.OPTION_LIST;
        this.name = name;
        this.valueOptionList = defaultValue;
        this.comment = comment;
    }

    @Override
    public ConfigType getType()
    {
        return this.type;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    @Nullable
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
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

    public int getIntegerValue()
    {
        return this.valueInteger;
    }

    public void setIntegerValue(int value)
    {
        this.valueInteger = value;
    }

    @Override
    public IConfigOptionListEntry getOptionListValue()
    {
        return this.valueOptionList;
    }

    @Override
    public void setOptionListValue(IConfigOptionListEntry value)
    {
        this.valueOptionList = value;
    }

    public String[] getStringArray()
    {
        return this.valueStringArray;
    }

    public String getStringValue()
    {
        switch (this.type)
        {
            case BOOLEAN:       return String.valueOf(this.valueBoolean);
            case INTEGER:       return String.valueOf(this.valueInteger);
            case HEX_STRING:    return String.format("0x%08X", this.valueInteger);
            case OPTION_LIST:   return this.valueOptionList.getStringValue();
            case STRING:
            default:            return this.valueString;
        }
    }

    public void setStringValue(String value)
    {
        this.valueString = value;
    }

    public void setColorValue(String str)
    {
        this.valueInteger = getColor(str, 0);
    }

    @Override
    public void setValueFromString(String value)
    {
        try
        {
            switch (this.type)
            {
                case BOOLEAN:
                    this.valueBoolean = Boolean.getBoolean(value);
                    break;
                case INTEGER:
                    this.valueInteger = Integer.parseInt(value);
                    break;
                case STRING:
                    this.valueString = value;
                    break;
                case HEX_STRING:
                    this.valueInteger = getColor(value, 0);
                    break;
                case OPTION_LIST:
                    this.valueOptionList = this.valueOptionList.fromString(value);
                    break;
                default:
            }
        }
        catch (Exception e)
        {
            LiteModChatTweaks.logger.warn("Failed to read config value for {} from the JSON config", this.getName(), e);
        }
    }

    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (this.type == ConfigType.STRING_ARRAY && element.isJsonArray())
            {
                this.valueStringArray = JsonUtils.getAsStringArray(element.getAsJsonArray());
            }
            else if (element.isJsonPrimitive())
            {
                JsonPrimitive primitive = element.getAsJsonPrimitive();

                switch (this.type)
                {
                    case BOOLEAN:
                        this.valueBoolean = primitive.getAsBoolean();
                        break;
                    case INTEGER:
                        this.valueInteger = primitive.getAsInt();
                        break;
                    case STRING:
                        this.valueString = primitive.getAsString();
                        break;
                    case HEX_STRING:
                        this.valueInteger = getColor(primitive.getAsString(), 0);
                        break;
                    case OPTION_LIST:
                        this.valueOptionList = this.valueOptionList.fromString(primitive.getAsString());
                        break;
                    default:
                }
            }
        }
        catch (Exception e)
        {
            LiteModChatTweaks.logger.warn("Failed to read config value for {} from the JSON config", this.getName(), e);
        }
    }

    public JsonElement getAsJsonElement()
    {
        switch (this.type)
        {
            case BOOLEAN:       return new JsonPrimitive(this.getBooleanValue());
            case INTEGER:       return new JsonPrimitive(this.getIntegerValue());
            case STRING:        return new JsonPrimitive(this.getStringValue());
            case HEX_STRING:    return new JsonPrimitive(String.format("0x%08X", this.getIntegerValue()));
            case OPTION_LIST:   return new JsonPrimitive(this.getStringValue());
            case STRING_ARRAY:  return JsonUtils.getAsJsonArray(this.valueStringArray);
            default:
        }

        return new JsonPrimitive(this.getStringValue());
    }

    public static int getColor(String colorStr, int defaultColor)
    {
        Pattern pattern = Pattern.compile("(?:0x|#)([a-fA-F0-9]{1,8})");
        Matcher matcher = pattern.matcher(colorStr);

        if (matcher.matches())
        {
            try { return (int) Long.parseLong(matcher.group(1), 16); }
            catch (NumberFormatException e) { return defaultColor; }
        }

        try { return Integer.parseInt(colorStr, 10); }
        catch (NumberFormatException e) { return defaultColor; }
    }
}
