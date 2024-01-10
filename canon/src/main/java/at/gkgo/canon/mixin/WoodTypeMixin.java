package at.gkgo.canon.mixin;

//import at.gkgo.core.Goatcore;
import at.gkgo.canon.material.Handler;
import at.gkgo.canon.material.MatTagKey;
import at.gkgo.canon.material.Material;
import at.gkgo.canon.material.MaterialRegistries;
import at.gkgo.canon.minecraft.MinecraftMatTags;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

//import static at.gkgo.core.WoodMaterials.WOOD_TAG;

@Mixin(WoodType.class)
public class WoodTypeMixin {
    @Inject(method = "register", at = @At("RETURN"))
    private static void gt$addMirrorMaterial(WoodType w, CallbackInfoReturnable<WoodType> cir) {
        var mt = new Material(Identifier.of("minecraft", w.name()), 1, new Handler() {
            @Override
            public <T> Optional<T> getTag(MatTagKey<T> x) {
                if(x == MinecraftMatTags.WOOD){
                    return Optional.of((T)w);
                }
                return Optional.empty();
            }
        });
        MaterialRegistries.registerMaterial(mt);
    }
}
