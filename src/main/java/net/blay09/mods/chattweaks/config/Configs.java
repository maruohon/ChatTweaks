package net.blay09.mods.chattweaks.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mumfrey.liteloader.core.LiteLoader;
import net.blay09.mods.chattweaks.LiteModChatTweaks;
import net.blay09.mods.chattweaks.chat.emotes.EmoteRegistry;
import net.blay09.mods.chattweaks.chat.emotes.LocalEmotes;
import net.blay09.mods.chattweaks.chat.emotes.twitch.BTTVChannelEmotes;
import net.blay09.mods.chattweaks.chat.emotes.twitch.BTTVEmotes;
import net.blay09.mods.chattweaks.chat.emotes.twitch.FFZChannelEmotes;
import net.blay09.mods.chattweaks.chat.emotes.twitch.FFZEmotes;
import net.blay09.mods.chattweaks.chat.emotes.twitch.TwitchEmotesAPI;
import net.blay09.mods.chattweaks.chat.emotes.twitch.TwitchGlobalEmotes;
import net.blay09.mods.chattweaks.chat.emotes.twitch.TwitchSubscriberEmotes;
import net.blay09.mods.chattweaks.reference.Reference;
import net.blay09.mods.chattweaks.util.JsonUtils;
import net.minecraft.client.Minecraft;

public class Configs
{
    private static final String CONFIG_FILE_NAME = Reference.MOD_ID + ".json";
    private static final SimpleDateFormat DEFAULT_TIMESTAMP_FORMAT = new SimpleDateFormat("[HH:mm]");
    public static SimpleDateFormat timestampFormat = DEFAULT_TIMESTAMP_FORMAT;

    public static class Generic
    {
        public static final ConfigOption ALTERNATE_BACKGROUND       = new ConfigOption("alternateBackgroundColor", true, "Should uneven lines alternate their background color for easier reading?");
        public static final ConfigOption CHAT_TEXT_OPACITY          = new ConfigOption("chatTextFullOpacity",  true, "Vanilla Minecraft makes the text in chat transparent too, when opacity is set. Set this to false to restore that behaviour.");
        public static final ConfigOption EMOTE_TAB_COMPLETION       = new ConfigOption("emoteTabCompletion", false, "Should emotes be considered in tab completion?");
        public static final ConfigOption HIDE_EMOTES_MENU           = new ConfigOption("hideEmotesMenu", false, "Set to true to hide the emote menu button in the chat.");
        public static final ConfigOption HILIGHT_NAME               = new ConfigOption("highlightName", false, "If set to true, mentions of your Minecraft IGN will be highlighted in chat.");
        public static final ConfigOption HILIGHT_STRINGS            = new ConfigOption("highlightedWords", new String[0], "List of words that are highlighted in chat.");
        public static final ConfigOption LINE_SPACING               = new ConfigOption("lineSpacing", 0, "Spacing between chat lines");
        public static final ConfigOption PREFER_NEW_MESSAGES        = new ConfigOption("smartViewNavigation", true, "When navigating between views, prefer views with new messages.");
        public static final ConfigOption SHOW_NEW_MESSAGE_OVERLAY   = new ConfigOption("showNewMessages",  true, "Highlights views with new messages red even when chat is closed.");
        public static final ConfigOption SMALLER_EMOTES             = new ConfigOption("smallerEmotes", false, "Should emotes be scaled down to perfectly fit into one line?");
        public static final ConfigOption TIMESTAMP_FORMAT_STRING    = new ConfigOption("timestampFormat", "[HH:mm]", false, "The format for the timestamp to be displayed in.");
    }

    public static class Theme
    {
        public static final ConfigOption BG_COLOR_1         = new ConfigOption("backgroundColor1", "#000000", true, "The background color to use for even line numbers in HEX.");
        public static final ConfigOption BG_COLOR_2         = new ConfigOption("backgroundColor2", "#111111", true, "The background color to use for uneven line numbers in HEX (if enabled).");
        public static final ConfigOption BG_COLOR_HILIGHT   = new ConfigOption("hilightColor", "#550000", true, "The background color to use for highlighted lines in HEX.");
    }

