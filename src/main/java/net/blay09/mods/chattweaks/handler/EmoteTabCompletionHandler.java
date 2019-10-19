package net.blay09.mods.chattweaks.handler;

import java.util.List;
import net.blay09.mods.chattweaks.api.event.IEventHandler;
import net.blay09.mods.chattweaks.api.event.TabCompletionEvent;
import net.blay09.mods.chattweaks.chat.emotes.EmoteRegistry;
import net.blay09.mods.chattweaks.config.Configs;
import net.minecraft.command.CommandBase;

public class EmoteTabCompletionHandler implements IEventHandler<TabCompletionEvent>
{
    @Override
    public void onEvent(TabCompletionEvent event)
    {
        if (Configs.Generic.EMOTE_TAB_COMPLETION.getValue())
        {
            List<String> strs = CommandBase.getListOfStringsMatchingLastWord(new String[]{event.getInput()}, EmoteRegistry.getCommonEmoteCodes());
            event.getCompletions().addAll(strs);
        }
    }
}
