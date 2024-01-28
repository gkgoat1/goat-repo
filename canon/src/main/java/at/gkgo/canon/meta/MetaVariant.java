package at.gkgo.canon.meta;

import at.gkgo.canon.util.TypeUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;

import java.util.Optional;

public class MetaVariant<T> {
    public final T value;
    public final Object meta;

    public MetaVariant(T value, Object meta) {
        this.value = value;
        this.meta = meta;
    }
    public <K>Optional<K> get(CapKey<K>key){
        return ((MetaItem)value).canon$meta().behavior.getCap(TypeUtils.unsafeCoerce(meta),key);
    }
    public <K> MetaVariant<T> patch(PatchKey<K> key, K patch){
        var p = ((MetaItem)value).canon$meta().behavior.patch(TypeUtils.unsafeCoerce(meta),key,patch);
        return new MetaVariant<>(value,p);
    }
    public NbtElement getNbt(){
        return ((MetaItem)value).canon$meta().codec.encodeStart(NbtOps.INSTANCE,TypeUtils.unsafeCoerce(meta)).get().orThrow();
    }
    public MetaVariant<T> withNbt(NbtCompound x){
        var v = ((MetaItem)value).canon$meta().codec.decode(NbtOps.INSTANCE,x).get().orThrow().getFirst();
        return new MetaVariant<>(value,v);
    }
    public MetaVariant(T value){
        this(value, ((MetaItem)value).canon$meta().defaultvalue);
    }
    public static<I> Codec<MetaVariant<I>> codec(Codec<I> base){
        base = base.fieldOf("base").codec();
        Codec<I> finalBase = base;
        return new Codec<MetaVariant<I>>() {
            @Override
            public <T> DataResult<Pair<MetaVariant<I>, T>> decode(DynamicOps<T> ops, T input) {
                return finalBase.decode(ops,input).flatMap((j) -> {
                    var t = j.getSecond();
                    return ((MetaItem)j.getFirst()).canon$meta().codec.decode(ops, t).map((a) -> {
                        return new Pair<>(new MetaVariant<>(j.getFirst(),a.getFirst()),a.getSecond());
                    });
                });
            }

            @Override
            public <T> DataResult<T> encode(MetaVariant<I> input, DynamicOps<T> ops, T prefix) {
                return finalBase.encode(input.value, ops,prefix).flatMap((a) -> {
                    return ((MetaItem)input.value).canon$meta().codec.encode(TypeUtils.unsafeCoerce(input.meta),ops,a);
                });
            }
        };
    }
}
