package at.gkgo.canon.mixin;

import at.gkgo.canon.Canon;
import at.gkgo.canon.blocknbt.BNComponent;
import at.gkgo.canon.guard.GuardHandler;
import at.gkgo.canon.meta.MetaItem;
import at.gkgo.canon.util.TransientStatics;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Block.class)
public class BlockMixin implements MetaItem {
    @Inject(at = @At("HEAD"), method = "dropStack(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V", cancellable = true)
    private static void canon$guardDropStacks(World world, BlockPos pos, ItemStack stack, CallbackInfo ci){
        if(GuardHandler.EVENT.invoker().guarded(world,pos)){
            ci.cancel();
        }
//        stack.getOrCreateNbt().put("$", BNComponent.get(world,pos).getCompound("$"));
    }
    @Inject(at = @At("HEAD"), method = "dropStack(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/item/ItemStack;)V", cancellable = true)
    private static void canon$guardDropStacksD(World world, BlockPos pos, Direction direction, ItemStack stack, CallbackInfo ci){
        if(GuardHandler.EVENT.invoker().guarded(world,pos)){
            ci.cancel();
        }
    }

    @ModifyReturnValue(at = @At("RETURN"), method = "getDroppedStacks(Lnet/minecraft/block/BlockState;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/entity/BlockEntity;)Ljava/util/List;")
    private static List<ItemStack> canon$tweakDroppedStacksForMeta(List<ItemStack> original, @Local BlockState state, @Local ServerWorld world, @Local BlockPos pos){
        var cm = TransientStatics.transient_interaction_compound;
        if(cm == null){
            cm = BNComponent.get(world,pos);
        }
        if(!cm.getCompound(Canon.META).equals(new NbtCompound())) {
            for (var stack : original) {
                if (stack.getItem() == state.getBlock().asItem()) {
                    stack.getOrCreateNbt().put(Canon.META, cm.getCompound(Canon.META));
                }
            }
        }
        return original;
    }

    @Override
    public Codec<?> canon$meta() {
        return Codec.unit(Unit.INSTANCE);
    }
}
