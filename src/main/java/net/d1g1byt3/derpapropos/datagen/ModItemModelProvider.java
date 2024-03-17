package net.d1g1byt3.derpapropos.datagen;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.block.ModBlocks;
import net.d1g1byt3.derpapropos.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Objects;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, DerpAproposMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.RAW_ALEXANDRITE);
        simpleItem(ModItems.ALEXANDRITE);
        simpleItem(ModItems.KOHLRABI);
        simpleItem(ModItems.ORE_DETECTOR);
        simpleItem(ModItems.PEAT_BRICK);
        simpleItem(ModItems.COFFEE_DRINK);
        simpleItem(ModItems.COFFEE_CHERRIES);

        simpleItem(ModItems.KOHLRABI_SEEDS);
        simpleItem(ModItems.COFFEE_CHERRY_SEEDS);

        //simpleItem(ModItems.DATA_TABLET);

        handheldItem(ModItems.ALEXANDRITE_SWORD);
        handheldItem(ModItems.ALEXANDRITE_PICKAXE);
        handheldItem(ModItems.ALEXANDRITE_AXE);
        handheldItem(ModItems.ALEXANDRITE_SHOVEL);
        handheldItem(ModItems.ALEXANDRITE_HOE);
        handheldItem(ModItems.ALEXANDRITE_PAXEL);

        buttonItem(ModBlocks.ALEXANDRITE_BUTTON, ModBlocks.ALEXANDRITE_BLOCK);
        fenceItem(ModBlocks.ALEXANDRITE_FENCE, ModBlocks.ALEXANDRITE_BLOCK);
        wallItem(ModBlocks.ALEXANDRITE_WALL, ModBlocks.ALEXANDRITE_BLOCK);

        simpleBlockItem(ModBlocks.ALEXANDRITE_DOOR);

        complexBlock(ModBlocks.GEM_EMPOWERING_STATION.get());
    }


    private void complexBlock(Block block){
        withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath(), new ResourceLocation(DerpAproposMod.MOD_ID,
                "block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath()));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock){
        this.withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture", new ResourceLocation(DerpAproposMod.MOD_ID, "block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(baseBlock.get())).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock){
        this.withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", new ResourceLocation(DerpAproposMod.MOD_ID, "block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(baseBlock.get())).getPath()));
    }

    private void simpleBlockItem(RegistryObject<Block> item) {
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DerpAproposMod.MOD_ID, "item/" + item.getId().getPath()));

    }

    private void handheldItem(RegistryObject<Item> item) {
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(DerpAproposMod.MOD_ID, "item/" + item.getId().getPath()));

    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock){
        this.withExistingParent(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath(), mcLoc("block/button_inventory"))
                .texture("texture", new ResourceLocation(DerpAproposMod.MOD_ID, "block/" + Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(baseBlock.get())).getPath()));
    }

    private void simpleItem(RegistryObject<Item> item){
        withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(DerpAproposMod.MOD_ID, "item/" + item.getId().getPath()));
    }

}
