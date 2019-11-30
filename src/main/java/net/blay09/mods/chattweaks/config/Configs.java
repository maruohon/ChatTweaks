package net.blay09.mods.chattweaks.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import com.mumfrey.liteloader.core.LiteLoader;
import net.blay09.mods.chattweaks.LiteModChatTweaks;
import net.blay09.mods.chattweaks.chat.emotes.EmoteRegistry;
import net.blay09.mods.chattweaks.chat.emotes.bttv.BTTVChannelEmotes;
import net.blay09.mods.chattweaks.chat.emotes.bttv.BTTVEmotes;
import net.blay09.mods.chattweaks.chat.emotes.ffz.FFZChannelEmotes;
import net.blay09.mods.chattweaks.chat.emotes.ffz.FFZEmotes;
import net.blay09.mods.chattweaks.chat.emotes.localfile.LocalEmotes;
import net.blay09.mods.chattweaks.chat.emotes.twitch.TwitchEmotesAPI;
import net.blay09.mods.chattweaks.chat.emotes.twitch.TwitchGlobalEmotes;
import net.blay09.mods.chattweaks.chat.emotes.twitch.TwitchSubscriberEmotes;
import net.blay09.mods.chattweaks.config.options.ConfigBase;
import net.blay09.mods.chattweaks.config.options.ConfigBoolean;
import net.blay09.mods.chattweaks.config.options.ConfigColor;
import net.blay09.mods.chattweaks.config.options.ConfigInteger;
import net.blay09.mods.chattweaks.config.options.ConfigString;
import net.blay09.mods.chattweaks.config.options.ConfigStringList;
import net.blay09.mods.chattweaks.reference.Reference;
import net.blay09.mods.chattweaks.util.JsonUtils;

public class Configs
{
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";
    private static final SimpleDateFormat DEFAULT_TIMESTAMP_FORMAT = new SimpleDateFormat("[HH:mm]");
    public static SimpleDateFormat timestampFormat = DEFAULT_TIMESTAMP_FORMAT;

    public static class Generic
    {
        public static final ConfigBoolean ALTERNATE_BACKGROUND      = new ConfigBoolean("alternateBackgroundColor", true, "Should uneven lines alternate their background color for easier reading?");
        public static final ConfigBoolean CHAT_TEXT_OPACITY         = new ConfigBoolean("chatTextFullOpacity",  true, "Vanilla Minecraft makes the text in chat transparent too,\nwhen opacity is set. Set this to false to restore that behaviour.");
        public static final ConfigBoolean DISABLE_UNDERLINES        = new ConfigBoolean("disableUnderlines",  false, "Set to true to disable underlines in all chat\nmessages, because they simply don't look good.");
        public static final ConfigBoolean EMOTE_TAB_COMPLETION      = new ConfigBoolean("emoteTabCompletion", false, "Should emotes be considered in tab completion?");
        public static final ConfigBoolean HIDE_EMOTES_MENU          = new ConfigBoolean("hideEmotesMenu", false, "Set to true to hide the emote menu button in the chat.");
        public static final ConfigBoolean HILIGHT_NAME              = new ConfigBoolean("highlightName", false, "If set to true, mentions of your Minecraft IGN will be highlighted in chat.");
        public static final ConfigStringList HILIGHT_STRINGS        = new ConfigStringList("highlightedWords", ImmutableList.of(), "List of words that are highlighted in chat.");
        public static final ConfigInteger LINE_SPACING              = new ConfigInteger("lineSpacing", 0, "Spacing between chat lines");
        public static final ConfigStringList LOCAL_EMOTE_ALIASES    = new ConfigStringList("localEmoteAliases", ImmutableList.of(), "List of aliases for local emotes,\nin alias=filename format. Example: :hmm:=thinking.png");
        public static final ConfigBoolean PREFER_NEW_MESSAGES       = new ConfigBoolean("smartViewNavigation", true, "When navigating between views, prefer views with new messages.");
        public static final ConfigBoolean SHOW_NEW_MESSAGE_OVERLAY  = new ConfigBoolean("showNewMessages",  true, "Highlights views with new messages red even when chat is closed.");
        public static final ConfigBoolean SMALLER_EMOTES            = new ConfigBoolean("smallerEmotes", false, "Should emotes be scaled down to perfectly fit into one line?");
        public static final ConfigString TIMESTAMP_FORMAT_STRING    = new ConfigString("timestampFormat", "[HH:mm]", "The format for the timestamp to be displayed in.");

