package at.gkgo.canon.mixin;

import at.gkgo.canon.Canon;
import at.gkgo.canon.blocknbt.BNComponent;
import at.gkgo.canon.meta.MetaItem;
import com.mojang.serialization.Codec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockItemMixin implements MetaItem {
    @Inject(at = @At("HEAD"), method = "writeNbtToBlockEntity")
    private static void canon$writeMetaToBlockNBT(World world, PlayerEntity player, BlockPos pos, ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        var x = BNComponent.get(world,pos).copy();
        x.put(Canon.META,stack.getOrCreateSubNbt(Canon.META));
        BNComponent.insert(world,pos,x);
    }

    @Override
    public Codec<?> canon$meta() {
        return ((MetaItem) ((BlockItem)(Object)this).getBlock()).canon$meta();
    }
}
