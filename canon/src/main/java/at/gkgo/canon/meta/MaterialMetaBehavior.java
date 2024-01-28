package at.gkgo.canon.meta;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class MaterialMetaBehavior<T,U> implements MetaBehavior<MatMeta<T,U>> {
    public final MetaBehavior<T> forMaterial;
    public final MetaBehavior<U> forForm;

    public MaterialMetaBehavior(MetaBehavior<T> forMaterial, MetaBehavior<U> forForm) {
        this.forMaterial = forMaterial;
        this.forForm = forForm;
    }

    @Override
    public <K> Optional<K> getCap(MatMeta<T, U> value, CapKey<K> key) {
        var a = forMaterial.getCap(value.forMaterial, key);
        if(a.isPresent()){
            return a;
        }
        var b = forForm.getCap(value.forForm, key);
        if(b.isPresent()){
            return b;
        }
        return MetaBehavior.super.getCap(value, key);
    }

//    @Override
//    public MatMeta<T, U> summonOnBlock(MatMeta<T, U> value, World world, BlockPos pos, BlockState state) {
//        return new MatMeta<>(
//                forMaterial.summonOnBlock(value.forMaterial, world,pos,state),
//                forForm.summonOnBlock(value.forForm, world,pos,state)
//        );
//    }

    @Override
    public <K> MatMeta<T, U> patch(MatMeta<T, U> orig, PatchKey<K> key, K patch) {
        return new MatMeta<>(
                forMaterial.patch(orig.forMaterial, key,patch),
                forForm.patch(orig.forForm,key,patch)
        );
    }
}
