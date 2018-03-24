package net.blay09.mods.chattweaks.api.event;

public interface IEventHandler<T extends Event>
{
    void onEvent(T event);
}
