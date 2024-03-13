package net.d1g1byt3.derpapropos.datagen;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocksStateProvider extends BlockStateProvider {
    public ModBlocksStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, DerpAproposMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.ALEXANDRITE_BLOCK);
        blockWithItem(ModBlocks.RAW_ALEXANDRITE_BLOCK);

        blockWithItem(ModBlocks.ALEXANDRITE_ORE);
        blockWithItem(ModBlocks.DEEPSLATE_ALEXANDRITE_ORE);
        blockWithItem(ModBlocks.NETHER_ALEXANDRITE_ORE);
        blockWithItem(ModBlocks.END_STONE_ALEXANDRITE_ORE);

        blockWithItem(ModBlocks.SOUND_BLOCK);

        stairsBlock((StairBlock) ModBlocks.ALEXANDRITE_STAIRS.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        slabBlock((SlabBlock) ModBlocks.ALEXANDRITE_SLAB.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));

        buttonBlock((ButtonBlock) ModBlocks.ALEXANDRITE_BUTTON.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        pressurePlateBlock((PressurePlateBlock) ModBlocks.ALEXANDRITE_PRESSURE_PLATE.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        fenceBlock((FenceBlock) ModBlocks.ALEXANDRITE_FENCE.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        fenceGateBlock((FenceGateBlock) ModBlocks.ALEXANDRITE_FENCE_GATE.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));
        wallBlock((WallBlock) ModBlocks.ALEXANDRITE_WALL.get(), blockTexture(ModBlocks.ALEXANDRITE_BLOCK.get()));

        blockItem(ModBlocks.ALEXANDRITE_STAIRS);
        blockItem(ModBlocks.ALEXANDRITE_SLAB);
        blockItem(ModBlocks.ALEXANDRITE_PRESSURE_PLATE);
        blockItem(ModBlocks.ALEXANDRITE_FENCE_GATE);

    }

    private void blockItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("derpapropos:block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject){
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

}
