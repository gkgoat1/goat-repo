package at.gkgo.canon.rel;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RelKey {
    public Identifier id;
    private RelKey(Identifier a){
        id = a;
    }
    private static final Map<Identifier,RelKey> all = new HashMap<>();
    public static RelKey of(Identifier i){
        return all.putIfAbsent(i,new RelKey(i));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelKey relKey = (RelKey) o;
        return Objects.equals(id, relKey.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
