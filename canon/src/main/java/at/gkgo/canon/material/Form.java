package at.gkgo.canon.material;

import net.minecraft.util.Identifier;

import java.util.Set;

public interface Form<T> {
    Identifier getId();
    default MatTagKey<T> key(){
        return MatTagKey.of(getId());
    }
//    default Optional<RegisterResult<T>> registerOpt(Material m){
//        return Optional.ofNullable(register(m));
//    };
    T create(Material m);
    default RegisterResult<T> register(Material m){
        var t = m.getTag(key());
        if(t.isPresent()){
            return new RegisterResult<>(t.get(),false);
        }
        var c = create(m);
        if(c == null){
            return null;
        }
        return new RegisterResult<>(c,true);
    }
    default void bake(Material m){}
    default boolean acceptsMaterial(Material m){return true;}
    default Set<Object> components(Object value){
        return Set.of();
    }
}
