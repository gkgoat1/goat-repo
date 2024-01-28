package at.gkgo.wurm;

import at.gkgo.canon.block.ModDimensionAttachedBlockEntity;
import at.gkgo.canon.block.propag.Propagation;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import xyz.nucleoid.fantasy.RuntimeWorldConfig;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class WurmholeBlockEntity extends ModDimensionAttachedBlockEntity<WurmholeBlockEntity,WurmholeBlock,WurmholeItem> {
    public WurmholeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, (s) -> new RuntimeWorldConfig().setFlat(true).setGenerator(new FlatChunkGenerator(FlatChunkGeneratorConfig.getDefaultConfig(
                s.getRegistryManager().createRegistryLookup().getOrThrow(RegistryKeys.BIOME),
                s.getRegistryManager().createRegistryLookup().getOrThrow(RegistryKeys.STRUCTURE_SET),
                s.getRegistryManager().createRegistryLookup().getOrThrow(RegistryKeys.PLACED_FEATURE)
                ))));
    }

    @Override
    public <T> Storage<T> getStorage(BlockApiLookup<Storage<T>, Direction> x, Direction y, Class<T> k) {
        var tx = x.find(getTarget().getWorld(),getTarget().getPos().offset(y.getOpposite(),1),y);
        return tx;
    }

    @Override
    public <T> T getPropag(Propagation<T> p, Direction y) {
        return p.lookup().find(getTarget().getWorld(),getTarget().getPos().offset(y.getOpposite(),1),y);
    }

    public WurmholeBlockEntity getTarget(){
        if(other.isPresent()){
            return (WurmholeBlockEntity) ModDimensionAttachedBlockEntity.get(getWorld().getServer(),other.get());
        }
        var c = getAttachedWorld();
        var p = BlockPos.ORIGIN.up(4);
        WurmholeBlockEntity e;
        if(c.getBlockState(p) != getCachedState()) {
            c.setBlockState(p, getCachedState());
            e = ((WurmholeBlockEntity) Objects.requireNonNull(c.getBlockEntity(p)));
            e.other = Optional.of(id);
        }else{
            e = ((WurmholeBlockEntity) Objects.requireNonNull(c.getBlockEntity(p)));
        }
        return e;
    }
    public World getOtherWorld(){
        return getTarget().getWorld();
    }
    public Optional<UUID> other = Optional.empty();

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        other = Optional.empty();
        if(nbt.containsUuid("other")){
            other = Optional.of(nbt.getUuid("other"));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        other.ifPresent(uuid -> nbt.putUuid("other", uuid));
    }
}