        public static final ImmutableList<ConfigBase> OPTIONS = ImmutableList.of(
                ALTERNATE_BACKGROUND,
                CHAT_TEXT_OPACITY,
                DISABLE_UNDERLINES,
                EMOTE_TAB_COMPLETION,
                HIDE_EMOTES_MENU,
                HILIGHT_NAME,
                HILIGHT_STRINGS,
                LINE_SPACING,
                LOCAL_EMOTE_ALIASES,
                SHOW_NEW_MESSAGE_OVERLAY,
                PREFER_NEW_MESSAGES,
                SMALLER_EMOTES,
                TIMESTAMP_FORMAT_STRING);
    }

    public static class Theme
    {
        public static final ConfigColor BG_COLOR_1          = new ConfigColor("backgroundColor1", "#000000", "The background color to use for even line numbers in HEX.");
        public static final ConfigColor BG_COLOR_2          = new ConfigColor("backgroundColor2", "#111111", "The background color to use for uneven line numbers in HEX (if enabled).");
        public static final ConfigColor BG_COLOR_HILIGHT    = new ConfigColor("hilightColor", "#550000", "The background color to use for highlighted lines in HEX.");

        public static final ImmutableList<ConfigBase> OPTIONS = ImmutableList.of(BG_COLOR_1, BG_COLOR_2, BG_COLOR_HILIGHT);
    }

    public static class Emotes
    {
        public static final ConfigBoolean INCLUDE_TWITCH_PRIME_EMOTES   = new ConfigBoolean("includeTwitchPrimeEmotes", true, "Should Prime emotes (ex. KappaHD) be included with the Twitch Global Emotes?");
        public static final ConfigBoolean TWITCH_GLOBAL_EMOTES          = new ConfigBoolean("twitchGlobalEmotes", true, "Should the Twitch Global emotes (ex. Kappa) be enabled?");
        public static final ConfigBoolean TWITCH_SUBSCRIBER_EMOTES      = new ConfigBoolean("twitchSubscriberEmotes", true, "Should the Twitch Subscriber emotes (ex. geekPraise) be enabled?\nThis will increase the memory required by this mod!");
        public static final ConfigString TWITCH_SUBSCRIBER_EMOTE_REGEX  = new ConfigString("twitchSubscriberEmoteRegex", "[a-z0-9][a-z0-9]+[A-Z0-9].*", "The regex pattern to match for Twitch Subscriber Emotes to be included.\nBy default includes all that follow prefixCode convention.");
        public static final ConfigBoolean BTTV_EMOTES                   = new ConfigBoolean("BTTVEmotes", true, "Should the BTTV emotes (ex. AngelThump) be enabled?");
        public static final ConfigBoolean BTTV_CHANNEL_EMOTES           = new ConfigBoolean("BTTVChannelEmotes", false, "Should BTTV channel emotes be enabled?");
        public static final ConfigStringList BTTV_EMOTE_CHANNELS        = new ConfigStringList("BTTVEmoteChannels", ImmutableList.of("ZeekDaGeek"), "A list of channels to postInitLoad BTTV channel emotes from.");
        public static final ConfigBoolean FFZ_EMOTES                    = new ConfigBoolean("FFZEmotes", true, "Should the FrankerFaceZ emotes (ex. ZreknarF) be enabled?");
        public static final ConfigBoolean FFZ_CHANNEL_EMOTES            = new ConfigBoolean("FFZChannelEmotes", false, "Should the FrankerFaceZ channel emotes be enabled?");
        public static final ConfigStringList FFZ_EMOTE_CHANNELS         = new ConfigStringList("FFZEmoteChannels", ImmutableList.of("tehbasshunter"), "A list of channels to load FrankerFaceZ channel emotes from.");

        public static final ImmutableList<ConfigBase> OPTIONS = ImmutableList.of(
                BTTV_EMOTES,
                BTTV_EMOTE_CHANNELS,
                FFZ_EMOTES,
                FFZ_EMOTE_CHANNELS,
                INCLUDE_TWITCH_PRIME_EMOTES,
                TWITCH_GLOBAL_EMOTES,
                TWITCH_SUBSCRIBER_EMOTES,
                TWITCH_SUBSCRIBER_EMOTE_REGEX);
    }

    public static void load()
    {
        File configFile = new File(LiteLoader.getCommonConfigFolder(), CONFIG_FILE_NAME);

        if (configFile.exists() && configFile.isFile() && configFile.canRead())
        {
            JsonElement element = JsonUtils.parseJsonFile(configFile);

            if (element != null && element.isJsonObject())
            {
                JsonObject root = element.getAsJsonObject();

                readOptions(root, "Generic", Generic.OPTIONS);
                readOptions(root, "Emotes", Emotes.OPTIONS);
                readOptions(root, "Theme", Theme.OPTIONS);
            }
        }

        updateConfigs();
    }

