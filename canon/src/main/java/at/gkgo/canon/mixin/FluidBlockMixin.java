package at.gkgo.canon.mixin;

import at.gkgo.canon.Canon;
import at.gkgo.canon.blocknbt.BNComponent;
import at.gkgo.canon.meta.Meta;
import at.gkgo.canon.meta.MetaItem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FluidBlock.class)
public class FluidBlockMixin implements MetaItem {
    @Shadow @Final protected FlowableFluid fluid;
    @Override
    public Meta<?> canon$meta() {
//        var f = (FluidBlock)(Object)this;
        return ((MetaItem)(fluid)).canon$meta();
    }
    @ModifyReturnValue(at = @At("RETURN"), method = "tryDrainFluid")
    private ItemStack canon$tryMetaFluid(ItemStack original, @Local WorldAccess w, @Local BlockPos pos){
        if(w instanceof World world) {
            if (!BNComponent.get(world, pos).getCompound(Canon.META).equals(new NbtCompound())) {
//            for (var stack : original) {
//                if (stack.getItem() == state.getBlock().asItem()) {
                original.getOrCreateNbt().put(Canon.META, BNComponent.get(world, pos).getCompound(Canon.META));
//                }
//            }
            }
        }
        return original;
    }
}
