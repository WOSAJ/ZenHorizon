package com.wosaj.zenhorizon.common.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.capability.Zen;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;

@javax.annotation.ParametersAreNonnullByDefault
public class ZenHorizonCommand {
    private static final SimpleCommandExceptionType UNKNOWN = new SimpleCommandExceptionType(new TranslatableComponent("command." + ZenHorizon.MODID + ".unknown"));
    private static final SimpleCommandExceptionType SET_TOO_MUCH = new SimpleCommandExceptionType(new TranslatableComponent("command." + ZenHorizon.MODID + ".set.fail.toomuch"));

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("zh").requires(s -> s.hasPermission(3)
        ).then(Commands.literal("zen")
                .then(Commands.literal("get")
                      .then(Commands.argument("player", EntityArgument.player())
                      .executes(ZenHorizonCommand::getZen))
                ).then(Commands.literal("set")
                        .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(ZenHorizonCommand::setZen)))
                ).then(Commands.literal("setmax")
                        .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                        .executes(ZenHorizonCommand::setMaxZen)))
                )
            )
        );
    }

    private static int getZen(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        Entity player = EntityArgument.getEntity(context, "player");
        LazyOptional<Zen> capability = Zen.get(player);
        if(capability.resolve().isPresent()) {
            Zen cap = capability.resolve().get();
            source.sendSuccess(new TranslatableComponent("command." + ZenHorizon.MODID + ".get.success",player.getDisplayName().getString(), cap.getZen(), cap.getMaxZen()), false);
            return 0;
        }
        throw UNKNOWN.create();
    }

    private static int setZen(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        if(source.getLevel().isClientSide) return 0;
        Entity player = EntityArgument.getEntity(context, "player");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        LazyOptional<Zen> capability = Zen.get(player);
        if(capability.resolve().isPresent()) {
            Zen cap = capability.resolve().get();
            if(amount > cap.getMaxZen()) throw SET_TOO_MUCH.create();
            cap.setZen(amount);
            cap.sync((ServerPlayer) player);
            source.sendSuccess(new TranslatableComponent("command." + ZenHorizon.MODID + ".set.success", player.getDisplayName(), amount), true);
            return 0;
        }
        throw UNKNOWN.create();
    }

    private static int setMaxZen(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        if(source.getLevel().isClientSide) return 0;
        Entity player = EntityArgument.getEntity(context, "player");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        LazyOptional<Zen> capability = Zen.get(player);
        if(capability.resolve().isPresent()) {
            Zen cap = capability.resolve().get();
            cap.setMaxZen(amount);
            cap.sync((ServerPlayer) player);
            source.sendSuccess(new TranslatableComponent("command." + ZenHorizon.MODID + ".setmax.success", player.getDisplayName(), amount), true);
            return 0;
        }
        throw UNKNOWN.create();
    }
}
