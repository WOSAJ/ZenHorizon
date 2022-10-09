package com.wosaj.zenhorizon.datagen;

import com.wosaj.zenhorizon.ZenHorizon;
import com.wosaj.zenhorizon.common.item.Items;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@javax.annotation.ParametersAreNonnullByDefault
public class ZenHorizonItemModelProvider extends ItemModelProvider {
    private final Logger log = LoggerFactory.getLogger(ZenHorizonItemModelProvider.class);
    public ZenHorizonItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ZenHorizon.MODID, existingFileHelper);
    }

    protected void blockItem(Item item) {
        if(item.getRegistryName() == null) return;
        getBuilder(item.getRegistryName().toString())
                .parent(getExistingFile(modLoc("block/"+item.getRegistryName().getPath())));
    }

    protected void simpleItem(Item item, ResourceLocation texture) {
        if(item.getRegistryName() == null) return;
        ResourceLocation location = new ResourceLocation(texture.getNamespace(), "item/" + texture.getPath());
        if(existingFileHelper.exists(location, PackType.CLIENT_RESOURCES, ".png", "textures")) {
            getBuilder(item.getRegistryName().toString())
                    .parent(getExistingFile(mcLoc("item/generated")))
                    .texture("layer0", location);
        } else log.error("Texture for {} not exists", item.getRegistryName());
    }

    protected void simpleItem(Item item) {
        if(item.getRegistryName() == null) return;
        simpleItem(item, item.getRegistryName());
    }

    @Override
    protected void registerModels() {
        simpleItem(Items.METAWINGS.get());
        simpleItem(Items.POTION_MASK.get());
    }
}
