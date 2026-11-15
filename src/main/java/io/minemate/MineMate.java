
package io.minemate;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;

public final class MineMate implements ModInitializer {
    public static final String MOD_ID = "minemate";

    public static EntityType<MineMateEntity> MINE_MATE;

    @Override
    public void onInitialize() {
        ResourceKey<EntityType<?>> key = ResourceKey.create(
                Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(MOD_ID, "minemate")
        );

        MINE_MATE = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                key,
                EntityType.Builder.of(MineMateEntity::new, MobCategory.CREATURE)
                        .sized(0.6f, 1.8f)
                        .build(key)
        );

        FabricDefaultAttributeRegistry.register(
                MINE_MATE,
                MineMateEntity.createAttributes()
        );

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

                        MineMateEntity bot = MINE_MATE.create(
                                level,
                                EntitySpawnReason.COMMAND
                        );

                        if (bot == null) return 0;

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
