package at.gkgo.canon.meta;

import com.mojang.serialization.Codec;

public interface MetaItem {
    default Meta<?> canon$meta(){
        return Meta.defaultMeta(this);
    }
}
