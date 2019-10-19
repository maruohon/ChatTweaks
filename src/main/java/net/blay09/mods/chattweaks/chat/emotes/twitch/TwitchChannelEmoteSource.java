package net.blay09.mods.chattweaks.chat.emotes.twitch;

import java.net.URI;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.blay09.mods.chattweaks.ChatTweaksAPI;
import net.blay09.mods.chattweaks.chat.emotes.IEmote;
import net.blay09.mods.chattweaks.chat.emotes.IEmoteSource;
import net.blay09.mods.chattweaks.reference.Reference;

public enum TwitchChannelEmoteSource implements IEmoteSource<TwitchChannelEmoteData> {
    INSTANCE;

    private static final String URL_TEMPLATE = "https://static-cdn.jtvnw.net/emoticons/v1/{{id}}/1.0";

    @Override
    public String getCacheFileName(TwitchChannelEmoteData data) {
        return "twitch-" + data.getId();
    }

    @Override
    public String getTooltip(TwitchChannelEmoteData data) {
        return TextFormatting.GRAY + I18n.format(Reference.MOD_ID + ":gui.chat.tooltipEmoteChannel") + " " + data.getChannel();
    }

    @Override
    public void loadEmoteImage(IEmote<TwitchChannelEmoteData> emote) throws Exception {
        ChatTweaksAPI.loadEmoteImage(emote, new URI(URL_TEMPLATE.replace("{{id}}", String.valueOf(emote.getCustomData().getId()))));
    }


}
