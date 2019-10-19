package net.blay09.mods.chattweaks.chat.emotes;

import java.util.Collection;
import java.util.List;
import com.google.common.collect.Lists;

public class EmoteGroup implements IEmoteGroup {

    private final String name;
    private final List<IEmote<?>> emotes = Lists.newArrayList();

    public EmoteGroup(String name) {
        this.name = name;
    }

    @Override
    public void addEmote(IEmote<?> emote) {
        emotes.add(emote);
    }

    public Collection<IEmote<?>> getEmotes() {
        return emotes;
    }

    @Override
    public String getName() {
        return name;
    }

}
