package net.blay09.mods.chattweaks.api.event;

public class Event
{
	private final boolean isCancelable;
	private boolean isCanceled = false;

	public Event(boolean isCancelable)
	{
		this.isCancelable = isCancelable;
	}

	/**
	 * Determine if this function is cancelable at all.
	 * @return If access to setCanceled should be allowed
	 */
	public boolean isCancelable()
	{
		return this.isCancelable;
	}

	/**
	 * Determine if this event is canceled and should stop executing.
	 * @return The current canceled state
	 */
	public boolean isCanceled()
	{
		return isCanceled;
	}

	/**
	 * Sets the cancel state of this event. Note, not all events are cancelable, and any attempt to
	 * invoke this method on an event that is not cancelable (as determined by {@link #isCancelable}
	 * will result in an {@link UnsupportedOperationException}.
	 *
	 * The functionality of setting the canceled state is defined on a per-event bases.
	 *
	 * @param cancel The new canceled value
	 */
	public void setCanceled(boolean cancel)
	{
		if (this.isCancelable() == false)
		{
			throw new UnsupportedOperationException(
				"Attempted to call Event#setCanceled() on a non-cancelable event of type: "
				+ this.getClass().getCanonicalName()
			);
		}

		this.isCanceled = cancel;
	}
}
