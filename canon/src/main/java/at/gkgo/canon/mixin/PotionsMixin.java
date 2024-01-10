package at.gkgo.canon.mixin;

import at.gkgo.canon.fluid.SimpleFluid;
import at.gkgo.canon.potion.PotionAsFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Potions.class)
public class PotionsMixin {
    @Inject(at = @At("RETURN"), method = "register(Ljava/lang/String;Lnet/minecraft/potion/Potion;)Lnet/minecraft/potion/Potion;")
    private static void canon$addVanillaPotionFluids(String name, Potion potion, CallbackInfoReturnable<Potion> cir){
        PotionAsFluid fluid = (PotionAsFluid) (Object)potion;
        if(fluid.canon$asFluid() == null){
            if(!Objects.equals(name, "water")) {
                fluid.canon$internalSetFluid(SimpleFluid.register(Identifier.of("minecraft", name)).getStill());
            }else{
                fluid.canon$internalSetFluid(Fluids.WATER);
            }
        }
    }
}
