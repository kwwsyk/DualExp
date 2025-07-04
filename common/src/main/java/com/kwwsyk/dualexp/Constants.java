package com.kwwsyk.dualexp;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public class Constants {

	public static final String MOD_ID = "dual_exp";
	public static final String MOD_NAME = "DualExp";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final String DEFAULT_CURRENCY_NAME = "rune";

	public static final String NEW_XP_COMMAND = "levelexp";

	public static final Supplier<MutableComponent> DEPRECATION_TIP = ()-> Component.translatable(
			"dualexp.deprecated.xp",
			Component.literal("["+MOD_NAME+"]").withStyle(ChatFormatting.GREEN) ,
			Component.literal("/"+NEW_XP_COMMAND).withStyle(ChatFormatting.AQUA),
			Component.literal("/"+Runtime.STARTUP_CONFIG.getCurrencyCmdName()).withStyle(ChatFormatting.AQUA)
	).withStyle(ChatFormatting.YELLOW);

	public static final HoverEvent DEPRECATION_HOVERING_TIP = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
			Component.translatable("dualexp.hoveringtxt.disable_mention")
			);
}