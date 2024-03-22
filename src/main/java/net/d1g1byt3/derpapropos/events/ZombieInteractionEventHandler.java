package net.d1g1byt3.derpapropos.events;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.UUID;

/**
 * Handles all {@link PlayerInteractEvent.EntityInteractSpecific}s and filters out zombie-related events to {@code #playerInteractsWithZombie}
 *
 * @since 0.1
 */
@Mod.EventBusSubscriber(modid = DerpAproposMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ZombieInteractionEventHandler extends ZombieVillager {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<Boolean> DATA_CONVERTING_ID = SynchedEntityData.defineId(ZombieInteractionEventHandler.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(ZombieInteractionEventHandler.class, EntityDataSerializers.VILLAGER_DATA);

    private int villagerConversionTime;
    @Nullable
    public UUID conversionStarter;
    @Nullable
    private Tag gossips;
    @Nullable
    private CompoundTag tradeOffers;
    private int villagerXp;


    public ZombieInteractionEventHandler(EntityType<? extends ZombieVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CONVERTING_ID, false);
        this.entityData.define(DATA_VILLAGER_DATA, new VillagerData(VillagerType.PLAINS, VillagerProfession.NONE, 1));
    }

    @SubscribeEvent
    public static void playerInteractsWithAnything(PlayerInteractEvent.@NotNull EntityInteractSpecific event) {

        //We only need to process this in the server side
        if (event.getSide().isClient()) {
            return;
        }

        if (event.getEntity().getItemInHand(event.getHand()).getItem().equals(ModItems.COFFEE_DRINK.get())) {

            EntityType<?> type = event.getTarget().getType();
            if (type.equals(EntityType.ZOMBIE)) {
                playerInteractsWithZombie(event, (Zombie) event.getTarget());
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
        mobToConvert.isAlive();
        return true;
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


    protected static void playerInteractsWithZombieVillager(PlayerInteractEvent.EntityInteractSpecific event) {
        ZombieVillager zombieVillager = (ZombieVillager) event.getTarget();
        if (meetsConversionRequirements(zombieVillager)) {
            zombieVillager.startConverting(event.getEntity().getUUID(), 200);
            if (!event.getEntity().getAbilities().instabuild){
                event.getEntity().getItemInHand(event.getHand()).shrink(1);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        VillagerData.CODEC.encodeStart(NbtOps.INSTANCE, this.getVillagerData()).resultOrPartial(LOGGER::error).ifPresent((p_204072_) -> pCompound.put("VillagerData", p_204072_));
        if (this.tradeOffers != null) {
            pCompound.put("Offers", this.tradeOffers);
        }

        if (this.gossips != null) {
            pCompound.put("Gossips", this.gossips);
        }

        pCompound.putInt("ConversionTime", this.isConverting() ? this.villagerConversionTime : -1);
        if (this.conversionStarter != null) {
            pCompound.putUUID("ConversionPlayer", this.conversionStarter);
                    }

        pCompound.putInt("Xp", this.villagerXp);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("VillagerData", 10)) {
            DataResult<VillagerData> dataresult = VillagerData.CODEC.parse(new Dynamic<>(NbtOps.INSTANCE, pCompound.get("VillagerData")));
            dataresult.resultOrPartial(LOGGER::error).ifPresent(this::setVillagerData);
        }

        if (pCompound.contains("Offers", 10)) {
            this.tradeOffers = pCompound.getCompound("Offers");
        }

        if (pCompound.contains("Gossips", 9)) {
            this.gossips = pCompound.getList("Gossips", 10);
        }

        if (pCompound.contains("ConversionTime", 99) && pCompound.getInt("ConversionTime") > -1) {
            this.startConverting(pCompound.hasUUID("ConversionPlayer") ? pCompound.getUUID("ConversionPlayer") : null, pCompound.getInt("ConversionTime"));
        }

        if (pCompound.contains("Xp", 3)) {
            this.villagerXp = pCompound.getInt("Xp");
        }
    }
}