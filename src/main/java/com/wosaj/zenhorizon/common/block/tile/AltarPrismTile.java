package com.wosaj.zenhorizon.common.block.tile;

import com.wosaj.zenhorizon.common.block.ZenHorizonBlocks;
import com.wosaj.zenhorizon.common.recipe.AltarRecipe;
import com.wosaj.zenhorizon.common.recipe.ZenHorizonRecipes;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class AltarPrismTile extends ZenHorizonTile implements Container {
    public static final short LOCATE_DIAMETER = 11;

    public float frames;

    //Crafting fields
    public AltarRecipe recipe;
    public int craftingTime;

    //Serialization field
    private ResourceLocation id;

    //Optimization fields
    private short reviveAttempts;
    private ItemStack lastItemCache;

    public AltarPrismTile(BlockPos pPos, BlockState pBlockState) {
        super(ZenHorizonBlocks.ALTAR_PRISM_TILE.get(), pPos, pBlockState);
    }

    public static <T> void tick(Level level, BlockPos pos, BlockState state, T blockEntity) {
        if(level.isClientSide) return;
        if(!(blockEntity instanceof AltarPrismTile tile && tile.isInRightPos())) return;
        if(tile.reviveAttempts < 200 && tile.id != null) {
            var rec = ZenHorizonRecipes.getAltarRecipe(level, tile.id);
            if(rec != null) {
                tile.recipe = rec;
                tile.id = null;
            }
            tile.reviveAttempts++;
        }
        if(tile.recipe != null) {
            craftTick(level, pos, state, tile);
            return;
        }
        var reagent = tile.getCentralStack();
        if(tile.lastItemCache == null || !tile.lastItemCache.equals(reagent)) if(!checkCraft(level, pos, state, tile))
            tile.lastItemCache = reagent;
    }

    protected static boolean checkCraft(Level level, BlockPos pos, BlockState state, AltarPrismTile tile) {
        for (var recipe : ZenHorizonRecipes.getAltarRecipes(level)) if(recipe.matches(tile, level)) {
            tile.recipe = recipe;
            tile.craftingTime = 0;
            craftTick(level, pos, state, tile);
            return true;
        }
        return false;
    }

    protected static void craftTick(Level level, BlockPos pos, BlockState state, AltarPrismTile tile) {
        if(!tile.recipe.matches(tile, level)) {
            tile.stopCrafting();
            return;
        }
        tile.getItemPoses(false).stream().map(BlockPos::above).forEach(pose ->
            ((ServerLevel)level).sendParticles(
                    ParticleTypes.WITCH,
                    pose.getX()+0.5,
                    pose.getY()+0.5,
                    pose.getZ()+0.5,
                    (int) (tile.craftingTime/((float) tile.recipe.duration)*10),
                    0,
                    0,
                    0,
                    1
            )
        );

        if(tile.craftingTime >= tile.recipe.duration) endCraft(level, pos, state, tile);
        else tile.craftingTime++;
    }

    protected static void endCraft(Level level, BlockPos pos, BlockState state, AltarPrismTile tile) {
        tile.clearPedestals();
        var result = tile.recipe.result.copy();
        if(tile.recipe.keepData) result.setTag(result.getTag());
        tile.setCentralStack(result);
        tile.stopCrafting();
        ((ServerLevel)level).sendParticles(
                ParticleTypes.TOTEM_OF_UNDYING,
                pos.getX()+0.5,
                pos.getY()+0.5,
                pos.getZ()+0.5,
                50,
                0,
                0,
                0,
                1
        );
    }

    private void stopCrafting() {
        recipe = null;
        craftingTime = 0;
    }

    public Set<ItemStack> getItems() {
        var level = getLevel();
        if(level == null) return Set.of();
        return getItemPoses(false).stream()
                .map(level::getBlockEntity)
                .filter(Objects::nonNull)
                .map(b -> ((BlackstonePedestalTile) b).getItem(0))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("all")
    public Set<BlockPos> getItemPoses(boolean all) {
        if(level == null) return Set.of();
        var reagentPedestalPos = getBlockPos().below(2);
        var list = new HashSet<BlockPos>();
        for(int i = 0; i < LOCATE_DIAMETER; i++) {
            for (int j = 0; j < LOCATE_DIAMETER; j++) {
                var x = getX() + i - LOCATE_DIAMETER * 0.5 - 0.5;
                var z = getZ() + j - LOCATE_DIAMETER * 0.5 - 0.5;
                var pPos = new BlockPos(x, getY() - 2, z);
                var blockState = level.getBlockState(pPos);
                if(!(blockState.isAir() || pPos.equals(reagentPedestalPos))
                        && blockState.getBlock() == ZenHorizonBlocks.BLACKSTONE_PEDESTAL.get()
                        && (all || level.getBlockEntity(pPos) != null && !((BlackstonePedestalTile) level.getBlockEntity(pPos)).isEmpty())) list.add(pPos);
            }
        }
        return list;
    }

    public void clearPedestals() {
        if(level == null) return;
        getItemPoses(false).forEach(pos -> {
            if(level.getBlockEntity(pos) instanceof BlackstonePedestalTile blockTile) blockTile.clearContent();
        });
    }

    public boolean isInRightPos() {
        return level != null && level.getBlockState(getBlockPos().below(2)).getBlock() == ZenHorizonBlocks.BLACKSTONE_PEDESTAL.get();
    }

    public ItemStack getCentralStack() {
        if(level != null) {
            var blockTile = level.getBlockEntity(getBlockPos().below(2));
            if(blockTile instanceof BlackstonePedestalTile pedestalTile) return pedestalTile.getItem(0);
        }
        return ItemStack.EMPTY;
    }

    public void setCentralStack(ItemStack stack) {
        if(level != null) {
            var blockTile = level.getBlockEntity(getBlockPos().below(2));
            if(blockTile instanceof BlackstonePedestalTile pedestalTile) pedestalTile.setStack(stack);
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if(recipe != null || id != null) tag.putString("recipe", recipe == null ? id.toString() : recipe.id.toString());
        tag.putInt("craftingTime", craftingTime);
    }

    @Override
    public CompoundTag getUpdateTag() {
        var tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        if(tag.contains("id")) id = new ResourceLocation(tag.getString("id"));
        craftingTime = tag.getInt("craftingTime");
        super.load(tag);
    }

    // Dumb filler

    @Deprecated
    @Override
    public int getContainerSize() {
        return 0;
    }

    @Deprecated
    @Override
    public boolean isEmpty() {
        return true;
    }

    @Deprecated
    @Override
    public ItemStack getItem(int pSlot) {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    public ItemStack removeItem(int pSlot, int pAmount) {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    public ItemStack removeItemNoUpdate(int pSlot) {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    public void setItem(int pSlot, ItemStack pStack) {}

    @Deprecated
    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }

    @Deprecated
    @Override
    public void clearContent() {}
}
