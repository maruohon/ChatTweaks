package net.blay09.mods.chattweaks.handler;

import java.awt.Desktop;
import java.io.File;
import org.apache.commons.lang3.SystemUtils;
import org.lwjgl.input.Keyboard;
import net.minecraft.util.text.event.ClickEvent;
import net.blay09.mods.chattweaks.api.event.ChatComponentClickEvent;
import net.blay09.mods.chattweaks.api.event.IEventHandler;

public class OpenScreenshotFolderHandler implements IEventHandler<ChatComponentClickEvent> {

    public void onEvent(ChatComponentClickEvent event) {
        ClickEvent clickEvent = event.getComponent().getStyle().getClickEvent();
        if (clickEvent == null || clickEvent.getAction() != ClickEvent.Action.OPEN_FILE || !clickEvent.getValue().endsWith(".png")) {
            return;
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            if (openContainingFolder(new File(clickEvent.getValue()))) {
                event.setCanceled(true);
            }
        }
    }

    private boolean openContainingFolder(File file) {
        try {
            if (SystemUtils.IS_OS_WINDOWS) {
                Runtime.getRuntime().exec("explorer.exe /select," + file.getAbsolutePath());
                return true;
            } else if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file.getParentFile());
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
