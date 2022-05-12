package tk.wosaj.zenhorizon;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tk.wosaj.zenhorizon.item.WandItem;
import tk.wosaj.zenhorizon.item.curio.back.Metawings;

@SuppressWarnings("unused")
public class Registers {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, ZenHorizon.MODID);
    public static final RegistryObject<Item> wand = items.register("wand", WandItem::new);
    public static final RegistryObject<Item> metawings = items.register("metawings", Metawings::new);
}
