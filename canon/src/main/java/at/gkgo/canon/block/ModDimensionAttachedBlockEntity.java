package at.gkgo.canon.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import xyz.nucleoid.fantasy.Fantasy;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModDimensionAttachedBlockEntity <Self extends ModDimensionAttachedBlockEntity<Self,B,I>, B extends ModBlockWithEntity<B,Self,I>, I extends ModBlockItem<B,I>> extends ModIDBlockEntity<Self,B,I>{
    public final Function<MinecraftServer,RuntimeWorldConfig> cfg;
    public ModDimensionAttachedBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, Function<MinecraftServer,RuntimeWorldConfig> cfg) {
        super(type, pos, state);
        this.cfg = cfg;
    }
    public ServerWorld getAttachedWorld(){
        if(!hasWorld()){
            return null;
        }
        var s = Objects.requireNonNull(getWorld()).getServer();
        if(s == null){
            return null;
        }
        return Fantasy.get(s).getOrOpenPersistentWorld(Identifier.of("canon","z" + id.getMostSignificantBits() + "y" + id.getLeastSignificantBits()),cfg.apply(s)).asWorld();
    }
}
