package at.gkgo.canon.minecraft;

import at.gkgo.canon.material.Handler;
import at.gkgo.canon.material.MatTagKey;
import at.gkgo.canon.material.Material;
import at.gkgo.canon.material.MaterialRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

import java.util.Map;
import java.util.Optional;

public class MinecraftMaterials {
    public static Material metalMat(Identifier id){
        return MaterialRegistries.registerMaterial(new Material(id, 3, new Handler() {
            @Override
            public <T> Optional<T> getTag(MatTagKey<T> x) {
                if(x == MinecraftMatTags.METALLIC){
                    return Optional.of((T) Unit.INSTANCE);
                }
                if(x == MinecraftForms.INGOT.key()){
                    return Optional.of((T) Registries.ITEM.get(Identifier.of(id.getNamespace(),id.getPath() + "_ingot")));
                }
                if(x == MinecraftForms.BLOCK.key()){
                    return Optional.of((T) Registries.ITEM.get(Identifier.of(id.getNamespace(),id.getPath() + "_block")));
                }
                if(x == MinecraftForms.RAW.key()){
                    return Optional.of((T) Registries.ITEM.get(Identifier.of(id.getNamespace(),"raw_" + id.getPath())));
                }
                if(x == MinecraftForms.RAW_BLOCK.key()){
                    return Optional.of((T) Registries.ITEM.get(Identifier.of(id.getNamespace(),"raw_" + id.getPath() + "_block")));
                }
                return Optional.empty();
            }
        }));
    }
    public static Material IRON = metalMat(Identifier.of("minecraft","iron"));
    public static Material GOLD = metalMat(Identifier.of("minecraft","gold"));
    public static Material COPPER = metalMat(Identifier.of("minecraft","copper"));
    public static void init(){

    }
}
