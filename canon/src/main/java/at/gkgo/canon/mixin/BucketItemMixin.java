package at.gkgo.canon.mixin;

import at.gkgo.canon.Canon;
import at.gkgo.canon.blocknbt.BNComponent;
import at.gkgo.canon.meta.MetaItem;
import com.mojang.serialization.Codec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketItem.class)
public class BucketItemMixin implements MetaItem {
    @Final
    @Shadow private Fluid fluid;
    @Override
    public Codec<?> canon$meta() {
        return ((MetaItem)(fluid)).canon$meta();
    }
    @Unique private ItemStack transient_stack = ItemStack.EMPTY;
    @Inject(at = @At("HEAD"), method = "use")
    private void canon$make_transient_stack(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        transient_stack = user.getStackInHand(hand);
    }
    @Inject(at = @At("RETURN"), method = "use")
    private void canon$break_transient_stack(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        transient_stack = ItemStack.EMPTY;
    }

    @Inject(at = @At("RETURN"), method = "placeFluid")
    private void canon$placeMetaFluid(PlayerEntity player, World world, BlockPos pos, BlockHitResult hitResult, CallbackInfoReturnable<Boolean> cir){
        if(world.getBlockState(pos) == fluid.getDefaultState().getBlockState()){
            var x = BNComponent.get(world,pos).copy();
            x.put(Canon.META,transient_stack.getOrCreateSubNbt(Canon.META));
            BNComponent.insert(world,pos,x);
        }
    }
}
