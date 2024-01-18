package at.gkgo.canon.mixin;

import at.gkgo.canon.Canon;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.impl.transfer.fluid.EmptyBucketStorage;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = EmptyBucketStorage.class, remap = false)
public class EmptyBucketStorageMixin {
    @ModifyArg(method = "insert(Lnet/fabricmc/fabric/api/transfer/v1/fluid/FluidVariant;JLnet/fabricmc/fabric/api/transfer/v1/transaction/TransactionContext;)J", at = @At(value = "INVOKE",target = "Lnet/fabricmc/fabric/api/transfer/v1/context/ContainerItemContext;exchange(Lnet/fabricmc/fabric/api/transfer/v1/item/ItemVariant;JLnet/fabricmc/fabric/api/transfer/v1/transaction/TransactionContext;)J"), index = 0)
    private ItemVariant canon$rewriteMetaFluids(ItemVariant newVariant, @Local FluidVariant resource){
var n = newVariant.getNbt().copy();
if(!resource.copyOrCreateNbt().getCompound(Canon.META).equals(new NbtCompound())){
    n.put(Canon.META,resource.copyOrCreateNbt().getCompound(Canon.META));
}
        return ItemVariant.of(newVariant.getItem(),n);
    }
}
