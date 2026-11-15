
package io.minemate;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.phys.Vec3;

public final class MineMate implements ModInitializer {
    public static final String MOD_ID = "minemate";

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(Commands.literal("minemate")

                .then(Commands.literal("status")
                    .executes(context -> {
                        context.getSource().sendSuccess(
                            () -> Component.literal("MineMate-AI is loaded!"),
                            false
                        );
                        return Command.SINGLE_SUCCESS;
                    })
                )

                .then(Commands.literal("spawn")
                    .executes(context -> {
                        var source = context.getSource();
                        var level = source.getLevel();

                        ArmorStand bot = EntityType.ARMOR_STAND.create(level);

                        if (bot == null) {
                            return 0;
                        }

                        Vec3 pos = source.getPosition();

                        bot.setPos(pos.x + 1.5, pos.y, pos.z);
                        bot.setCustomName(Component.literal("MineMate"));
                        bot.setCustomNameVisible(true);

                        level.addFreshEntity(bot);

                        source.sendSuccess(
                            () -> Component.literal("MineMate has spawned!"),
                            false
                        );

                        return Command.SINGLE_SUCCESS;
                    })
                )
            )
        );
    }
}

