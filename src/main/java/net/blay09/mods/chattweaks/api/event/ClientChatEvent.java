package net.blay09.mods.chattweaks.api.event;

public class ClientChatEvent extends Event
{
	private String message;

	public ClientChatEvent(String message)
	{
		super(true);
		this.message = message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
}
