package at.gkgo.canon.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Loc {
    public final RegistryKey<World> world;
    public final BlockPos pos;

    public Loc(RegistryKey<World> world, BlockPos pos) {
        this.world = world;
        this.pos = pos;
    }
    public static Codec<Loc> CODEC = RecordCodecBuilder.create((i) -> i.group(
    World.CODEC.fieldOf("world").forGetter((x) -> x.world),
            BlockPos.CODEC.fieldOf("pos").forGetter((x) -> x.pos)
    ).apply(i,Loc::new));
}
