
package io.minemate;

import java.util.EnumSet;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class MineMateEntity extends PathfinderMob {

    public MineMateEntity(
            EntityType<? extends MineMateEntity> entityType,
            Level level
    ) {
        super(entityType, level);

        this.goalSelector.addGoal(1, new FollowPlayerGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    private static class FollowPlayerGoal extends Goal {

        private final MineMateEntity bot;
        private Player player;

        public FollowPlayerGoal(MineMateEntity bot) {
            this.bot = bot;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            player = bot.level().getNearestPlayer(bot, 16.0);
            return player != null && bot.distanceToSqr(player) > 9.0;
        }

        @Override
        public boolean canContinueToUse() {
            return player != null
                    && player.isAlive()
                    && bot.distanceToSqr(player) > 9.0
                    && bot.distanceToSqr(player) < 256.0;
        }

        @Override
        public void tick() {
            if (player != null) {
                bot.getNavigation().moveTo(player, 1.1);
            }
        }

        @Override
        public void stop() {
            player = null;
            bot.getNavigation().stop();
        }
    }
}
