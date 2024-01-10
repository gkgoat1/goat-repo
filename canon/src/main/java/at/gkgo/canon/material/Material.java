package at.gkgo.canon.material;

import at.gkgo.canon.util.Mangler;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Material {
    public final Map<Identifier,RegisterResult<?>> all = new HashMap<>();
    public final Identifier id;
    public final int rarity;
    public final Handler handler;
    public static final Handler NULL_HANDLER = new Handler() {

        @Override
        public <T> Optional<T> getTag(MatTagKey<T> x) {
            return Optional.empty();
        }
    };
    public Identifier mangle(Identifier x){
        return Identifier.of(id.getNamespace(), Mangler.mangle(id.getPath()) +"/"+x.getNamespace()+"/"+x.getPath());
    }
    public boolean hasTagsIn(Form<?> x){
        return x.acceptsMaterial(this);
    }
    public Material(Identifier id, int rarity, Handler handler) {
        this.id = id;
        this.rarity = rarity;
        this.handler = handler;
    }
    public <T> Optional<T> getTag(MatTagKey<T> x){
        return MaterialEvents.TAG.invoker().get(this, x, new TagGetter() {
            @Override
            public <T> Optional<T> getTag(MatTagKey<T> tMatTagKey) {
                return handler.getTag(tMatTagKey);
            }
        });
    }
}
