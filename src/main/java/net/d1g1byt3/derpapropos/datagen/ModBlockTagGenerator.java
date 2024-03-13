package net.d1g1byt3.derpapropos.datagen;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.block.ModBlocks;
import net.d1g1byt3.derpapropos.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DerpAproposMod.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ModTags.Blocks.ORE_DETECTOR_VALUABLES)
                .add(ModBlocks.ALEXANDRITE_ORE.get()).addTags(Tags.Blocks.ORES);

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ALEXANDRITE_BLOCK.get(),
                        ModBlocks.ALEXANDRITE_ORE.get(),
                        ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(),
                        ModBlocks.NETHER_ALEXANDRITE_ORE.get(),
                        ModBlocks.END_STONE_ALEXANDRITE_ORE.get(),
                        ModBlocks.RAW_ALEXANDRITE_BLOCK.get(),
                        ModBlocks.ALEXANDRITE_STAIRS.get(),
                        ModBlocks.ALEXANDRITE_SLAB.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ALEXANDRITE_BLOCK.get(),
                        ModBlocks.ALEXANDRITE_ORE.get(),
                        ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(),
                        ModBlocks.RAW_ALEXANDRITE_BLOCK.get(),
                        ModBlocks.ALEXANDRITE_STAIRS.get(),
                        ModBlocks.ALEXANDRITE_SLAB.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.NETHER_ALEXANDRITE_ORE.get(),
                        ModBlocks.END_STONE_ALEXANDRITE_ORE.get());

        this.tag(BlockTags.FENCES)
                .add(ModBlocks.ALEXANDRITE_FENCE.get());

        this.tag(BlockTags.FENCE_GATES)
                .add(ModBlocks.ALEXANDRITE_FENCE_GATE.get());

        this.tag(BlockTags.WALLS)
                .add(ModBlocks.ALEXANDRITE_WALL.get());
    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
