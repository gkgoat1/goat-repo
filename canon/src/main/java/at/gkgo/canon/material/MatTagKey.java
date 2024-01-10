package at.gkgo.canon.material;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class MatTagKey <T>{
    public final Identifier id;

    private MatTagKey(Identifier id) {
        this.id = id;
    }
    private static Map<Identifier,MatTagKey<?>> ALL = new HashMap<>();
    public static <T> MatTagKey<T> of(Identifier x){
        if(ALL.containsKey(x))return (MatTagKey<T>) ALL.get(x);
        ALL.put(x,new MatTagKey<>(x));
        return (MatTagKey<T>) ALL.get(x);
    }
}
