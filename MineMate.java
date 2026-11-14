package io.minemate;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public final class MineMate implements ModInitializer {
    public static final String MOD_ID = "minemate";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(Commands.literal("minemate")
                .then(Commands.literal("status")
                    .executes(context -> {
                        context.getSource().sendSuccess(
                            () -> Component.literal("MineMate-AI is loaded. Companion features are being built."),
                            false
                        );
                        return Command.SINGLE_SUCCESS;
                    })
                )
            )
        );
    }
}
