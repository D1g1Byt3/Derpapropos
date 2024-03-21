package net.d1g1byt3.derpapropos.util;


import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items{

        private static TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(DerpAproposMod.MOD_ID, name));
        }
        private static TagKey<Item> forgeTag(String name){
            return ItemTags.create(new ResourceLocation("forge", name));
        }

    }

    public static class Blocks{

        public static final TagKey<Block> ORE_DETECTOR_VALUABLES = tag("ore_detector_valuables");
        public static final TagKey<Block> NEEDS_ALEXANDRITE_TOOL = tag("needs_alexandrite_tool");

        public static final TagKey<Block> PAXEL_MINEABLE = tag("mineable/paxel");

        private static TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(DerpAproposMod.MOD_ID, name));
        }
        private static TagKey<Block> forgeTag(String name){
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }
}
