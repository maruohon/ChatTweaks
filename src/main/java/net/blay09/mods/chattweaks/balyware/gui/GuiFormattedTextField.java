package net.blay09.mods.chattweaks.balyware.gui;

import com.mumfrey.liteloader.client.overlays.IGuiTextField;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextFormatting;

public class GuiFormattedTextField extends GuiTextField {

    protected final FormattedFontRenderer fontRenderer;
    private String displayTextWhenEmpty = "";
    private String emptyText = "";

    public GuiFormattedTextField(int id, FormattedFontRenderer fontRenderer, int x, int y, int width, int height) {
        super(id, fontRenderer, x, y, width, height);
        this.fontRenderer = fontRenderer;
        setMaxStringLength(Integer.MAX_VALUE);
    }

    public void setDisplayTextWhenEmpty(String displayTextWhenEmpty) {
        this.displayTextWhenEmpty = displayTextWhenEmpty;
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
    }

    @Override
    public void drawTextBox() {
        if (!getVisible()) {
            return;
        }

        boolean isEmpty = getText().isEmpty() || getText().equals(emptyText);
        if(!isFocused() && isEmpty) {
            fontRenderer.setVisible(false);
        } else {
            fontRenderer.setVisible(true);
        }

        super.drawTextBox();

        if (!isFocused() && isEmpty) {
            fontRenderer.getBaseFontRenderer().drawStringWithShadow(TextFormatting.GRAY + displayTextWhenEmpty, x + 4, y + (this.getHeightWrapper() - 8) / 2, 0xE0E0E0);
        }
    }

    private int getHeightWrapper() {
        return ((IGuiTextField) (Object) this).getHeight();
    }
}