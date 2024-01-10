package at.gkgo.canon.meta.item;

import com.mojang.serialization.Codec;

public interface MetaItem {
    Codec<?> canon$meta();
}
