package tk.wosaj.zenhorizon.item.loader;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.client.model.geometry.IModelGeometry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tk.wosaj.zenhorizon.ZenHorizon;
import tk.wosaj.zenhorizon.item.spells.AbstractSpell;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Function;

public class WandLoader implements IModelLoader<WandLoader.WandGeometry> {
    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {}

    @Nonnull
    @Override
    public WandGeometry read(JsonDeserializationContext ctx, JsonObject contents) {
        return new WandGeometry(ctx.deserialize(contents.get("base_model"), BlockModel.class));
    }

    static class WandGeometry implements IModelGeometry<WandGeometry> {
        private final BlockModel handleModel;

        WandGeometry(BlockModel handleModel) { this.handleModel = handleModel; }

        @Override
        public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
            IBakedModel bakedHandle = handleModel.bakeModel(bakery, handleModel, spriteGetter, modelTransform, modelLocation, false);
            return new WandOverrideModel(bakedHandle);
        }

        @Override
        public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            return handleModel.getTextures(modelGetter, missingTextureErrors);
        }
    }

    private static class WandOverrideModel extends BakedModelWrapper<IBakedModel>
    {
        private final ItemOverrideList overrideList;

        WandOverrideModel(IBakedModel originalModel) {
            super(originalModel);
            this.overrideList = new WandOverrideList();
        }

        @Nonnull
        @Override
        public ItemOverrideList getOverrides() {
            return overrideList;
        }
    }

    private static class WandModel extends BakedModelWrapper<IBakedModel> {
        private final List<BakedQuad> quads;

        WandModel(IBakedModel handleModel, List<BakedQuad> quads) {
            super(handleModel);
            this.quads = quads;
        }

        @Nonnull
        @Override
        public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand) {
            return quads;
        }

        @Nonnull
        @ParametersAreNonnullByDefault
        @Override
        public IBakedModel handlePerspective(ItemCameraTransforms.TransformType transformType, MatrixStack stack) {
            return ForgeHooksClient.handlePerspective(this, transformType, stack);
        }
    }

    private static class WandOverrideList extends ItemOverrideList {
        private static final Random RANDOM = new Random();

        @SuppressWarnings("all")
        @Nullable
        @Override
        public IBakedModel getOverrideModel(@Nonnull IBakedModel model, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
            AbstractSpell spell = stack.hasTag() ? AbstractSpell.byId(stack.getTag().getString("spellId")) : AbstractSpell.byId(ZenHorizon.MODID + ":blank");
            List<BakedQuad> quads = new ArrayList<>(model.getQuads(null, null, RANDOM, EmptyModelData.INSTANCE));
            IBakedModel headModel = Minecraft.getInstance().getModelManager().getModel(spell.getSprite());
            quads.addAll(headModel.getQuads(null, null, RANDOM, EmptyModelData.INSTANCE));
            return new WandModel(model, quads);
        }
    }

    @Mod.EventBusSubscriber(modid = ZenHorizon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onModelRegister(ModelRegistryEvent event) {
            ModelLoaderRegistry.registerLoader(new ResourceLocation(ZenHorizon.MODID, "wand_loader"), new WandLoader());
            AbstractSpell.spells.forEach(spell -> ModelLoader.addSpecialModel(spell.getSprite()));
        }
    }
}