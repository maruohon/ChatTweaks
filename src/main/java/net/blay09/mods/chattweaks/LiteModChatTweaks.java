package net.blay09.mods.chattweaks;

import java.io.File;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import com.google.common.collect.Lists;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.JoinGameListener;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import net.blay09.mods.chattweaks.api.ChatTweaksAPI;
import net.blay09.mods.chattweaks.auth.AuthManager;
import net.blay09.mods.chattweaks.chat.ChatChannel;
import net.blay09.mods.chattweaks.chat.ChatMessage;
import net.blay09.mods.chattweaks.chat.ChatView;
import net.blay09.mods.chattweaks.config.Configs;
import net.blay09.mods.chattweaks.config.gui.ChatTweaksConfigPanel;
import net.blay09.mods.chattweaks.event.EventBus;
import net.blay09.mods.chattweaks.gui.BottomChatRenderer;
import net.blay09.mods.chattweaks.gui.SideChatRenderer;
import net.blay09.mods.chattweaks.gui.chat.GuiChatExt;
import net.blay09.mods.chattweaks.gui.chat.GuiNewChatExt;
import net.blay09.mods.chattweaks.gui.chat.GuiSleepMPExt;
import net.blay09.mods.chattweaks.handler.EmoteTabCompletionHandler;
import net.blay09.mods.chattweaks.handler.HighlightHandler;
import net.blay09.mods.chattweaks.mixin.IMixinGuiChat;
import net.blay09.mods.chattweaks.mixininterfaces.IMixinGuiIngame;
import net.blay09.mods.chattweaks.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.INetHandler;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.util.text.ITextComponent;

public class LiteModChatTweaks implements LiteMod, Configurable, InitCompleteListener, JoinGameListener
{
    public static final String TEXT_FORMATTING_RGB = "\u00a7#";
    public static final String TEXT_FORMATTING_EMOTE = "\u00a7*";
    public static final Logger logger = LogManager.getLogger(Reference.MOD_ID);

    // FIXME LiteLoader port
    public static final KeyBinding KEY_SWITCH_CHAT_VIEW = new KeyBinding("key.chattweaks.switch_chat_view", Keyboard.KEY_C, "key.categories.chattweaks");

    public static String configDirPath;
    private static LiteModChatTweaks instance;

    private GuiNewChatExt persistentChatGUI;
    private SideChatRenderer sideChatRenderer;
    private BottomChatRenderer bottomChatRenderer;
    private AuthManager authManager;
    private List<Function<String, String>> imageURLTransformers = Lists.newArrayList();

    public LiteModChatTweaks()
    {
        instance = this;
    }

    public static LiteModChatTweaks getInstance()
    {
        return instance;
    }

    @Override
    public String getName()
    {
        return Reference.MOD_NAME;
    }

    @Override
    public String getVersion()
    {
        return Reference.MOD_VERSION;
    }

    @Override
    public Class<? extends ConfigPanel> getConfigPanelClass()
    {
        return ChatTweaksConfigPanel.class;
    }

    @Override
    public void init(File configPath)
    {
        configDirPath = new File(LiteLoader.getCommonConfigFolder(), Reference.MOD_ID).getAbsolutePath();

        this.sideChatRenderer = new SideChatRenderer();
        this.bottomChatRenderer = new BottomChatRenderer();
        EventBus.instance().register(new EmoteTabCompletionHandler());
        EventBus.instance().register(new HighlightHandler());

        File configDir = new File(LiteLoader.getCommonConfigFolder(), Reference.MOD_ID);
        File cacheDir = new File(configDir, "cache");

        if (configDir.exists() == false && configDir.mkdirs() == false)
        {
            logger.error("Failed to create ChatTweaks config directory.");
        }

        if (cacheDir.exists() == false && cacheDir.mkdirs() == false)
        {
            logger.error("Failed to create ChatTweaks cache directory.");
        }

        Configs.load();
        ChatManager.init();

        this.authManager = new AuthManager();
        this.authManager.load();

        ChatTweaksAPI.registerImageURLTransformer(new PatternImageURLTransformer(".+\\.(?:png|jpg)", "%s"));
        ChatTweaksAPI.registerImageURLTransformer(new PatternImageURLTransformer(".*imgur\\.com/[A-Za-z]+", "%s.png"));
        ChatTweaksAPI.registerImageURLTransformer(new PatternImageURLTransformer(".*gyazo\\.com/[a-z0-9]+", "%s.png"));
        ChatTweaksAPI.registerImageURLTransformer(new PatternImageURLTransformer("(.*twimg\\.com/[^:]+):large", "%s"));
    }

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {
    }

    @Override
    public void onInitCompleted(Minecraft minecraft, LiteLoader loader)
    {
        LiteLoader.getInput().registerKeyBinding(KEY_SWITCH_CHAT_VIEW);
        this.persistentChatGUI = new GuiNewChatExt(minecraft);

        Configs.postInitLoad();
        ChatViewManager.load();
    }

    @Override
    public void onJoinGame(INetHandler netHandler, SPacketJoinGame joinGamePacket, ServerData serverData, RealmsServer realmsServer)
    {
        ((IMixinGuiIngame) Minecraft.getMinecraft().ingameGUI).setPersistentChatGUI(this.persistentChatGUI);
    }

    public static GuiScreen onOpenGui(GuiScreen gui)
    {
        if (gui != null)
        {
            if (gui.getClass() == GuiChat.class)
            {
                return new GuiChatExt(((IMixinGuiChat) gui).getDefaultInputFieldText());
            }
            else if (gui.getClass() == GuiSleepMP.class)
            {
                return new GuiSleepMPExt();
            }
        }

        return gui;
    }

    public static void onRenderGameOverlayPost(float partialTicks)
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        instance.sideChatRenderer.onRenderGameOverlayPost(mc, scaledResolution, partialTicks);
        instance.bottomChatRenderer.onRenderGameOverlayPost(mc, scaledResolution, partialTicks);
    }

    public static GuiNewChatExt getChatDisplay()
    {
        return instance.persistentChatGUI;
    }

    public static SideChatRenderer getSideChatHandler()
    {
        return instance.sideChatRenderer;
    }

    public static BottomChatRenderer getBottomChatHandler()
    {
        return instance.bottomChatRenderer;
    }

    public static AuthManager getAuthManager()
    {
        return instance.authManager;
    }

    public static List<Function<String, String>> getImageURLTransformers()
    {
        return instance.imageURLTransformers;
    }

    public static void registerImageURLTransformer(Function<String, String> function)
    {
        instance.imageURLTransformers.add(function);
    }

    public static int colorFromHex(String hex)
    {
        return Integer.parseInt(hex.startsWith("#") ? hex.substring(1) : hex, 16);
    }

    public static ChatMessage createChatMessage(ITextComponent component)
    {
        return new ChatMessage(ChatManager.getNextMessageId(), component);
    }

    public static void addChatMessage(ChatMessage chatMessage, @Nullable ChatChannel chatChannel)
    {
        if (chatChannel == null)
        {
            chatChannel = ChatManager.findChatChannel(chatMessage);
        }

        instance.persistentChatGUI.addChatMessage(chatMessage, chatChannel);
    }

    public static void addChatMessage(ITextComponent component, @Nullable ChatChannel chatChannel)
    {
        addChatMessage(createChatMessage(component), chatChannel);
    }

    public static void refreshChat()
    {
        for(ChatView chatView : ChatViewManager.getViews())
        {
            chatView.refresh();
        }

        instance.persistentChatGUI.refreshChat();
    }
}
