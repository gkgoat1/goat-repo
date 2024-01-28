package at.gkgo.canon.meta;

import net.minecraft.util.Identifier;

import java.util.HashMap;

public class PatchKey<T>{
    public final Identifier id;

    private PatchKey(Identifier id) {
        this.id = id;
    }
    private static HashMap<Identifier, PatchKey<?>> ALL = new HashMap<>();
    public static <T> PatchKey<T> of(Identifier x){
        var a = ALL.getOrDefault(x,new PatchKey<>(x));
        ALL.put(x,a);
        return (PatchKey<T>) a;
    }
}
