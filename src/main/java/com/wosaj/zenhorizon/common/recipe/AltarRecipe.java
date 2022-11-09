package com.wosaj.zenhorizon.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.block.tile.AltarPrismTile;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class AltarRecipe implements Recipe<AltarPrismTile> {
    public static final String RECIPE_ID = "altar_crafting";

    public final ResourceLocation id;
    public final Ingredient reagent;
    public final Set<Ingredient> items;
    public final int duration;
    public final ItemStack result;
    public final int zenCost;
    public final boolean keepData;

    @SuppressWarnings("all")
    public AltarRecipe(Ingredient reagent, Set<Ingredient> items, int duration, ItemStack result, int zenCost, boolean keepData) {
        this.id = ZenHorizon.rl(result.getItem().getRegistryName().getPath());
        this.reagent = reagent;
        this.items = items;
        this.duration = duration;
        this.result = result;
        this.zenCost = zenCost;
        this.keepData = keepData;
    }

    public AltarRecipe(ResourceLocation id, Ingredient reagent, Set<Ingredient> items, int duration, ItemStack result, int zenCost, boolean keepData) {
        this.id = id;
        this.reagent = reagent;
        this.items = items;
        this.duration = duration;
        this.result = result;
        this.zenCost = zenCost;
        this.keepData = keepData;
    }

    @Override
    public boolean matches(AltarPrismTile tile, Level level) {
        var copy = new HashSet<>(tile.getItems());
        if(reagent.test(tile.getCentralStack()) && items.size() == copy.size()) {
            for (Ingredient item : items) {
                boolean flag = true;
                for (ItemStack tileItem : copy) {
                    if(item.test(tileItem)) {
                        copy.remove(tileItem);
                        flag = false;
                        break;
                    }
                }
                if(flag) return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public ItemStack assemble(AltarPrismTile pContainer) {
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ZenHorizonRecipes.ALTAR_RECIPE_SERIALIZER;
    }

    @Override
    public RecipeType<AltarRecipe> getType() {
        return ZenHorizonRecipes.ALTAR_RECIPE_TYPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AltarRecipe that = (AltarRecipe) o;
        return reagent.equals(that.reagent) && items.equals(that.items) && result.equals(that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reagent, items, result);
    }

    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AltarRecipe> {
        @Override
        public AltarRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
            var reagent = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "reagent"));
            var duration = GsonHelper.getAsInt(json, "duration");
            var result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            var cost = json.has("zenCost") ? GsonHelper.getAsInt(json, "zenCost") : 0;
            var keepNbt = json.has("keepNbt") && GsonHelper.getAsBoolean(json, "keepNbt");
            var items = new HashSet<Ingredient>();
            for(JsonElement e : GsonHelper.getAsJsonArray(json, "items"))
                items.add(Ingredient.fromJson(e.getAsJsonObject()));
            return new AltarRecipe(pRecipeId, reagent, items, duration, result, cost, keepNbt);
        }

        @Nullable
        @Override
        public AltarRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf buf) {
            var reagent = Ingredient.fromNetwork(buf); //1
            var duration = buf.readInt(); //2
            var result = buf.readItem(); //3

            var length = buf.readInt(); //4
            var items = new HashSet<Ingredient>(); //5
            for (int i = 0; i < length; i++) {
                try {
                    items.add(Ingredient.fromNetwork(buf));
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            var cost = buf.readInt(); //6
            var keepNbt = buf.readBoolean(); //7
            return new AltarRecipe(pRecipeId, reagent, items, duration, result, cost, keepNbt);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, AltarRecipe recipe) {
            recipe.reagent.toNetwork(buf); //1
            buf.writeInt(recipe.duration); //2
            buf.writeItem(recipe.result); //3
            buf.writeInt(recipe.items.size()); //4
            recipe.items.forEach(r -> r.toNetwork(buf)); //5
            buf.writeInt(recipe.zenCost); //6
            buf.writeBoolean(recipe.keepData); //7
        }
    }
}
