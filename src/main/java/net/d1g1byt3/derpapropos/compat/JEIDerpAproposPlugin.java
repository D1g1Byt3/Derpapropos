package net.d1g1byt3.derpapropos.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.d1g1byt3.derpapropos.recipe.GemEmpoweringRecipe;
import net.d1g1byt3.derpapropos.screen.GemEmpoweringStationScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIDerpAproposPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(DerpAproposMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new GemEmpoweringRecipeCategory(
                registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<GemEmpoweringRecipe> empoweringRecipe = recipeManager.getAllRecipesFor(GemEmpoweringRecipe.Type.INSTANCE);
        registration.addRecipes(GemEmpoweringRecipeCategory.GEM_EMPOWERING_TYPE, empoweringRecipe);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(GemEmpoweringStationScreen.class, 60, 40, 20, 30,
                GemEmpoweringRecipeCategory.GEM_EMPOWERING_TYPE);
    }
}
