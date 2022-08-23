package com.wosaj.zenhorizon.common.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.capability.Zen;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.LazyOptional;

@javax.annotation.ParametersAreNonnullByDefault
public class ZenHorizonCommand {
    private static final SimpleCommandExceptionType UNKNOWN = new SimpleCommandExceptionType(new TranslationTextComponent("command." + ZenHorizon.MODID + ".unknown"));
    private static final SimpleCommandExceptionType SET_TOO_MUCH = new SimpleCommandExceptionType(new TranslationTextComponent("command." + ZenHorizon.MODID + ".set.fail.toomuch"));

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        dispatcher.register(Commands.literal("zh").requires(s -> s.hasPermissionLevel(3)
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

    private static int getZen(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        Entity player = EntityArgument.getEntity(context, "player");
        LazyOptional<Zen> capability = Zen.get(player);
        if(capability.resolve().isPresent()) {
            Zen cap = capability.resolve().get();
            source.sendFeedback(new TranslationTextComponent("command." + ZenHorizon.MODID + ".get.success",player.getDisplayName().getString(), cap.getZen(), cap.getMaxZen()), false);
            return 0;
        }
        throw UNKNOWN.create();
    }

    private static int setZen(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        if(source.getWorld().isRemote) return 0;
        Entity player = EntityArgument.getEntity(context, "player");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        LazyOptional<Zen> capability = Zen.get(player);
        if(capability.resolve().isPresent()) {
            Zen cap = capability.resolve().get();
            if(amount > cap.getMaxZen()) throw SET_TOO_MUCH.create();
            cap.setZen(amount);
            cap.sync((ServerPlayerEntity) player);
            source.sendFeedback(new TranslationTextComponent("command." + ZenHorizon.MODID + ".set.success", player.getDisplayName(), amount), true);
            return 0;
        }
        throw UNKNOWN.create();
    }

    private static int setMaxZen(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        if(source.getWorld().isRemote) return 0;
        Entity player = EntityArgument.getEntity(context, "player");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        LazyOptional<Zen> capability = Zen.get(player);
        if(capability.resolve().isPresent()) {
            Zen cap = capability.resolve().get();
            cap.setMaxZen(amount);
            cap.sync((ServerPlayerEntity) player);
            source.sendFeedback(new TranslationTextComponent("command." + ZenHorizon.MODID + ".setmax.success", player.getDisplayName(), amount), true);
            return 0;
        }
        throw UNKNOWN.create();
    }
}
