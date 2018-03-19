package net.blay09.mods.chattweaks.api.event;

/**
 * This event is published on the ChatTweaks event bus whenever ChatTweaks reloads it's emoticons.
 * It is also published once during startup.
 * Other mods can listen on this event to register their own emoticons.
 */
public class ReloadEmotes extends Event
{
	public ReloadEmotes()
	{
		super(false);
	}
}
