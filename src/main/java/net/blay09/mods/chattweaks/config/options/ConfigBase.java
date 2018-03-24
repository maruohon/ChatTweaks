package net.blay09.mods.chattweaks.config.options;

import javax.annotation.Nullable;
import com.google.gson.JsonElement;

public abstract class ConfigBase
{
    private final String name;
    private String comment;

    public ConfigBase(String name, String comment)
    {
        this.name = name;
        this.comment = comment;
    }

    public abstract ConfigType getType();

    public String getName()
    {
        return this.name;
    }

    @Nullable
    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public abstract String getStringValue();

    public abstract void setValueFromString(String value);

    public abstract void setValueFromJsonElement(JsonElement element);

    public abstract JsonElement getAsJsonElement();
}
