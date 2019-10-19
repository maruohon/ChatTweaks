package net.blay09.mods.chattweaks.chat.emotes.bttv;

import java.net.URI;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.blay09.mods.chattweaks.ChatTweaksAPI;
import net.blay09.mods.chattweaks.chat.emotes.IEmote;
import net.blay09.mods.chattweaks.chat.emotes.IEmoteSource;
import net.blay09.mods.chattweaks.reference.Reference;

public enum BTTVChannelEmoteSource implements IEmoteSource<BTTVChannelEmoteData> {
    INSTANCE;

    private String urlTemplate;

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public void setUrlTemplate(String urlTemplate) {
        this.urlTemplate = urlTemplate;
    }

    @Override
    public String getCacheFileName(BTTVChannelEmoteData data) {
        return "bttv-" + data.getId();
    }

    @Override
    public String getTooltip(BTTVChannelEmoteData data) {
        return TextFormatting.GRAY + I18n.format(Reference.MOD_ID + ":gui.chat.tooltipEmoteChannel") + " " + data.getChannel();
    }

    @Override
    public void loadEmoteImage(IEmote<BTTVChannelEmoteData> emote) throws Exception {
        ChatTweaksAPI.loadEmoteImage(emote, new URI("https:" + urlTemplate.replace("{{id}}", emote.getCustomData().getId()).replace("{{image}}", "1x")));
    }


}
