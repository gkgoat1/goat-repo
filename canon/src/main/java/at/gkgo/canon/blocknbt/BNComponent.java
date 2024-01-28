package at.gkgo.canon.blocknbt;

import at.gkgo.canon.Canon;
import at.gkgo.canon.meta.BlockInitPatch;
import at.gkgo.canon.meta.Meta;
import at.gkgo.canon.meta.MetaItem;
import at.gkgo.canon.util.CodecUtils;
import at.gkgo.canon.util.TypeUtils;
import com.mojang.serialization.Codec;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public <T> T fixup(Meta<T> m,T a,BlockPos p){
        if(owner instanceof WorldChunk wc){
            var b = m.behavior.patch(TypeUtils.unsafeCoerce(a),BlockInitPatch.KEY,new BlockInitPatch(wc.getWorld(),wc.getPos().getBlockPos(p.getX(),p.getY(),p.getZ()),wc.getBlockState(p)));
            a = TypeUtils.unsafeCoerce(b);
        }
        return a;
    }
    private void clean(NbtCompound x,BlockPos p){
        var m = ((MetaItem)owner.getBlockState(p).getBlock()).canon$meta();
        var mm = m.defaultNbt((a) -> TypeUtils.unsafeCoerce(fixup(TypeUtils.unsafeCoerce(m),a,p)));
        if(x.getCompound(Canon.META).equals(mm)){
            x.remove(Canon.META);
        }
    }
    private synchronized void clean(){
        for(var k: new HashSet<>(map.keySet())){
            clean(map.get(k),k);
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
    public void writeSyncPacket(PacketByteBuf buf, ServerPlayerEntity recipient) {
//        AutoSyncedComponent.super.writeSyncPacket(buf, recipient);
        for(var e: map.entrySet()){
            buf.writeBoolean(true);
            buf.writeBlockPos(e.getKey());
            buf.writeNbt(e.getValue());
        }
        buf.writeBoolean(false);
    }

    @Override
    public void applySyncPacket(PacketByteBuf buf) {
//        AutoSyncedComponent.super.applySyncPacket(buf);
        while(buf.readBoolean()){
            var p = buf.readBlockPos();
            map.put(p,buf.readNbt());
        }
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
