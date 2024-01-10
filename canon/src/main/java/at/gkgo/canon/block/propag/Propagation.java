package at.gkgo.canon.block.propag;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.util.math.Direction;

import java.util.List;

public interface Propagation <T>{
    T combine(List<T> all);
    BlockApiLookup<T, Direction> lookup();
    Class<T> klass();
}
