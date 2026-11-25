package io.minemate;

import java.util.EnumSet;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.SimpleContainer;

public class MineMateEntity extends PathfinderMob {

    private final SimpleContainer inventory = new SimpleContainer(9);

    public MineMateEntity(
            EntityType<? extends MineMateEntity> entityType,
            Level level
    ) {
        super(entityType, level);

        inventory.setItem(0, new ItemStack(Items.DIRT, 64));

        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(2, new FollowPlayerGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(
                2,
                new NearestAttackableTargetGoal<>(
                        this,
                        Monster.class,
                        true
                )
        );
    }

    public SimpleContainer getInventory() {
        return inventory;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 4.0);
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

            return bot.getTarget() == null
                    && player != null
                    && bot.distanceToSqr(player) > 9.0;
        }

        @Override
        public boolean canContinueToUse() {
            return bot.getTarget() == null
                    && player != null
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
