package net.blay09.mods.chattweaks.api.event;

import net.blay09.mods.chattweaks.chat.ChatMessage;
import net.blay09.mods.chattweaks.chat.ChatView;

public class PrintChatMessageEvent extends Event
{
	private final ChatMessage chatMessage;
	private final ChatView view;

	public PrintChatMessageEvent(ChatMessage chatMessage, ChatView view)
	{
		super(false);
		this.chatMessage = chatMessage;
		this.view = view;
	}

	public ChatMessage getChatMessage()
	{
		return chatMessage;
	}

	public ChatView getView()
	{
		return view;
	}
}
