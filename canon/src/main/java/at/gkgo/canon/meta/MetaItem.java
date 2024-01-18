package at.gkgo.canon.meta;

import com.mojang.serialization.Codec;

public interface MetaItem {
    Codec<?> canon$meta();
}
