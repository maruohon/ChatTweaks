package net.blay09.mods.chattweaks.chat.emotes.ffz;

import java.net.URI;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.blay09.mods.chattweaks.ChatTweaksAPI;
import net.blay09.mods.chattweaks.chat.emotes.IEmote;
import net.blay09.mods.chattweaks.chat.emotes.IEmoteSource;
import net.blay09.mods.chattweaks.reference.Reference;

public enum FFZChannelEmoteSource implements IEmoteSource<FFZChannelEmoteData> {
    INSTANCE;

    @Override
    public String getCacheFileName(FFZChannelEmoteData data) {
        return "ffz-" + data.getId();
    }

    @Override
    public String getTooltip(FFZChannelEmoteData data) {
        return TextFormatting.GRAY + I18n.format(Reference.MOD_ID + ":gui.chat.tooltipEmoteChannel") + " " + data.getChannel();
    }

    @Override
    public void loadEmoteImage(IEmote<FFZChannelEmoteData> emote) throws Exception {
        ChatTweaksAPI.loadEmoteImage(emote, new URI("https:" + emote.getCustomData().getUrl()));
    }


}
