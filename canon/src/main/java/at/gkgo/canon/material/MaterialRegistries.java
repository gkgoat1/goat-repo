package at.gkgo.canon.material;

//import at.gkgo.core.Goatcore;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.*;
import java.util.function.Consumer;

public class MaterialRegistries {
    private static final Map<Identifier,Material> MATERIALS = new HashMap<>();
//    public static Map<Identifier, BakedEntry> BAKED = new HashMap<>();
//    public static Baker DEFAULT_BAKER = (i,j,k) -> BAKED.put(i,new BakedEntry(j,k));
    public static Codec<Material> MATERIAL_CODEC = Identifier.CODEC.comapFlatMap((i) -> {
        var m = material(i);
        return m.<DataResult<? extends Material>>map(DataResult::success).orElseGet(() -> DataResult.error(() -> "invalid material"));
    },(m) -> m.id);
    public static Codec<Form<?>> FORM_CODEC = Identifier.CODEC.comapFlatMap((i) -> {
        var m = form(i);
        if(m.isPresent()){
            return DataResult.success(m.get());
        }
        return DataResult.error(() -> "invalid form");
    }, Form::getId);
    private static final Map<Identifier,Form<?>> FORMS = new HashMap<>();
    private static final Multimap<Identifier, Consumer<Form<?>>> POST_FORM_REGISTER = Multimaps.newSetMultimap(new HashMap<>(), HashSet::new);
    public static void post_form_register(Identifier i, Consumer<Form<?>> go){
        var f = form(i);
        if(f.isPresent()){
            go.accept(f.get());
            return;
        }
        POST_FORM_REGISTER.put(i,go);
    }
    private static final Map<Object, Pair<Material,Form<?>>> INVERT = new HashMap<>();
    public static Optional<Pair<Material,Form<?>>> invert(Object id){
        return Optional.ofNullable(INVERT.get(id));
    }
    public static Optional<Material> as(Object id, Form<?> f) {return invert(id).flatMap((a) -> {
        if(a.getRight() == f){
            return Optional.of(a.getLeft());
        };
        return Optional.empty();
    });}
    public static Codec<Object> MATERIAL_FORM_OBJ = by_material_form(Object.class);
    public static<T> Codec<T> by_material_form(Class<T> k) {
        return RecordCodecBuilder.<Pair<Material, Form<?>>>create((i) -> i.group(
                MATERIAL_CODEC.fieldOf("material").forGetter(Pair::getLeft),
                FORM_CODEC.fieldOf("form").forGetter(Pair::getRight)
        ).apply(i, Pair::new)).flatXmap((a) -> {
            var x = a.getLeft().all.get(a.getRight().getId());
            if (x == null) {
                return DataResult.error(() -> "form or material not registered");
            }
            if(!k.isInstance(x.value)){
                return DataResult.error(() -> "registered value not the right type");
            }
            return DataResult.success((T)x.value);
        }, (b) -> {
            var m = invert(b);
            return m.<DataResult<? extends Pair<Material, Form<?>>>>map(DataResult::success).orElseGet(() -> DataResult.error(() -> "form or material not registered"));
        });
    }
    private static boolean didTriggerPreMaterialEntrypoint = false;
    private static void triggerPreMaterialEntrypoint(){
        if(didTriggerPreMaterialEntrypoint)return;
        didTriggerPreMaterialEntrypoint = true;
        var entries = FabricLoader.getInstance().getEntrypoints("canon:material/before_first", BeforeFirstMaterialEntrypoint.class);
        for(var e: entries){
            e.runBeforeMaterials();
        }
    }
    public static Codec<Item> MATERIAL_ITEM  = by_material_form(ItemConvertible.class).xmap(ItemConvertible::asItem,(a) -> a);
    public static Codec<Block> MATERIAL_BLOCK = by_material_form(Block.class);
    public static Material registerMaterial(Material m){
//        Goatcore.LOGGER.info(m.id.toString());
        for(var f: FORMS.values()){
            triggerPreMaterialEntrypoint();
            if(m.hasTagsIn(f)) {
                var r = f.register(m);
                if(r != null) {
                    m.all.put(f.getId(), r);
                    INVERT.put(r.value, new Pair<>(m, f));
                    for(var c: f.components((Object) r.value)){
                        INVERT.put(c, new Pair<>(m, f));
                    }
                    f.bake(m);
                }
            }
        }
        MATERIALS.put(m.id,m);
        return m;
    }
    public static Optional<Material> material(Identifier id){
        return Optional.ofNullable(MATERIALS.get(id));
    }
    public static Collection<Identifier> materials(){
        return MATERIALS.keySet();
    }
    public static <U extends Form<?>> U registerForm(U f){
        for(var m: MATERIALS.values()){
            triggerPreMaterialEntrypoint();
            if(m.hasTagsIn(f)) {
                var r = f.register(m);
                if(r != null) {
                    m.all.put(f.getId(), r);
                    INVERT.put(r.value, new Pair<>(m, f));
                    for(var c: f.components((Object) r.value)){
                        INVERT.put(c, new Pair<>(m, f));
                    }
                    f.bake(m);
                }
            }
        }
        FORMS.put(f.getId(),f);
        for(var a: POST_FORM_REGISTER.get(f.getId())){
            a.accept(f);
        }
        POST_FORM_REGISTER.removeAll(f.getId());
        return f;
    }
    public static Optional<Form<?>> form(Identifier id){
        return Optional.ofNullable(FORMS.get(id));
    }
    public static Collection<Identifier> forms(){
        return FORMS.keySet();
    }
}
