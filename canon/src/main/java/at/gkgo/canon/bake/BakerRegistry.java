package at.gkgo.canon.bake;

//import at.gkgo.core.Goatcore;
import net.minecraft.util.Identifier;

import java.util.*;

public class BakerRegistry {
    public static Map<Identifier, BakedEntry> BAKED = new HashMap<>();
    public static Baker DEFAULT_BAKER = (i,j,k) -> BAKED.put(i,new BakedEntry(j,k));

}
