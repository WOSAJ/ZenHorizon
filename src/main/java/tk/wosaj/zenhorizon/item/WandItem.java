package tk.wosaj.zenhorizon.item;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import tk.wosaj.zenhorizon.item.spells.AbstractSpell;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WandItem extends Item {
    public WandItem() {
        super(new Properties().maxStackSize(1).group(ItemGroup.COMBAT));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity entity, Hand hand) {
        ItemStack itemStack = entity.getHeldItem(hand);
        if (!itemStack.hasTag()) {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putString("spellId", "zenhorizon:blank");
            itemStack.setTag(compoundNBT);
        }
        if(itemStack.getTag() == null) return ActionResult.resultFail(itemStack);
        if (!itemStack.getTag().contains("spellId"))
            itemStack.getTag().putString("spellId", "zenhorizon:blank");
        String spellId = itemStack.getTag().getString("spellId");
        AbstractSpell spell = AbstractSpell.byId(spellId);
        if (entity.isSneaking()) { //TODO fix spell change
            int index = AbstractSpell.spells.indexOf(spell) + 1;
            itemStack.getTag().putString(
                    "spellId",
                    AbstractSpell.spells.get((index >= AbstractSpell.spells.size()) ? 0 : index).getId());
        } else {
            return spell.onSpellUse(world, entity, hand, itemStack);
        }
        return ActionResult.resultFail(itemStack);
    }


}
