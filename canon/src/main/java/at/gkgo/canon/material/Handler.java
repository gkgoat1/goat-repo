package at.gkgo.canon.material;

import at.gkgo.canon.meta.Meta;
import net.minecraft.nbt.NbtCompound;

import java.util.Optional;

public interface Handler {
    <T>Optional<T> getTag(MatTagKey<T> x);
    default Meta<?> meta(Material m){
        return Meta.defaultMeta(m);
    }
}
