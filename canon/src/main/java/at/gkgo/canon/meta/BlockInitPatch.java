package at.gkgo.canon.meta;

import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockInitPatch {
    public final World world;
    public final BlockPos pos;
    public final BlockState state;

    public BlockInitPatch(World world, BlockPos pos, BlockState state) {
        this.world = world;
        this.pos = pos;
        this.state = state;
    }
    public static PatchKey<BlockInitPatch> KEY = PatchKey.of(Identifier.of("canon","init/block"));
}
