package net.blay09.mods.chattweaks.chat.emotes.ffz;

import java.net.URI;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.blay09.mods.chattweaks.ChatTweaksAPI;
import net.blay09.mods.chattweaks.chat.emotes.IEmote;
import net.blay09.mods.chattweaks.chat.emotes.IEmoteSource;
import net.blay09.mods.chattweaks.reference.Reference;

public enum FFZEmoteSource implements IEmoteSource<FFZEmoteData> {
    INSTANCE;

    @Override
    public String getCacheFileName(FFZEmoteData data) {
        return "ffz-" + data.getId();
    }

    @Override
    public String getTooltip(FFZEmoteData data) {
        return TextFormatting.GRAY + I18n.format(Reference.MOD_ID + ":gui.chat.tooltipFFZEmotes");
    }

    @Override
    public void loadEmoteImage(IEmote<FFZEmoteData> emote) throws Exception {
        ChatTweaksAPI.loadEmoteImage(emote, new URI("https:" + emote.getCustomData().getUrl()));
    }


}
