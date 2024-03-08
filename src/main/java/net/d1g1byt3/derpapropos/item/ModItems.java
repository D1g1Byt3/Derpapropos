package net.d1g1byt3.derpapropos.item;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.item.custom.MetalDetectorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, DerpAproposMod.MOD_ID);

    public static final RegistryObject<Item> ALEXANDRITE = ITEMS.register("alexandrite",
            () -> new Item (new Item.Properties()));
    public static final RegistryObject<Item> RAW_ALEXANDRITE = ITEMS.register("raw_alexandrite",
            () -> new Item (new Item.Properties()));


    //Custom Items
    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties()));


    //Food Items
    public static final RegistryObject<Item> KOHLRABI = ITEMS.register("kohlrabi",
            () -> new Item(new Item.Properties().food(ModFoodProperties.KOHLRABI)));

    public static final RegistryObject<Item> COFFEE_DRINK = ITEMS.register("coffee_drink",
            () -> new Item(new Item.Properties().food(ModFoodProperties.COFFEE_DRINK)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);

    }
}
