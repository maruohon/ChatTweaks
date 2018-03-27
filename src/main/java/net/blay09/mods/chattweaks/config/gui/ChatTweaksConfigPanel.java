package net.blay09.mods.chattweaks.config.gui;

import java.util.ArrayList;
import java.util.List;
import com.mumfrey.liteloader.modconfig.AbstractConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.blay09.mods.chattweaks.config.gui.button.ButtonGeneric;
import net.blay09.mods.chattweaks.config.gui.button.ConfigOptionListeners.ButtonListenerPanelSelection;
import net.blay09.mods.chattweaks.reference.Reference;

public class ChatTweaksConfigPanel extends AbstractConfigPanel
{
    private final List<ConfigPanelSub> subPanels = new ArrayList<>();
    private ConfigPanelSub selectedSubPanel;
    private ConfigPanelHost host;

    private void createSubPanels()
    {
        this.subPanels.clear();
        this.addSubPanel(new ConfigPanelGeneric(this));
        this.addSubPanel(new ConfigPanelEmotes(this));
        this.addSubPanel(new ConfigPanelTheme(this));
    }

    @Override
    public String getPanelTitle()
    {
        if (this.selectedSubPanel != null)
        {
            return Reference.MOD_NAME + " options - " + this.selectedSubPanel.getPanelTitle();
        }

        return Reference.MOD_NAME + " options";
    }

    @Override
    public void onPanelHidden()
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.onPanelHidden();
        }
    }

    @Override
    protected void addOptions(ConfigPanelHost host)
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.addOptions(host);
            return;
        }

        this.host = host;

        this.clearOptions();
        this.createSubPanels();

        int buttonWidth = 300;
        int buttonHeight = 20;
        int x = host.getWidth() / 2 - buttonWidth / 2;
        int y = 10;

        for (int i = 0; i < this.subPanels.size(); i++)
        {
            ConfigPanelSub panel = this.subPanels.get(i);
            ButtonListenerPanelSelection<ButtonGeneric> listener = new ButtonListenerPanelSelection<>(this, panel);
            this.addControl(new ButtonGeneric(i, x, y, buttonWidth, buttonHeight, panel.getPanelTitle()), listener);
            y += 21;
        }
    }

    @Override
    public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks)
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.drawPanel(host, mouseX, mouseY, partialTicks);
        }
        else
        {
            super.drawPanel(host, mouseX, mouseY, partialTicks);
        }
    }

    @Override
    public int getContentHeight()
    {
        if (this.selectedSubPanel != null)
        {
            return this.selectedSubPanel.getContentHeight();
        }
        else
        {
            return super.getContentHeight();
        }
    }

    @Override
    public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode)
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.keyPressed(host, keyChar, keyCode);
        }
        else
        {
            super.keyPressed(host, keyChar, keyCode);
        }
    }

    @Override
    public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY)
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.mouseMoved(host, mouseX, mouseY);
        }
        else
        {
            super.mouseMoved(host, mouseX, mouseY);
        }
    }

    @Override
    public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.mousePressed(host, mouseX, mouseY, mouseButton);
        }
        else
        {
            super.mousePressed(host, mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.mouseReleased(host, mouseX, mouseY, mouseButton);
        }
        else
        {
            super.mouseReleased(host, mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void onPanelResize(ConfigPanelHost host)
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.onPanelResize(host);
        }
        else
        {
            super.onPanelResize(host);
        }
    }

    @Override
    protected void clearOptions()
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.clearOptions();
        }
        else
        {
            super.clearOptions();
        }
    }

    private void addSubPanel(ConfigPanelSub panel)
    {
        panel.addOptions(this.host);
        this.subPanels.add(panel);
    }

    public boolean isSubPanelActive()
    {
        return this.selectedSubPanel != null;
    }

    public void setSelectedSubPanel(int id)
    {
        if (id >= 0 && id < this.subPanels.size())
        {
            this.setSelectedSubPanel(this.subPanels.get(id));
        }
        else
        {
            this.setSelectedSubPanel(null);
        }
    }

    public void setSelectedSubPanel(ConfigPanelSub subPanel)
    {
        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.onPanelHidden();
        }

        this.selectedSubPanel = subPanel;

        if (this.selectedSubPanel != null)
        {
            this.selectedSubPanel.onPanelShown(this.host);
        }
    }
}
