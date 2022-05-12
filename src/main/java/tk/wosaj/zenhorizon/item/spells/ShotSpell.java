package tk.wosaj.zenhorizon.item.spells;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import tk.wosaj.zenhorizon.ZenHorizon;

public class ShotSpell extends AbstractSpell {
    public ShotSpell() {
        super(ZenHorizon.MODID, "shot", new ResourceLocation(ZenHorizon.MODID + ":spell/shot"));
    }

    @Override
    public ActionResult<ItemStack> onSpellUse(World world, PlayerEntity entity, Hand hand, ItemStack wand) {
        FireballEntity fireballEntity = new FireballEntity(EntityType.FIREBALL, world);
        Vector3d vec = entity.getPositionVec().add(0, entity.getEyeHeight(), 0).add(entity.getLookVec());
        fireballEntity.setPosition(vec.x, vec.y, vec.z);
        world.addEntity(fireballEntity);
        return ActionResult.resultSuccess(wand);
    }
}
