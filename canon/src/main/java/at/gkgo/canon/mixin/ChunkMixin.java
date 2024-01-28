package at.gkgo.canon.mixin;

import at.gkgo.canon.Canon;
import at.gkgo.canon.blocknbt.BNComponent;
import at.gkgo.canon.meta.MetaItem;
import at.gkgo.canon.util.TypeUtils;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Chunk.class)
public class ChunkMixin {
    @Unique
    public void canon$resetNbtCore(BlockPos pos, BlockState bs){
        var m = pos.mutableCopy();
        m.setX(m.getX() & 15);
        m.setZ(m.getZ() & 15);
        var c = BNComponent.KEY.get(this);
        var mm = new NbtCompound();
        var mx = ((MetaItem)bs.getBlock()).canon$meta();
        mm.put(Canon.META,mx.defaultNbt((a) -> TypeUtils.unsafeCoerce(c.fixup(TypeUtils.unsafeCoerce(mx),a,m.toImmutable()))));
        synchronized (c) {
            c.map.put(m.toImmutable(),mm);
        }
        c.sync();
    }
}
