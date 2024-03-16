package net.d1g1byt3.derpapropos.block.entity;

import com.mojang.logging.LogUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.village.ReputationEventType;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.UUID;

public class ModZombieVillagerEntity extends ZombieVillager {

    private static final Logger LOGGER = LogUtils.getLogger();
    private static final EntityDataAccessor<Boolean> DATA_CONVERTING_ID = SynchedEntityData.defineId(ZombieVillager.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<VillagerData> DATA_VILLAGER_DATA = SynchedEntityData.defineId(ZombieVillager.class, EntityDataSerializers.VILLAGER_DATA);
    private static final int VILLAGER_CONVERSION_WAIT_MIN = 3600;
    private static final int VILLAGER_CONVERSION_WAIT_MAX = 6000;
    private static final int MAX_SPECIAL_BLOCKS_COUNT = 14;
    private static final int SPECIAL_BLOCK_RADIUS = 4;
    private int villagerConversionTime;
    @Nullable
    private UUID conversionStarter;
    @Nullable
    private Tag gossips;
    @Nullable
    private CompoundTag tradeOffers;
    private int villagerXp;
    public ModZombieVillagerEntity(EntityType<? extends ZombieVillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public boolean isConverting() {
        return super.isConverting();
    }


    public void finishConversion(ServerLevel pServerLevel) {
        Villager villager = this.convertTo(EntityType.VILLAGER, false);

        for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            ItemStack itemstack = this.getItemBySlot(equipmentslot);
            if (!itemstack.isEmpty()) {
                if (EnchantmentHelper.hasBindingCurse(itemstack)) {
                    villager.getSlot(equipmentslot.getIndex() + 300).set(itemstack);
                } else {
                    double d0 = (double)this.getEquipmentDropChance(equipmentslot);
                    if (d0 > 1.0D) {
                        this.spawnAtLocation(itemstack);
                    }
                }
            }
        }

        villager.setVillagerData(this.getVillagerData());
        if (this.gossips != null) {
            villager.setGossips(this.gossips);
        }

        if (this.tradeOffers != null) {
            villager.setOffers(new MerchantOffers(this.tradeOffers));
        }

        villager.setVillagerXp(this.villagerXp);
        villager.finalizeSpawn(pServerLevel, pServerLevel.getCurrentDifficultyAt(villager.blockPosition()), MobSpawnType.CONVERSION, (SpawnGroupData)null, (CompoundTag)null);
        villager.refreshBrain(pServerLevel);
        if (this.conversionStarter != null) {
            Player player = pServerLevel.getPlayerByUUID(this.conversionStarter);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.CURED_ZOMBIE_VILLAGER.trigger((ServerPlayer)player, this, villager);
                pServerLevel.onReputationEvent(ReputationEventType.ZOMBIE_VILLAGER_CURED, player, villager);
            }
        }

        villager.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
        if (!this.isSilent()) {
            pServerLevel.levelEvent((Player)null, 1027, this.blockPosition(), 0);
        }
        net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, villager);
    }

    private int getConversionProgress() {
        int i = 1;
        if (this.random.nextFloat() < 0.01F) {
            int j = 0;
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int k = (int)this.getX() - 4; k < (int)this.getX() + 4 && j < 14; ++k) {
                for(int l = (int)this.getY() - 4; l < (int)this.getY() + 4 && j < 14; ++l) {
                    for(int i1 = (int)this.getZ() - 4; i1 < (int)this.getZ() + 4 && j < 14; ++i1) {
                        BlockState blockstate = this.level().getBlockState(blockpos$mutableblockpos.set(k, l, i1));
                        if (blockstate.is(Blocks.IRON_BARS) || blockstate.getBlock() instanceof BedBlock) {
                            if (this.random.nextFloat() < 0.3F) {
                                ++i;
                            }

                            ++j;
                        }
                    }
                }
            }
        }

        return i;
    }


}
