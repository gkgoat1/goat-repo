package at.gkgo.canon.mixin;

import at.gkgo.canon.Canon;
import at.gkgo.canon.blocknbt.BNComponent;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowableFluid.class)
public class FlowableFluidMixin {
    @Unique private BlockPos transient_pos;
    @Inject(at = @At("RETURN"), method = "flow")
    void canon$reflow(WorldAccess w, BlockPos pos, BlockState state, Direction direction, FluidState fluidState, CallbackInfo ci){
        if(w instanceof World world){
        var x = BNComponent.get(world,pos).copy();
        x.put(Canon.META,BNComponent.get(world,transient_pos).getCompound(Canon.META));
        BNComponent.insert(world,pos,x);
        }
    }
    @Inject(at = @At("HEAD"), method = "tryFlow")
    void canon$storeUpdatedState(World world, BlockPos fluidPos, FluidState state, CallbackInfo ci){
        transient_pos = fluidPos;
    }
    @Inject(at = @At("RETURN"), method = "tryFlow")
    void canon$unstoreUpdatedState(World world, BlockPos fluidPos, FluidState state, CallbackInfo ci){
        transient_pos = null;
    }
}
