package com.wosaj.zenhorizon.util;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.capability.attacher.CapabilityZen;
import com.wosaj.zenhorizon.capability.storage.player.Zen;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
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
                        .then(Commands.argument("amount", IntegerArgumentType.integer(10))
                        .executes(ZenHorizonCommand::setMaxZen)))
                )
        )
        );
    }

    private static int getZen(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        Entity player = EntityArgument.getEntity(context, "player");
        LazyOptional<Zen> capability = player.getCapability(CapabilityZen.INSTANCE);
        if(capability.resolve().isPresent()) {
            Zen cap = capability.resolve().get();
            source.sendFeedback(new TranslationTextComponent("command." + ZenHorizon.MODID + ".get.success",player.getDisplayName().getString(), cap.zen, cap.maxZen), false);
            return 0;
        }
        throw UNKNOWN.create();
    }

    private static int setZen(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        Entity player = EntityArgument.getEntity(context, "player");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        LazyOptional<Zen> capability = player.getCapability(CapabilityZen.INSTANCE);
        if(capability.resolve().isPresent()) {
            Zen cap = capability.resolve().get();
            if(amount > cap.maxZen) throw SET_TOO_MUCH.create();
            cap.zen = amount;
            source.sendFeedback(new TranslationTextComponent("command." + ZenHorizon.MODID + ".set.success", player.getDisplayName(), amount), true);
            return 0;
        }
        throw UNKNOWN.create();
    }

    private static int setMaxZen(CommandContext<CommandSource> context) throws CommandSyntaxException {
        CommandSource source = context.getSource();
        Entity player = EntityArgument.getEntity(context, "player");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        LazyOptional<Zen> capability = player.getCapability(CapabilityZen.INSTANCE);
        if(capability.resolve().isPresent()) {
            Zen cap = capability.resolve().get();
            if(cap.zen > amount) cap.zen = amount;
            cap.maxZen = amount;
            source.sendFeedback(new TranslationTextComponent("command." + ZenHorizon.MODID + ".setmax.success", player.getDisplayName(), amount), true);
            return 0;
        }
        throw UNKNOWN.create();
    }
}
