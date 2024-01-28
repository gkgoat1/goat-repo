package at.gkgo.canon.mixin;

import at.gkgo.canon.meta.ForceRandomTicks;
import at.gkgo.canon.meta.MetaItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.class)
public class AbstractBlockMixin implements ForceRandomTicks {
    @Mutable
    @Final
    @Shadow
    protected boolean randomTicks;
    @Inject(at = @At("RETURN"), method = "randomTick")
    private void canon$fireTickEvent(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci){

    }

    @Override
    public void canon$forceRandomTicks() {
        randomTicks = true;
    }
}
