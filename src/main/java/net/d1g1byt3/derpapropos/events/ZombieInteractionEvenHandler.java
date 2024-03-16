package net.d1g1byt3.derpapropos.events;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.block.entity.ModZombieVillagerEntity;
import net.d1g1byt3.derpapropos.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.ZombieHorse;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles all {@link PlayerInteractEvent.EntityInteractSpecific}s and filters out zombie-related events to {@code #playerInteractsWithZombie}
 *
 * @since 0.1
 */
@Mod.EventBusSubscriber(modid = DerpAproposMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ZombieInteractionEvenHandler {
    /**
     * Called whenever a {@link PlayerInteractEvent.EntityInteractSpecific} is called
     *
     * @param event the {@link PlayerInteractEvent.EntityInteractSpecific}
     * @since 0.1
     */
    @SubscribeEvent
    public static void playerInteractsWithAnything(PlayerInteractEvent.EntityInteractSpecific event) {

        //We only need to process this in the server side

        //TODO #5 allow conversion with coffee drink
        if (!event.getSide().isClient() && (event.getEntity().getItemInHand(event.getHand()).getItem().equals(ModItems.COFFEE_DRINK))) {

            EntityType<?> type = event.getTarget().getType();
            if (type.equals(EntityType.ZOMBIE)) {
                playerInteractsWithZombie(event, (Zombie) event.getTarget());
            } else if (EntityType.ZOMBIE_HORSE.equals(type)) {
                playerInteractsWithZombieHorse(event);
            } else if (EntityType.ZOMBIE_VILLAGER.equals((type))) {
                playerInteractsWithZombieVillager(event);
            }
        }
    }

    /**
     * Checks to see if the {@link Mob} can be converted according to current game rules
     *
     * @param mobToConvert the {@link Mob} to convert
     * @return {@code true} if it can be converted, otherwise {@code false}
     * @since 0.1
     */
    private static <T extends Mob> boolean meetsConversionRequirements(T mobToConvert) {
        //TODO #5 allow conversion with no buff
        if (mobToConvert.hasEffect(MobEffects.WEAKNESS)) return true;

        return false;
    }

    /**
     * Only called whenever a {@link PlayerInteractEvent.EntityInteractSpecific} is for a mob that is a zombie mob
     *
     * @param event The {@link PlayerInteractEvent}
     * @param mob the {@link Zombie} mob
     * @since 0.1
     */
    protected static <T extends Zombie> void playerInteractsWithZombie(PlayerInteractEvent.EntityInteractSpecific event, T mob) {
        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20*60*5, 4));
        mob.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20*60*5, 30));
    }

    /**
     * Only called whenever a {@link PlayerInteractEvent.EntityInteractSpecific} is for a mob that is a zombie horse
     *
     * @param event The {@link PlayerInteractEvent}
     * @since 0.1
     */
    protected static void playerInteractsWithZombieHorse(PlayerInteractEvent.EntityInteractSpecific event) {
        ZombieHorse zombieHorse = (ZombieHorse) event.getTarget();
        ServerLevel level = (ServerLevel) event.getLevel();
        if (meetsConversionRequirements(zombieHorse)) {
            Horse horse = zombieHorse.convertTo(EntityType.HORSE, true);

            horse.finalizeSpawn(level, level.getCurrentDifficultyAt(horse.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
            horse.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
            level.levelEvent((Player)null, 1027, horse.blockPosition(), 0);
            event.getEntity().getItemInHand(event.getHand()).shrink(1);
        }
    }

    /**
     * Only called whenever a {@link PlayerInteractEvent.EntityInteractSpecific} is for a mob hat is a zombie villager
     *
     * @param event The {@link PlayerInteractEvent}
     * @since 0.1
     */

    protected static void playerInteractsWithZombieVillager(PlayerInteractEvent.EntityInteractSpecific event) {
        ZombieVillager zombieVillager = (ModZombieVillagerEntity) event.getTarget();
        ServerLevel level = (ServerLevel) event.getLevel();
        if (meetsConversionRequirements(zombieVillager)) {
            Zombie zombie = zombieVillager.convertTo(EntityType.ZOMBIE_VILLAGER, true);

            ((ModZombieVillagerEntity) zombieVillager).finishConversion(level);
            event.getEntity().getItemInHand(event.getHand()).shrink(1);
        }
    }



}
