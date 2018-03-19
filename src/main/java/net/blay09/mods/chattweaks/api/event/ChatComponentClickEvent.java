package net.blay09.mods.chattweaks.api.event;

import net.minecraft.util.text.ITextComponent;

public class ChatComponentClickEvent extends Event
{
	private final ITextComponent component;

	public ChatComponentClickEvent(ITextComponent component)
	{
		super(true);
		this.component = component;
	}

	public ITextComponent getComponent()
	{
		return component;
	}
}
