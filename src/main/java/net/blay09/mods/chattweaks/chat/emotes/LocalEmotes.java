package net.blay09.mods.chattweaks.chat.emotes;

import java.io.File;
import com.google.common.io.Files;
import net.blay09.mods.chattweaks.api.ChatTweaksAPI;
import net.blay09.mods.chattweaks.reference.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public class LocalEmotes implements IEmoteLoader {

    public LocalEmotes(File directory) throws Exception {
        if(!directory.exists() && !directory.mkdirs()) {
            throw new Exception("Could not create local emotes directory.");
        }
        IEmoteGroup group = ChatTweaksAPI.registerEmoteGroup("Local");
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".gif"));
        if(files != null) {
            for(File file : files) {
                IEmote emote = ChatTweaksAPI.registerEmote(Files.getNameWithoutExtension(file.getName()), this);
                emote.addTooltip(TextFormatting.GRAY + I18n.format(Reference.MOD_ID + ":gui.chat.tooltipLocalEmotes"));
                emote.setCustomData(file);
                group.addEmote(emote);
            }
        }
    }

    @Override
    public void loadEmoteImage(IEmote emote) throws Exception {
        ChatTweaksAPI.loadEmoteImage(emote, ((File) emote.getCustomData()).toURI());
    }

}
