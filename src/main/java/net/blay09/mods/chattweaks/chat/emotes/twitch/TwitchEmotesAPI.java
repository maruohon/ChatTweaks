package net.blay09.mods.chattweaks.chat.emotes.twitch;

import java.util.Map;
import javax.annotation.Nullable;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.IntHashMap;
import net.blay09.mods.chattweaks.balyware.CachedAPI;
import net.blay09.mods.chattweaks.chat.emotes.IEmote;

public class TwitchEmotesAPI {

    public static final String CLIENT_ID = "gdhi94otnk7c7746syjv7gkr6bizq4w";

    public static final int EMOTESET_GLOBAL = 0;
    public static final int EMOTESET_TURBO = 19194;

    private static final IntHashMap<String> emoteSets = new IntHashMap<>();
    private static final IntHashMap<IEmote<?>> twitchEmotes = new IntHashMap<>();

    public static void loadEmoteSets() throws Exception {
        JsonObject sets = CachedAPI.loadCachedAPI("https://twitchemotes.com/api_cache/v3/sets.json", "twitch_emotesets_v3.json", null);
        if(sets != null) {
            for(Map.Entry<String, JsonElement> entry : sets.entrySet()) {
                emoteSets.addKey(Integer.parseInt(entry.getKey()), entry.getValue().getAsJsonObject().get("channel_name").getAsString());
            }
        }
    }

    @Nullable
    public static String getChannelForEmoteSet(int emoteSet) {
        return emoteSets.lookup(emoteSet);
    }

    @Nullable
    public static JsonObject loadEmotes(int... emotesets) {
        StringBuilder sb = new StringBuilder();
        for (int emoteset : emotesets) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(emoteset);
        }
        String url = "https://api.twitch.tv/kraken/chat/emoticon_images?client_id=" + CLIENT_ID;
        if(emotesets.length > 0) {
            url += "&emotesets=" + sb.toString();
        }
        return CachedAPI.loadCachedAPI(url, "twitch_emotes" + (sb.length() > 0 ? "-" + sb.toString() : "") + ".json", "application/vnd.twitchtv.v5+json");
    }

    public static void registerTwitchEmote(int id, IEmote<?> emote) {
        twitchEmotes.addKey(id, emote);
    }

    @Nullable
    public static IEmote<?> getEmoteById(int id) {
        return twitchEmotes.lookup(id);
    }

}
