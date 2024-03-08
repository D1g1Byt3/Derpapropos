package net.d1g1byt3.derpapropos.item;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DerpAproposMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DERPAPROPOS_TAB = CREATIVE_MODE_TABS.register("derpapropos_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ALEXANDRITE.get()))
                    .title(Component.translatable("creativetab.derpapropos_tab"))
                    .displayItems((displayParameters, output) -> {
                        {
                            List<Item> items = ModItems.ITEMS.getEntries()
                                    .stream()
                                    .map(RegistryObject::get).toList();
                            for (Item item : items) {
                                output.accept(item);
                            }
                        }

                        {
                            List<Block> blocks = ModBlocks.BLOCKS.getEntries()
                                    .stream()
                                    .map(RegistryObject::get).toList();
                            for (Block block : blocks) {
                                output.accept(block);
                            }
                        }
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
