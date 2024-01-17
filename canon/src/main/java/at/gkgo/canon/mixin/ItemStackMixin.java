package at.gkgo.canon.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
class ItemStackMixin {
//    @ModifyReturnValue(at = @At("RETURN"), method = "areItemsEqual")
//    private static boolean canon$makeMetaItemsEqual(boolean original, @Local(ordinal = 0) ItemStack first, @Local(ordinal = 1) ItemStack second){
//        if(original && !first.getOrCreateSubNbt("$").equals(second.getOrCreateSubNbt("$"))){
//            return false;
//        }
//        return original;
//    }
}
