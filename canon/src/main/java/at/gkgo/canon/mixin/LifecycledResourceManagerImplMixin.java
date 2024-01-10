package at.gkgo.canon.mixin;

//import at.gkgo.core.material.BakedResourcePack;
import at.gkgo.canon.bake.BakedResourcePack;
import net.minecraft.resource.LifecycledResourceManagerImpl;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.List;

@Mixin(LifecycledResourceManagerImpl.class)
public class LifecycledResourceManagerImplMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static List<ResourcePack> gt$addDynamicPack(List<ResourcePack> packs){
        packs = new ArrayList<>(packs);
        packs.add(new BakedResourcePack(ResourceType.SERVER_DATA));
        return packs;
    }
}
