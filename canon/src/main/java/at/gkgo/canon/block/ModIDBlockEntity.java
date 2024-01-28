package at.gkgo.canon.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public class ModIDBlockEntity <Self extends ModIDBlockEntity<Self,B,I>, B extends ModBlockWithEntity<B,Self,I>, I extends ModBlockItem<B,I>> extends ModBlockEntity<Self,B,I>{
    public ModIDBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public UUID id = UUID.randomUUID();
    public static ModIDBlockEntity<?,?,?> get(MinecraftServer srv, UUID id){
        var l = IDComponent.KEY.get(srv.getSaveProperties().getMainWorldProperties()).all.get(id);
        return (ModIDBlockEntity<?, ?, ?>) srv.getWorld(l.world).getBlockEntity(l.pos);
    }
private void update(){
        if(!hasWorld())return;
        if(getWorld().isClient)return;
    IDComponent.KEY.get(world.getServer().getSaveProperties().getMainWorldProperties()).all.put(id,new Loc(world.getRegistryKey(),getPos()));
}

    @Override
    public void setWorld(World world) {
        super.setWorld(world);
        update();
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putUuid("id",id);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        if(nbt.containsUuid("id")) {
            if(hasWorld()) {
                IDComponent.KEY.get(world.getServer()).all.remove(id);
            }
            id = nbt.getUuid("id");
            if(hasWorld()) {
                update();
            }
        }
    }
}
