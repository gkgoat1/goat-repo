package at.gkgo.canon.mixin;

import at.gkgo.canon.blocknbt.BNComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Chunk.class)
public class ChunkMixin {
    @Unique
    public void canon$resetNbtCore(BlockPos pos){
        var m = pos.mutableCopy();
        m.setX(m.getX() & 15);
        m.setZ(m.getZ() & 15);
        var c = BNComponent.KEY.get(this);
        synchronized (c) {
            c.map.put(m.toImmutable(), new NbtCompound());
        }
        c.sync();
    }
}