    public static void updateConfigs()
    {
        try
        {
            timestampFormat = new SimpleDateFormat(Generic.TIMESTAMP_FORMAT_STRING.getStringValue());
        }
        catch (IllegalArgumentException e)
        {
            LiteModChatTweaks.logger.error("Invalid timestamp format - reverting to default");
            timestampFormat = DEFAULT_TIMESTAMP_FORMAT;
        }
    }

    public static void save()
    {
        File dir = LiteLoader.getCommonConfigFolder();

        if (dir.exists() && dir.isDirectory())
        {
            File configFile = new File(dir, CONFIG_FILE_NAME);
            FileWriter writer = null;
            JsonObject root = new JsonObject();

            writeOptions(root, "Generic", Generic.OPTIONS);
            writeOptions(root, "Emotes", Emotes.OPTIONS);
            writeOptions(root, "Theme", Theme.OPTIONS);

            try
            {
                writer = new FileWriter(configFile);
                writer.write(JsonUtils.GSON.toJson(root));
                writer.close();
            }
            catch (IOException e)
            {
                LiteModChatTweaks.logger.warn("Failed to write configs to file '{}'", configFile.getAbsolutePath(), e);
            }
            finally
            {
                try
                {
                    if (writer != null)
                    {
                        writer.close();
                    }
                }
                catch (Exception e)
                {
                    LiteModChatTweaks.logger.warn("Failed to close config file", e);
                }
            }
        }
    }

    public static void readOptions(JsonObject root, String category, ImmutableList<ConfigBase> options)
    {
        JsonObject obj = JsonUtils.getNestedObject(root, category, false);

        if (obj != null)
        {
            for (ConfigBase option : options)
            {
                if (obj.has(option.getName()))
                {
                    option.setValueFromJsonElement(obj.get(option.getName()));
                }
            }
        }
    }

    public static void writeOptions(JsonObject root, String category, ImmutableList<ConfigBase> options)
    {
        JsonObject obj = JsonUtils.getNestedObject(root, category, true);

        for (ConfigBase option : options)
        {
            obj.add(option.getName(), option.getAsJsonElement());
        }
    }

    public static void postInitLoad() {
        EmoteRegistry.reloadEmoticons();

        new Thread(() -> {
            EmoteRegistry.isLoading = true;

            try {
                TwitchEmotesAPI.loadEmoteSets();
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load Twitch emote set mappings.");
            }

            try {
                if (Emotes.TWITCH_GLOBAL_EMOTES.getValue()) {
                    new TwitchGlobalEmotes(Emotes.INCLUDE_TWITCH_PRIME_EMOTES.getValue());
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load Twitch global emotes: ", e);
            }

            try {
                if (Emotes.TWITCH_SUBSCRIBER_EMOTES.getValue()) {
                    new TwitchSubscriberEmotes(Emotes.TWITCH_SUBSCRIBER_EMOTE_REGEX.getStringValue());
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load Twitch subscriber emotes: ", e);
            }

            try {
                if (Emotes.BTTV_EMOTES.getValue()) {
                    new BTTVEmotes();
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load BetterTTV emotes: ", e);
            }

            if (Emotes.BTTV_CHANNEL_EMOTES.getValue())
            {
                try {
                    List<String> bttvChannels = Emotes.BTTV_EMOTE_CHANNELS.getValues();
                    for (String channel : bttvChannels) {
                        new BTTVChannelEmotes(channel);
                    }
                } catch (Exception e) {
                    LiteModChatTweaks.logger.error("Failed to load BetterTTV channel emotes: ", e);
                }
            }

            try {
                if (Emotes.FFZ_EMOTES.getValue()) {
                    new FFZEmotes();
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load FrankerFaceZ emotes: ", e);
            }

            if (Emotes.FFZ_CHANNEL_EMOTES.getValue())
            {
                try {
                    List<String> ffzChannels = Emotes.FFZ_EMOTE_CHANNELS.getValues();
                    for (String channel : ffzChannels) {
                        new FFZChannelEmotes(channel);
                    }
                } catch (Exception e) {
                    LiteModChatTweaks.logger.error("Failed to load FrankerFaceZ channel emotes: ", e);
                }
            }

            try {
                new LocalEmotes(new File(Minecraft.getMinecraft().gameDir, "chattweaks/emotes/"));
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load local emotes: ", e);
            }
            EmoteRegistry.isLoading = false;
        }).start();
    }
}
