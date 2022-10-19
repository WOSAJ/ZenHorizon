package com.wosaj.zenhorizon.datagen;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.item.ZenHorizonItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

@javax.annotation.ParametersAreNonnullByDefault
public class ZenHorizonItemModelProvider extends ItemModelProvider {
    public ZenHorizonItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ZenHorizon.MODID, existingFileHelper);
    }

    protected void blockItem(Item item) {
        if(item.getRegistryName() == null) return;
        getBuilder(item.getRegistryName().toString())
                .parent(getExistingFile(modLoc("block/"+item.getRegistryName().getPath())));
    }

    protected void simpleItem(Item item, ResourceLocation texture) {
        assert item.getRegistryName() != null : "Item registry name is null";
        ResourceLocation location = new ResourceLocation(texture.getNamespace(), "item/" + texture.getPath());
        if(existingFileHelper.exists(location, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            getBuilder(item.getRegistryName().toString())
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", location);
        } else throw new RuntimeException("Texture for " + item.getRegistryName() + " not exists");
    }

    protected void simpleItem(Item item) {
        assert item.getRegistryName() != null : "Item registry name is null";
        simpleItem(item, item.getRegistryName());
    }

    protected void handheldItem(Item item, ResourceLocation texture) {
        assert item.getRegistryName() != null : "Item registry name is null";
        ResourceLocation location = new ResourceLocation(texture.getNamespace(), "item/" + texture.getPath());
        if(existingFileHelper.exists(location, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            getBuilder(item.getRegistryName().toString())
                    .parent(getExistingFile(mcLoc("item/handheld")))
                    .texture("layer0", location);
        } else throw new RuntimeException("Texture for " + item.getRegistryName() + " not exists");
    }

    protected void handheldItem(Item item) {
        assert item.getRegistryName() != null : "Item registry name is null";
        handheldItem(item, item.getRegistryName());
    }

    protected void bigHandheldItem(Item item, ResourceLocation texture) {
        assert item.getRegistryName() != null : "Item registry name is null";
        ResourceLocation location = new ResourceLocation(texture.getNamespace(), "item/" + texture.getPath());
        if(existingFileHelper.exists(location, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            getBuilder(item.getRegistryName().toString())
                    .parent(getExistingFile(modLoc("item/big_handheld")))
                    .texture("layer0", location);
        } else throw new RuntimeException("Texture for " + item.getRegistryName() + " not exists");
    }

    protected void bigHandheldItem(Item item) {
        assert item.getRegistryName() != null : "Item registry name is null";
        bigHandheldItem(item, item.getRegistryName());
    }

    @Override
    protected void registerModels() {
        simpleItem(ZenHorizonItems.METAWINGS.get());
        simpleItem(ZenHorizonItems.POTION_MASK.get());
        bigHandheldItem(ZenHorizonItems.MITHRIL_SWORD.get());
        simpleItem(ZenHorizonItems.MITHRIL_INGOT.get());

        blockItem(ZenHorizonItems.BLACKSTONE_PEDESTAL.get());
    }
}
