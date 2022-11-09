package com.wosaj.zenhorizon.common.recipe;

import com.wosaj.zenhorizon.ZenHorizon;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.util.List;

@Mod.EventBusSubscriber(modid = ZenHorizon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ZenHorizonRecipes {
    public static final RecipeType<AltarRecipe> ALTAR_RECIPE_TYPE = new ModRecipeType<>();

    public static final RecipeSerializer<AltarRecipe> ALTAR_RECIPE_SERIALIZER = new AltarRecipe.Serializer();

    public static List<AltarRecipe> getAltarRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(ALTAR_RECIPE_TYPE);
    }

    @Nullable
    public static AltarRecipe getAltarRecipe(Level level, ResourceLocation id) {
        return getAltarRecipes(level).stream().filter(r -> r.id.equals(id)).findFirst().orElse(null);
    }

    @SubscribeEvent
    public static void onRegistry(RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, ZenHorizon.rl(AltarRecipe.RECIPE_ID), ALTAR_RECIPE_TYPE);

        event.getRegistry().register(ALTAR_RECIPE_SERIALIZER.setRegistryName(ZenHorizon.rl(AltarRecipe.RECIPE_ID)));
    }

    //Copied from Ars Nouveau
    @SuppressWarnings("all")
    private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }
}
