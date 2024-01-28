package at.gkgo.canon.meta;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
//import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class PairMetaBehavior<T,U> implements MetaBehavior<Pair<T,U>> {
    public final MetaBehavior<T> first;
    public final MetaBehavior<U> second;

    @Override
    public <K> Optional<K> getCap(Pair<T, U> value, CapKey<K> key) {
        var a = first.getCap(value.getFirst(), key);
        if(a.isPresent()){
            return a;
        }
        var b = second.getCap(value.getSecond(), key);
        if(b.isPresent()){
            return b;
        }
        return MetaBehavior.super.getCap(value, key);
    }

    public PairMetaBehavior(MetaBehavior<T> forMaterial, MetaBehavior<U> forForm) {
        this.first = forMaterial;
        this.second = forForm;
    }

//    @Override
//    public Pair<T, U> summonOnBlock(Pair<T, U> value, World world, BlockPos pos, BlockState state) {
//        return new Pair<>(
//                first.summonOnBlock(value.getFirst(), world,pos,state),
//                second.summonOnBlock(value.getSecond(), world,pos,state)
//        );
//    }

    @Override
    public <K> Pair<T, U> patch(Pair<T, U> orig, PatchKey<K> key, K patch) {
        return new Pair<>(
                first.patch(orig.getFirst(),key,patch),
                second.patch(orig.getSecond(), key,patch)
        );
    }
}
