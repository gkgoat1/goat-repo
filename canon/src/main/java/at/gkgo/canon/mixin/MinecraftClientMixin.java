package at.gkgo.canon.mixin;

//import at.gkgo.core.material.BakedResourcePack;
import at.gkgo.canon.bake.BakedResourcePack;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @ModifyExpressionValue(
            method = {"reloadResources(ZLnet/minecraft/client/MinecraftClient$LoadingContext;)Ljava/util/concurrent/CompletableFuture;", "<init>"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;createResourcePacks()Ljava/util/List;")
    )
    private List<ResourcePack> gtceu$loadPacks(List<ResourcePack> packs) {
        packs = new ArrayList<>(packs);
        packs.add(new BakedResourcePack(ResourceType.CLIENT_RESOURCES));
        return packs;
    }
}
