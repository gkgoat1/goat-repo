package at.gkgo.canon.blocknbt;

import at.gkgo.canon.util.CodecUtils;
import com.mojang.serialization.Codec;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import java.util.HashMap;
import java.util.Objects;

public class BNComponent implements Component , AutoSyncedComponent {
    public static NbtCompound get(World w, BlockPos pos){
        var c = w.getChunk(pos);
        var ck = BNComponent.KEY.get(c);
        var m = pos.mutableCopy();
        m.setX(m.getX() & 15);
        m.setZ(m.getZ() & 15);
        return ck.map.getOrDefault(m.toImmutable(),new NbtCompound()).copy();
    }
    public static void insert(World w, BlockPos pos, NbtCompound x){
        var c = w.getChunk(pos);
        var ck = BNComponent.KEY.get(c);
        var m = pos.mutableCopy();
        m.setX(m.getX() & 15);
        m.setZ(m.getZ() & 15);
        ck.map.put(m.toImmutable(),x);
        ck.sync();
    }
    public HashMap<BlockPos, NbtCompound> map = new HashMap<>();
    public final Chunk owner;
    public static Codec<HashMap<BlockPos,NbtCompound>> CODEC = Codec.unboundedMap(CodecUtils.jsonKey(BlockPos.CODEC),NbtCompound.CODEC).xmap(HashMap::new,(b) -> b);
    public static final ComponentKey<BNComponent> KEY =
            ComponentRegistry.getOrCreate(Identifier.of("canon","block_nbt"), BNComponent.class);

    public BNComponent(Chunk owner) {
        this.owner = owner;
    }
    private void clean(NbtCompound x){
        if(x.getCompound("$").equals(new NbtCompound())){
            x.remove("$");
        }
    }
    private void clean(){
        for(var k: map.keySet()){
            clean(map.get(k));
            if(Objects.equals(new NbtCompound(),map.get(k))){
                map.remove(k);
            }
        }
    }
    public void sync() {
        clean();
        KEY.sync(owner);
        owner.setNeedsSaving(true);
    }
    @Override
    public void readFromNbt(NbtCompound tag) {
        map = CODEC.decode(NbtOps.INSTANCE,tag).result().get().getFirst();
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
CODEC.encode(map,NbtOps.INSTANCE,tag).result().get();
    }
}