package net.d1g1byt3.derpapropos.block.entity;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DerpAproposMod.MOD_ID);

  // public static final RegistryObject<EntityType<ModZombieVillagerEntity>>

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
