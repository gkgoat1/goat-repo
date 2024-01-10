package at.gkgo.canon.block.propag;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.util.math.Direction;

import java.util.List;
import java.util.function.Function;

public class PropagationImpl <T> implements Propagation<T>{
    private final Function<List<T>,T> combiner;
    private final BlockApiLookup<T,Direction> lookup;
    private final Class<T> klass;

    public PropagationImpl(Function<List<T>, T> combiner, BlockApiLookup<T, Direction> lookup, Class<T> klass) {
        this.combiner = combiner;
        this.lookup = lookup;
        this.klass = klass;
        Propagations.created.invoker().accept(this);
    }

    @Override
    public T combine(List<T> all) {
        return combiner.apply(all);
    }

    @Override
    public BlockApiLookup<T, Direction> lookup() {
        return lookup;
    }

    @Override
    public Class<T> klass() {
        return klass;
    }
}
