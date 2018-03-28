package net.blay09.mods.chattweaks.config.options;

import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import net.blay09.mods.chattweaks.LiteModChatTweaks;
import net.blay09.mods.chattweaks.util.JsonUtils;

public class ConfigStringList extends ConfigBase
{
    private final ImmutableList<String> defaultValues;
    private final List<String> values = new ArrayList<>();

    public ConfigStringList(String name, ImmutableList<String> defaultValue, String comment)
    {
        super(ConfigType.STRING_LIST, name, comment);

        this.defaultValues = defaultValue;
        this.values.addAll(defaultValue);
    }

    public List<String> getValues()
    {
        return this.values;
    }

    public ImmutableList<String> getDefaultValues()
    {
        return this.defaultValues;
    }

    public void setValues(List<String> values)
    {
        this.values.clear();
        this.values.addAll(values);
    }

    @Override
    public String getStringValue()
    {
        return "";
    }

    public String getButtonDisplayString(final int maxWidth)
    {
        return getClampedDisplayStringOf(this.values, maxWidth, "[ ", " ]");
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
                this.values.clear();
                this.values.addAll(JsonUtils.getAsStringList(element.getAsJsonArray()));
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
        return JsonUtils.getAsJsonArray(this.values);
    }

    public static String getClampedDisplayStringOf(List<String> list, int maxWidth, String prefix, String suffix)
    {
        StringBuilder sb = new StringBuilder(128);
        sb.append(prefix);
        int width = prefix.length() + suffix.length();
        final int size = list.size();

        if (size > 0)
        {
            for (int i = 0; i < size && width < maxWidth; i++)
            {
                if (i > 0)
                {
                    sb.append(", ");
                    width += 2;
                }

                String str = list.get(i);
                final int len = str.length();
                int end = Math.min(len, maxWidth - width);

                if (end < len)
                {
                    end = Math.max(0, Math.min(len, maxWidth - width - 3));

                    if (end >= 1)
                    {
                        sb.append(str.substring(0, end));
                    }

                    sb.append("...");
                    width += end + 3;
                }
                else
                {
                    sb.append(str);
                    width += len;
                }
            }
        }
        else
        {
            sb.append("<empty>");
        }

        sb.append(suffix);
        return sb.toString();
    }
}
