package net.d1g1byt3.derpapropos.datagen;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.block.ModBlocks;
import net.d1g1byt3.derpapropos.datagen.custom.GemEmpoweringRecipeBuilder;
import net.d1g1byt3.derpapropos.item.ModItems;
import net.d1g1byt3.derpapropos.recipe.GemEmpoweringRecipe;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> ALEXANDRITE_SMELTABLES = List.of(ModItems.RAW_ALEXANDRITE.get(), ModBlocks.ALEXANDRITE_ORE.get(),
            ModBlocks.DEEPSLATE_ALEXANDRITE_ORE.get(), ModBlocks.NETHER_ALEXANDRITE_ORE.get(), ModBlocks.END_STONE_ALEXANDRITE_ORE.get());

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ALEXANDRITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.ALEXANDRITE.get())
                .unlockedBy("has_alexandrite", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModItems.ALEXANDRITE.get()).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ALEXANDRITE_SWORD.get())
                .define('A', ModItems.ALEXANDRITE.get()).define('S', Tags.Items.RODS_WOODEN )
                .pattern(" A ").pattern(" A ").pattern(" S ").unlockedBy("has_alexandrite",
                        has(ModItems.ALEXANDRITE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ALEXANDRITE_PICKAXE.get())
                .define('A', ModItems.ALEXANDRITE.get()).define('S', Tags.Items.RODS_WOODEN )
                .pattern("AAA").pattern(" S ").pattern(" S ").unlockedBy("has_alexandrite",
                        has(ModItems.ALEXANDRITE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ALEXANDRITE_AXE.get())
                .define('A', ModItems.ALEXANDRITE.get()).define('S', Tags.Items.RODS_WOODEN )
                .pattern(" AA").pattern(" SA").pattern(" S ").unlockedBy("has_alexandrite",
                        has(ModItems.ALEXANDRITE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ALEXANDRITE_SHOVEL.get())
                .define('A', ModItems.ALEXANDRITE.get()).define('S', Tags.Items.RODS_WOODEN )
                .pattern(" A ").pattern(" S ").pattern(" S ").unlockedBy("has_alexandrite",
                        has(ModItems.ALEXANDRITE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ALEXANDRITE_HOE.get())
                .define('A', ModItems.ALEXANDRITE.get()).define('S', Tags.Items.RODS_WOODEN )
                .pattern(" AA").pattern(" S ").pattern(" S ").unlockedBy("has_alexandrite",
                        has(ModItems.ALEXANDRITE.get()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 9)
                .requires(ModBlocks.ALEXANDRITE_BLOCK.get())
                .unlockedBy("has_alexandrite_block", inventoryTrigger(ItemPredicate.Builder.item().
                        of(ModBlocks.ALEXANDRITE_BLOCK.get()).build()))
                .save(pWriter);

        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, ModItems.RAW_ALEXANDRITE.get(),
                RecipeCategory.MISC, ModBlocks.RAW_ALEXANDRITE_BLOCK.get(),"derpapropos:raw_alexandrite",
                "alexandrite", "derpapropos:raw_alexandrite_block", "alexandrite");

        oreSmelting(pWriter, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.25f, 200, "alexandrite" );
        oreBlasting(pWriter, ALEXANDRITE_SMELTABLES, RecipeCategory.MISC, ModItems.ALEXANDRITE.get(), 0.50f, 100, "alexandrite" );

        new GemEmpoweringRecipeBuilder(ModItems.RAW_ALEXANDRITE.get(), ModItems.ALEXANDRITE.get(), 3)
                .unlockedBy("has_raw_alexandrite", has(ModItems.RAW_ALEXANDRITE.get())).save(pWriter);

        new GemEmpoweringRecipeBuilder(Items.COAL, Items.DIAMOND, 2)
                .unlockedBy("has_diamond", has(Items.DIAMOND)).save(pWriter);


    }

    /*
    protected static void pickaxeTool(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult ){

    }
     */

    protected static void oreSmelting(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTIme, @NotNull String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(@NotNull Consumer<FinishedRecipe> pFinishedRecipeConsumer, @NotNull RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, @NotNull RecipeCategory pCategory, @NotNull ItemLike pResult, float pExperience, int pCookingTime, @NotNull String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime,
                    pCookingSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer, DerpAproposMod.MOD_ID + ":" + (pResult) + pRecipeName + "_" + getItemName(itemlike));
        }

    }
}
