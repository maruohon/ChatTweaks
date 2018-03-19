package net.blay09.mods.chattweaks.api.event;

import java.util.List;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;

public class TabCompletionEvent extends Event
{
	private final ICommandSender sender;
	private final String input;
	private final BlockPos pos;
	private final boolean hasTargetBlock;
	private final List<String> completions;

	public TabCompletionEvent(ICommandSender sender, String input, BlockPos pos, boolean hasTargetBlock, List<String> completions)
	{
		super(false);
		this.sender = sender;
		this.input = input;
		this.pos = pos;
		this.hasTargetBlock = hasTargetBlock;
		this.completions = completions;
	}

	public ICommandSender getSender()
	{
		return sender;
	}

	public String getInput()
	{
		return input;
	}

	public BlockPos getPos()
	{
		return pos;
	}

	public boolean isHasTargetBlock()
	{
		return hasTargetBlock;
	}

	public List<String> getCompletions()
	{
		return completions;
	}

	public void addCompletion(String completion)
	{
		completions.add(completion);
	}
}