    public static class Emotes
    {
        public static final ConfigOption INCLUDE_TWITCH_PRIME_EMOTES    = new ConfigOption("includeTwitchPrimeEmotes", true, "Should Prime emotes (ex. KappaHD) be included with the Twitch Global Emotes?");
        public static final ConfigOption INCLUDE_TWITCH_SMILEYS         = new ConfigOption("includeTwitchSmileys", false, "Should smileys (ex. :-D) be included with the Twitch Global Emotes?");
        public static final ConfigOption TWITCH_GLOBAL_EMOTES           = new ConfigOption("twitchGlobalEmotes", true, "Should the Twitch Global emotes (ex. Kappa) be enabled?");
        public static final ConfigOption TWITCH_SUBSCRIBER_EMOTES       = new ConfigOption("twitchSubscriberEmotes", true, "Should the Twitch Subscriber emotes (ex. geekPraise) be enabled?");
        public static final ConfigOption TWITCH_SUBSCRIBER_EMOTE_REGEX  = new ConfigOption("twitchSubscriberEmoteRegex", "[a-z0-9][a-z0-9]+[A-Z0-9].*", false, "The regex pattern to match for Twitch Subscriber Emotes to be included. By default includes all that follow prefixCode convention.");
        public static final ConfigOption BTTV_EMOTES                    = new ConfigOption("BTTVEmotes", true, "Should the BTTV emotes (ex. AngelThump) be enabled?");
        public static final ConfigOption BTTV_EMOTE_CHANNELS            = new ConfigOption("BTTVEmoteChannels", new String[]{"ZeekDaGeek"}, "A list of channels to postInitLoad BTTV channel emotes from.");
        public static final ConfigOption FFZ_EMOTES                     = new ConfigOption("FFZEmotes", true, "Should the FrankerFaceZ emotes (ex. ZreknarF) be enabled?");
        public static final ConfigOption FFZ_EMOTE_CHANNELS             = new ConfigOption("FFZEmoteChannels", new String[]{"tehbasshunter"}, "A list of channels to load FrankerFaceZ channel emotes from.");
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
                JsonObject objTweakToggles      = JsonUtils.getNestedObject(root, "TweakToggles", false);
                JsonObject objTweakHotkeys      = JsonUtils.getNestedObject(root, "TweakHotkeys", false);
                JsonObject objGenericHotkeys    = JsonUtils.getNestedObject(root, "GenericHotkeys", false);
                JsonObject objGeneric           = JsonUtils.getNestedObject(root, "Generic", false);

                if (objGeneric != null)
                {
                    for (ConfigOption gen : ConfigOption.values())
                    {
                        if (objGeneric.has(gen.getName()))
                        {
                            gen.setValueFromJsonElement(objGeneric.get(gen.getName()));
                        }
                    }
                }
            }
        }

        try
        {
            timestampFormat = new SimpleDateFormat(Generic.TIMESTAMP_FORMAT_STRING.getStringValue());
        }
        catch (IllegalArgumentException e)
        {
            LiteModChatTweaks.logger.error("Invalid timestamp format - reverting to default");
            timestampFormat = DEFAULT_TIMESTAMP_FORMAT;
        }

        InputEventHandler.updateUsedKeys();
    }

    public static void save()
    {
        File dir = LiteLoader.getCommonConfigFolder();

        if (dir.exists() && dir.isDirectory())
        {
            File configFile = new File(dir, CONFIG_FILE_NAME);
            FileWriter writer = null;
            JsonObject root = new JsonObject();
            JsonObject objTweakToggles      = JsonUtils.getNestedObject(root, "TweakToggles", true);
            JsonObject objTweakHotkeys      = JsonUtils.getNestedObject(root, "TweakHotkeys", true);
            JsonObject objGenericHotkeys    = JsonUtils.getNestedObject(root, "GenericHotkeys", true);
            JsonObject objGeneric           = JsonUtils.getNestedObject(root, "Generic", true);

            for (ConfigOption gen : ConfigOption.values())
            {
                objGeneric.add(gen.getName(), gen.getAsJsonElement());
            }

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
                if (Emotes.TWITCH_GLOBAL_EMOTES.getBooleanValue()) {
                    new TwitchGlobalEmotes(Emotes.INCLUDE_TWITCH_PRIME_EMOTES.getBooleanValue(), Emotes.INCLUDE_TWITCH_SMILEYS.getBooleanValue());
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load Twitch global emotes: ", e);
            }

            try {
                if (Emotes.TWITCH_SUBSCRIBER_EMOTES.getBooleanValue()) {
                    new TwitchSubscriberEmotes(Emotes.TWITCH_SUBSCRIBER_EMOTE_REGEX.getStringValue());
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load Twitch subscriber emotes: ", e);
            }

            try {
                if (Emotes.BTTV_EMOTES.getBooleanValue()) {
                    new BTTVEmotes();
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load BetterTTV emotes: ", e);
            }

            try {
                String[] bttvChannels = Emotes.BTTV_EMOTE_CHANNELS.getStringArray();
                for (String channel : bttvChannels) {
                    new BTTVChannelEmotes(channel);
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load BetterTTV channel emotes: ", e);
            }

            try {
                if (Emotes.FFZ_EMOTES.getBooleanValue()) {
                    new FFZEmotes();
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load FrankerFaceZ emotes: ", e);
            }

            try {
                String[] ffzChannels = Emotes.FFZ_EMOTE_CHANNELS.getStringArray();
                for (String channel : ffzChannels) {
                    new FFZChannelEmotes(channel);
                }
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load FrankerFaceZ channel emotes: ", e);
            }

            try {
                new LocalEmotes(new File(Minecraft.getMinecraft().mcDataDir, "chattweaks/emotes/"));
            } catch (Exception e) {
                LiteModChatTweaks.logger.error("Failed to load local emotes: ", e);
            }
            EmoteRegistry.isLoading = false;
        }).start();
    }
}
