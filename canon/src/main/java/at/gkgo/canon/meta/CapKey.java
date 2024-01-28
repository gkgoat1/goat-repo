package at.gkgo.canon.meta;

import net.minecraft.util.Identifier;

import java.util.HashMap;

public class CapKey <T>{
    public final Identifier id;

    private CapKey(Identifier id) {
        this.id = id;
    }
    private static HashMap<Identifier,CapKey<?>> ALL = new HashMap<>();
    public static <T> CapKey<T> of(Identifier x){
        var a = ALL.getOrDefault(x,new CapKey<>(x));
        ALL.put(x,a);
        return (CapKey<T>) a;
    }
}
