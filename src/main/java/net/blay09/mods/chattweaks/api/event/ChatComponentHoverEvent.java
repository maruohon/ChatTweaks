package net.blay09.mods.chattweaks.api.event;

import net.minecraft.util.text.ITextComponent;

public class ChatComponentHoverEvent extends Event
{
	private final ITextComponent component;
	private final int x;
	private final int y;

	public ChatComponentHoverEvent(ITextComponent component, int x, int y)
	{
		super(true);
		this.component = component;
		this.x = x;
		this.y = y;
	}

	public ITextComponent getComponent()
	{
		return component;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}
