package com.kwwsyk.dualexp.command;

import com.kwwsyk.dualexp.Runtime;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

import static com.kwwsyk.dualexp.Runtime.STARTUP_CONFIG;

public class RuneCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal(STARTUP_CONFIG.getCurrencyCmdName())
                        .requires(source->source.hasPermission(2))
                        .then(
                                Commands.literal("add")
                                        .then(
                                                Commands.argument("targets", EntityArgument.players())
                                                        .then(
                                                                Commands.argument("amount", IntegerArgumentType.integer())
                                                                        .executes(
                                                                                cxt -> addRune(
                                                                                        cxt.getSource(),
                                                                                        EntityArgument.getPlayers(cxt, "targets"),
                                                                                        IntegerArgumentType.getInteger(cxt, "amount")
                                                                                )
                                                                        )
                                                        )
                                        )
                        )
                        .then(
                                Commands.literal("set")
                                        .then(
                                                Commands.argument("targets", EntityArgument.players())
                                                        .then(
                                                                Commands.argument("amount", IntegerArgumentType.integer(0))
                                                                        .executes(
                                                                                p_137335_ -> setRune(
                                                                                        p_137335_.getSource(),
                                                                                        EntityArgument.getPlayers(p_137335_, "targets"),
                                                                                        IntegerArgumentType.getInteger(p_137335_, "amount")
                                                                                )
                                                                        )
                                                        )
                                        )
                        )
                        .then(
                                Commands.literal("get")
                                        .then(
                                                Commands.argument("targets", EntityArgument.player())
                                                        .executes(
                                                                cxt->getRune(
                                                                        cxt.getSource(),
                                                                        EntityArgument.getPlayer(cxt, "targets")
                                                                )
                                                        )
                                        )
                        )

        );
    }

    private static int getRune(CommandSourceStack source, ServerPlayer player) {
        int i = Runtime.runeAttachment.getRune(player);
        source.sendSuccess(() -> Component.translatable("dualexp.command.rune.get", player.getDisplayName(),  STARTUP_CONFIG.getCurrencyName(), i), false);
        return i;
    }

    private static int setRune(CommandSourceStack source, Collection<ServerPlayer> targets, int amount) {
        for(ServerPlayer serverPlayer : targets){
            Runtime.runeAttachment.setRune(serverPlayer,amount);
        }

        if (targets.size() == 1) {
            source.sendSuccess(
                    () -> Component.translatable(
                            "dualexp.command.rune.set.success.single", targets.iterator().next().getDisplayName(), STARTUP_CONFIG.getCurrencyName(), amount
                    ),
                    true
            );
        } else {
            source.sendSuccess(
                    () -> Component.translatable("dualexp.command.rune.set.success.multiple", targets.size(), STARTUP_CONFIG.getCurrencyName(), amount), true
            );
        }

        return targets.size();
    }

    private static int addRune(CommandSourceStack source, Collection<ServerPlayer> targets, int amount) {
        for(ServerPlayer serverPlayer : targets){
            Runtime.runeAttachment.giveRune(serverPlayer,amount);
        }

        if (targets.size() == 1) {
            source.sendSuccess(
                    () -> Component.translatable(
                            "dualexp.command.rune.add.success.single", amount, STARTUP_CONFIG.getCurrencyName(), targets.iterator().next().getDisplayName()
                    ),
                    true
            );
        } else {
            source.sendSuccess(
                    () -> Component.translatable("dualexp.command.rune.add.success.multiple", amount, STARTUP_CONFIG.getCurrencyName(), targets.size()), true
            );
        }

        return targets.size();
    }
}
