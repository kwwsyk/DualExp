package com.kwwsyk.dualexp;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

	public static final String MOD_ID = "dual_exp";
	public static final String MOD_NAME = "DualExp";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

	public static final String CURRENCY = "rune";

	public static final String NEW_XP_COMMAND = "levelexp";
	public static final String RUNE_COMMAND = "rune";

	public static final Component DEPRECATION_TIP = Component.translatable(
			"dualexp.command.deprecated.xp",
			Component.literal("["+MOD_NAME+"]").withStyle(ChatFormatting.GREEN) ,
			Component.literal("/"+NEW_XP_COMMAND).withStyle(ChatFormatting.AQUA),
			Component.literal("/"+RUNE_COMMAND).withStyle(ChatFormatting.AQUA)
	).withStyle(ChatFormatting.YELLOW);

	static {

	}
}