package net.d1g1byt3.derpapropos.recipe;

import net.d1g1byt3.derpapropos.DerpAproposMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, DerpAproposMod.MOD_ID);

    public static final RegistryObject<RecipeSerializer<GemEmpoweringRecipe>> GEM_EMPOWERING_SERIALIZER =
            SERIALIZERS.register("gem_empowering", ()-> GemEmpoweringRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus){
        SERIALIZERS.register(eventBus);
    }
}
