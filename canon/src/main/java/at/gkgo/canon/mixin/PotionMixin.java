package at.gkgo.canon.mixin;

import at.gkgo.canon.meta.MetaItem;
import at.gkgo.canon.potion.PotionAsFluid;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Potion.class)
public class PotionMixin implements MetaItem {
//    @Unique
//    private Fluid asFluid;
//
//    @Override
//    public Fluid canon$asFluid() {
//        return asFluid;
//    }
//
//    @Override
//    public void canon$internalSetFluid(Fluid f) {
//asFluid = f;
//    }
//    @Inject(at = @At("RETURN"), method = "<init>(Ljava/lang/String;[Lnet/minecraft/entity/effect/StatusEffectInstance;)V")
//    private void canon$setupWithStr(String baseName, StatusEffectInstance[] effects, CallbackInfo ci){
//        if(baseName != null){
//            if(baseName.equals("water"))asFluid = Fluids.WATER;
//        }
//    }
}
