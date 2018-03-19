package net.blay09.mods.chattweaks.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.blay09.mods.chattweaks.LiteModChatTweaks;
import net.blay09.mods.chattweaks.api.event.Event;
import net.blay09.mods.chattweaks.api.event.IEventHandler;

public class EventBus
{
    private static final EventBus INSTANCE = new EventBus();
    private final Map<Class<? extends Event>, List<IEventHandler>> handlerMap = new HashMap<>();

    public static EventBus instance()
    {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public void register(IEventHandler handler)
    {
        Class<? extends Event> clazz = null;

        for (Method method : handler.getClass().getDeclaredMethods())
        {
            if (method.getName().equals("onEvent") &&
                method.getParameterCount() == 1 &&
                method.getParameters()[0].getType().isAssignableFrom(Event.class))
            {
                clazz = (Class<? extends Event>) method.getParameters()[0].getType();
            }
        }

        if (clazz != null)
        {
            List<IEventHandler> handlers = this.handlerMap.get(clazz);

            if (handlers == null)
            {
                handlers = new ArrayList<>();
                this.handlerMap.put(clazz, handlers);
            }

            if (handlers.contains(handler) == false)
            {
                handlers.add(handler);
            }
        }
        else
        {
            LiteModChatTweaks.logger.error("Failed to register an event handler {}", handler.getClass().getName());
        }
    }

    /**
     * Post an event to the bus.
     * @param event
     * @return true if the event was canceled, false otherwise
     */
    public boolean post(Event event)
    {
        List<IEventHandler> handlers = this.handlerMap.get(event.getClass());

        if (handlers != null && handlers.size() > 0)
        {
            for (IEventHandler handler : handlers)
            {
                handler.onEvent(event);

                if (event.isCanceled())
                {
                    return true;
                }
            }
        }

        return event.isCanceled();
    }
}
