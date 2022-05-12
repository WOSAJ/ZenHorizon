package tk.wosaj.zenhorizon.item.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractSpell {
    public static final List<AbstractSpell> spells = new ArrayList<>();

    static {
        spells.add(new BlankSpell());
        spells.add(new ShotSpell());
    }

    protected final String id;
    protected final ResourceLocation sprite;

    public AbstractSpell(String modid, String id, ResourceLocation sprite) {
        this.id = modid+':'+id;
        this.sprite = sprite;
    }

    @SuppressWarnings("unused")
    public String getId() {
        return id;
    }

    @Nonnull
    public ResourceLocation getSprite() {
        return sprite;
    }

    public abstract ActionResult<ItemStack> onSpellUse(World world, PlayerEntity entity, Hand hand, ItemStack wand);

    public static void registerSpell(AbstractSpell spell) {
        spells.add(spell);
    }

    @Nonnull
    public static AbstractSpell byId(String id) {
        for (AbstractSpell spell : spells) {
            if(spell.id.equals(id)) return spell;
        }
        return spells.get(0);
    }
}
