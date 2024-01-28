package at.gkgo.canon.meta;

import at.gkgo.canon.material.MaterialRegistries;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Unit;

import java.util.Optional;
import java.util.WeakHashMap;
import java.util.function.Function;

public class Meta <T>{
    private static boolean didTriggerPreMetaEntrypoint = false;
    private static void triggerPreMetaEntrypoint(){
        if(didTriggerPreMetaEntrypoint)return;
        didTriggerPreMetaEntrypoint = true;
        var entries = FabricLoader.getInstance().getEntrypoints("canon:meta/before_first", BeforeFirstMetaEntrypoint.class);
        for(var e: entries){
            e.runBeforeMetas();
        }
    }
    public final Codec<T> codec;
    public final T defaultvalue;
    public final MetaBehavior<T> behavior;
    public final Object attached;
    public MetaItem getAttachedMetaItem(){
        return (MetaItem) attached;
    }
    public NbtCompound defaultNbt(Function<T,T> go){
        return (NbtCompound) codec.encodeStart(NbtOps.INSTANCE,go.apply(defaultvalue)).result().get();
    }
    public <K> Optional<K> get(NbtCompound x, CapKey<K> k){
        var t = codec.decode(NbtOps.INSTANCE,x).result().get().getFirst();
        return behavior.getCap(t,k);
    }

    public Meta(Codec<T> codec, T defaultvalue, MetaBehavior<T> behavior, Object attached) {
        this.codec = codec;
        this.defaultvalue = defaultvalue;
        this.behavior = behavior;
        this.attached = attached;
    }
    public static <X> Meta<MetaVariant<X>> variant(Codec<X>codec,X defaultValue,Object attached){
        return new Meta<>(MetaVariant.codec(codec),new MetaVariant<>(defaultValue),MetaBehavior.variant(),attached);
    }
    public <U> Meta<Pair<T,U>> with(Meta<U> x){
        return new Meta<>(RecordCodecBuilder.create((i) -> i.group(
                codec.fieldOf("first").forGetter(Pair::getFirst),
                x.codec.fieldOf("second").forGetter(Pair::getSecond)
        ).apply(i,Pair::new)),Pair.of(defaultvalue,x.defaultvalue),new PairMetaBehavior<>(behavior,x.behavior), attached);
    }
    public static <T,U> Meta<MatMeta<T,U>> mat(Meta<T> ma, Meta<U> b,Object attached){
        return new Meta<MatMeta<T,U>>(RecordCodecBuilder.create((i) -> i.group(
                ma.codec.fieldOf("for_material").forGetter((x) -> x.forMaterial),
                b.codec.fieldOf("for_form").forGetter((x) -> x.forForm)
        ).apply(i,MatMeta::new)),new MatMeta<T,U>(ma.defaultvalue,b.defaultvalue), new MaterialMetaBehavior<>(ma.behavior,b.behavior), attached);
    }
    public static Meta<Unit> unit(Object attached){return new Meta<Unit>(Codec.unit(Unit.INSTANCE), Unit.INSTANCE, new MetaBehavior<Unit>() {
    }, attached);}
    public static Meta<?> defaultMetaBase(Object a){
        var matForm = MaterialRegistries.invert(a);
        if(matForm.isPresent()){
            var ma = matForm.get().getLeft().canon$meta();
            var b = matForm.get().getRight().canon$meta();
            return mat(ma,b,a);
        }
        return unit(a);
    }
    private static final WeakHashMap<Object,Meta<?>> ALL = new WeakHashMap<>();
    public static Meta<?> defaultMeta(Object a){
        triggerPreMetaEntrypoint();
        if(ALL.containsKey(a)){
            return ALL.get(a);
        }
        var v = MetaRewrite.EVENT.invoker().rewrite(defaultMetaBase(a));
        ALL.put(a,v);
        return v;
    }
}
