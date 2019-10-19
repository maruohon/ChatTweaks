package net.blay09.mods.chattweaks.balyware.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class GuiUtils extends GuiScreen {

    private static GuiUtils instance;
    public static GuiUtils getInstance() {
        if(instance == null) {
            instance = new GuiUtils();
            instance.setWorldAndResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        }
        return instance;
    }

    public static void drawTooltip(List<String> list, int x, int y) {
        getInstance().drawHoveringText(list, x, y);
    }

}
