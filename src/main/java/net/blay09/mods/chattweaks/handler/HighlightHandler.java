package net.blay09.mods.chattweaks.handler;

import net.blay09.mods.chattweaks.api.event.PrintChatMessageEvent;
import net.blay09.mods.chattweaks.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class HighlightHandler
{
	public void onPrintChatMessage(PrintChatMessageEvent event)
	{
		if (Configs.Generic.HILIGHT_NAME.getBooleanValue() == false &&
		    Configs.Generic.HILIGHT_STRINGS.getStringArray().length == 0)
		{
			return;
		}

		ITextComponent senderComponent = event.getChatMessage().getSender();
		String sender = senderComponent != null ? TextFormatting.getTextWithoutFormattingCodes(senderComponent.getUnformattedText()) : null;

		if (sender != null && sender.isEmpty() == false)
		{
			EntityPlayer player = Minecraft.getMinecraft().player;
			String playerName = player != null ? player.getName() : null; // FIXME was getDisplayNameString() with Forge

			if (sender.equals(playerName) == false)
			{
				ITextComponent messageComponent = event.getChatMessage().getMessage();

				if (messageComponent != null)
				{
					String message = messageComponent.getUnformattedText();

					if (Configs.Generic.HILIGHT_NAME.getBooleanValue() &&
					    message.matches(".*(?:[\\p{Punct} ]|^)" + playerName + "(?:[\\p{Punct} ]|$).*"))
					{
						event.getChatMessage().setBackgroundColor(Configs.Theme.BG_COLOR_HILIGHT.getIntegerValue());
					}
					else
					{
						for (String highlight : Configs.Generic.HILIGHT_STRINGS.getStringArray())
						{
							if (message.contains(highlight))
							{
								event.getChatMessage().setBackgroundColor(Configs.Theme.BG_COLOR_HILIGHT.getIntegerValue());
								break;
							}
						}
					}
				}
			}
		}
	}

}
