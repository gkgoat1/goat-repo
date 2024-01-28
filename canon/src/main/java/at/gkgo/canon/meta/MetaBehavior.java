package at.gkgo.canon.meta;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Supplier;

public interface MetaBehavior <T>{
//    default T summonOnBlock(T value, World world, BlockPos pos, BlockState state){
//        return patch(value,);
//    }
    default<K> Optional<K> getCap(T value, CapKey<K> key){
        return Optional.empty();
    }
    default<K> T patch(T orig, PatchKey<K> key, K patch){
        return orig;
    }
    static <X> MetaBehavior<MetaVariant<X>> variant(){
        return new MetaBehavior<MetaVariant<X>>() {
            @Override
            public <K> Optional<K> getCap(MetaVariant<X> value, CapKey<K> key) {
                return value.get(key);
            }

            @Override
            public <K> MetaVariant<X> patch(MetaVariant<X> orig, PatchKey<K> key, K patch) {
                return orig.patch(key,patch);
            }
        };
    }
}
