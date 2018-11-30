package net.blay09.mods.chattweaks.chat.emotes;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import com.google.common.collect.Lists;
import net.blay09.mods.chattweaks.image.renderable.IChatRenderable;
import net.blay09.mods.chattweaks.image.renderable.NullRenderable;
import net.blay09.mods.chattweaks.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

public class Emote implements IEmote {

	private final List<String> tooltip = Lists.newArrayList();
	private final IEmoteLoader loader;
	private final String code;
	private final Pattern pattern;

	private File imageCacheFile;
	private Object customData;
	private IChatRenderable image = NullRenderable.INSTANCE;
	private boolean loadRequested;

	public Emote(String code, IEmoteLoader loader, boolean isRegex) {
		this.code = code;
		this.pattern = isRegex ? Pattern.compile("(?: |^)" + code + "(?: |$)") : null;
		this.loader = loader;

		if(!isRegex) {
			tooltip.add(TextFormatting.YELLOW + I18n.format(Reference.MOD_ID + ":gui.chat.tooltipEmote") + " " + TextFormatting.WHITE + code);
		}
	}

	@Override
	public boolean isRegex() {
		return pattern != null;
	}

	@Override
	public Object getCustomData() {
		return customData;
	}

	@Override
	public void setCustomData(Object customData) {
		this.customData = customData;
	}

	@Override
	public List<String> getTooltip() {
		return tooltip;
	}

	@Override
	public void addTooltip(String... tooltip) {
		Collections.addAll(this.tooltip, tooltip);
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public IEmoteLoader getLoader() {
		return loader;
	}

	@Override
	public IChatRenderable getImage() {
		return image;
	}

	@Override
	public void setImage(@Nullable IChatRenderable image) {
		if(image == null) {
			image = NullRenderable.INSTANCE;
		}
		this.image = image;
		loadRequested = false;
	}

	@Override
	public int getWidthInSpaces() {
		return image != null ? image.getWidthInSpaces() : 4;
	}

	@Override
	public void requestLoad() {
		if(!loadRequested) {
			loadRequested = true;
			AsyncEmoteLoader.getInstance().loadAsync(this);
		}
	}

	@Override
	public void setImageCacheFile(@Nullable String fileName) {
		if(fileName == null) {
			imageCacheFile = null;
		} else {
			imageCacheFile = new File(Minecraft.getMinecraft().gameDir, "chattweaks/cache/" + fileName);
		}
	}

	@Override
	public File getImageCacheFile() {
		return imageCacheFile;
	}
}
