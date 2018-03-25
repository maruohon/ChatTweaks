package net.blay09.mods.chattweaks.config.gui;

import static com.mumfrey.liteloader.gl.GLClippingPlanes.glDisableClipping;
import static com.mumfrey.liteloader.gl.GLClippingPlanes.glEnableClipping;
import java.io.IOException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import com.mumfrey.liteloader.client.gui.GuiSimpleScrollBar;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class ChatTweaksConfigGui extends GuiScreen
{
    protected static final int WHITE                = 0xFFFFFFFF;
    protected static final int TOOLTIP_BACKGROUND   = 0xB0000000;
    protected static final int COLOR_HORIZONTAL_BAR = 0xFF999999;
    protected static final int LEFT_EDGE    = 80;
    protected static final int TOP          = 26;
    protected static final int BOTTOM       = 40;
    protected static final int MARGIN       = 12;
    private final ChatTweaksConfigPanel mainPanel;
    private final GuiSimpleScrollBar scrollBar = new GuiSimpleScrollBar();
    private final HostProxy host;
    /** Current inner pane width (width - margins) */
    protected int innerWidth = 0;
    /** Current inner pane visible height (height - chrome) */
    protected int innerHeight = 0;
    /** Panel Y position (for scroll) */
    protected int innerTop = TOP;
    /** Panel's internal height (for scrolling) */
    private int totalHeight = -1;

    public ChatTweaksConfigGui()
    {
        this.mainPanel = new ChatTweaksConfigPanel();
        this.host = new HostProxy(this);
    }

    @Override
    public void initGui()
    {
        this.setSize(this.width, this.height);
        this.onShown();
    }

    @Override
    public void onGuiClosed()
    {
        this.onHidden();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen()
    {
        this.onTick();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawPanel(mouseX, mouseY, partialTicks);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton control)
    {
        if (control.id == 0)
        {
            this.close();
        }
    }

    @Override
    public void handleMouseInput() throws IOException
    {
        int mouseWheelDelta = Mouse.getEventDWheel();

        if (mouseWheelDelta != 0)
        {
            this.mouseWheelScrolled(mouseWheelDelta);
        }

        super.handleMouseInput();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) throws IOException
    {
        this.mousePressed(mouseX - LEFT_EDGE, mouseY, button);

        super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button)
    {
        if (button == -1)
        {
            this.mainPanel.mouseMoved(this.host, mouseX - LEFT_EDGE - MARGIN, mouseY - this.innerTop);
        }
        else
        {
            if (button == 0)
            {
                this.scrollBar.setDragging(false);
            }

            this.mainPanel.mouseReleased(this.host, mouseX - LEFT_EDGE - MARGIN, mouseY - this.innerTop, button);
        }

        super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.mainPanel.keyPressed(this.host, typedChar, keyCode);
    }

    public int getInnerWidth()
    {
        return this.innerWidth;
    }

    public int getInnerHeight()
    {
        return this.innerHeight;
    }

    private boolean stealFocus()
    {
        return true;
    }

    private String getPanelTitle()
    {
        return this.mainPanel.getPanelTitle();
    }

    /**
     * Callback from parent screen when window is resized
     * 
     * @param width
     * @param height
     */
    private void setSize(int width, int height)
    {
        this.innerHeight = this.height - TOP - BOTTOM;
        this.innerWidth = this.width - (MARGIN * 2) - LEFT_EDGE;

        this.mainPanel.onPanelResize(this.host);
        this.addButton(new GuiButton(0, this.width - 99 - MARGIN, this.height - BOTTOM + 9, 100, 20, I18n.format("gui.saveandclose")));
    }

    private void onShown()
    {
        try
        {
            this.mainPanel.onPanelShown(this.host);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void onHidden()
    {
        this.mainPanel.onPanelHidden();
    }

    private void onTick()
    {
        this.mainPanel.onTick(this.host);
    }

    public void close()
    {
        this.mc.displayGuiScreen(null);
    }

    private void drawPanel(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.pushMatrix();

        // Draw the dark background
        drawRect(LEFT_EDGE, 0, this.width, this.height, TOOLTIP_BACKGROUND);
        // Draw the left edge
        drawRect(LEFT_EDGE, 0, LEFT_EDGE + 1, this.height, WHITE);

        GlStateManager.translate(LEFT_EDGE, 0, 0);
        this.draw(mouseX - LEFT_EDGE, mouseY, partialTicks);

        if (this.stealFocus() == false)
        {
            // Draw other controls inside the transform so that they slide properly
            //super.drawScreen(mouseX, mouseY, partialTicks);
        }

        GlStateManager.popMatrix();

        //this.drawTooltips(mouseX, mouseY, partialTicks, active, xOffset, mouseOverTab);
    }

    /**
     * Draw the panel and chrome
     * 
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    private void draw(int mouseX, int mouseY, float partialTicks)
    {
        // Scroll position
        this.innerTop = TOP - this.scrollBar.getValue();

        // Draw panel title
        this.mc.fontRenderer.drawString(this.getPanelTitle(), MARGIN, TOP - 14, 0xFFFFFFFF);

        // Draw top and bottom horizontal bars
        drawRect(MARGIN, TOP - 4, this.innerWidth + MARGIN, TOP - 3, COLOR_HORIZONTAL_BAR);
        drawRect(MARGIN, this.height - BOTTOM + 2, this.innerWidth + MARGIN, this.height - BOTTOM + 3, COLOR_HORIZONTAL_BAR);

        // Clip rect
        glEnableClipping(MARGIN, this.innerWidth, TOP, this.height - BOTTOM);

        // Offset by scroll
        GlStateManager.pushMatrix();
        GlStateManager.translate(MARGIN, this.innerTop, 0.0F);

        // Draw panel contents
        this.mainPanel.drawPanel(this.host, mouseX - MARGIN - (this.mouseOverPanel(mouseX, mouseY) ? 0 : 99999), mouseY - this.innerTop, partialTicks);
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);

        // Disable clip rect
        glDisableClipping();

        // Restore transform
        GlStateManager.popMatrix();

        // Get total scroll height from panel
        this.totalHeight = Math.max(-1, this.mainPanel.getContentHeight());

        // Update and draw scroll bar
        this.scrollBar.setMaxValue(this.totalHeight - this.innerHeight);
        this.scrollBar.drawScrollBar(mouseX, mouseY, partialTicks, this.width - MARGIN - 5, TOP, 5, this.innerHeight,
                Math.max(this.innerHeight, this.totalHeight));
    }

    private void mouseWheelScrolled(int mouseWheelDelta)
    {
        this.scrollBar.offsetValue(-mouseWheelDelta / 8);
    }

    private boolean mouseOverPanel(int mouseX, int mouseY)
    {
        return mouseX > MARGIN && mouseX <= this.width - MARGIN && mouseY > TOP && mouseY <= this.height - BOTTOM;
    }

    private void mousePressed(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (this.scrollBar.wasMouseOver())
            {
                this.scrollBar.setDragging(true);
            }
        }

        if (this.mouseOverPanel(mouseX, mouseY))
        {
            this.mainPanel.mousePressed(this.host, mouseX - MARGIN, mouseY - this.innerTop, mouseButton);
        }
    }
}
