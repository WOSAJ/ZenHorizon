package tk.wosaj.zenhorizon.item.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import tk.wosaj.zenhorizon.ZenHorizon;

public class BlankSpell extends AbstractSpell {
    public BlankSpell() {
        super(ZenHorizon.MODID, "blank", new ResourceLocation(ZenHorizon.MODID + ":spell/blank"));
    }

    @Override
    public ActionResult<ItemStack> onSpellUse(World world, PlayerEntity entity, Hand hand, ItemStack wand) {
        return ActionResult.resultFail(wand);
    }
}
