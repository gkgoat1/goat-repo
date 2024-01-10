package at.gkgo.canon.block;

import at.gkgo.canon.block.propag.Propagation;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ContainerLock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class ModBlockEntity<Self extends ModBlockEntity<Self,B,I>, B extends ModBlockWithEntity<B,Self,I>, I extends ModBlockItem<B,I>> extends BlockEntity implements ScreenHandlerFactory {
    public ModBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public B getBlock(){
        return (B)getCachedState().getBlock();
    }
    public ContainerLock lock;
    public boolean checkUnlocked(PlayerEntity player) {
        return checkUnlocked(player, this.lock, this.getBlock().getName());
    }

    public static boolean checkUnlocked(PlayerEntity player, ContainerLock lock, Text containerName) {
        if (!player.isSpectator() && !lock.canOpen(player.getMainHandStack())) {
            player.sendMessage(Text.translatable("container.isLocked", containerName), true);
            player.playSound(SoundEvents.BLOCK_CHEST_LOCKED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return false;
        } else {
            return true;
        }
    }

    @Nullable
    public ScreenHandler createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return this.checkUnlocked(playerEntity) ? this.createScreenHandler(i, playerInventory) : null;
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory){
        return null;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        lock = ContainerLock.fromNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if(lock != null) {
            lock.writeNbt(nbt);
        }
    }

    public <T> Storage<T> getStorage(BlockApiLookup<Storage<T>, Direction> x, Direction y, Class<T> k){
        return null;
    }

    public <T> T getPropag(Propagation<T> p, Direction y){
        return null;
    }

    static <T>void initStorage(BlockApiLookup<Storage<T>, Direction> x,Class<T> k){
        x.registerFallback((w,p,s,be,d) -> {
            if(be instanceof ModBlockEntity<?,?,?> b){
                return b.getStorage(x,d,k);
            }
            return null;
        });
    }

    public static <T> void initPropag(Propagation<T> p){
        p.lookup().registerFallback((w,po,s,be,d) -> {
            if(be instanceof ModBlockEntity<?,?,?> b){
                return b.getPropag(p,d);
            }
            return null;
        });
    }

    void tick(Random random){

    }

    static{
        initStorage(ItemStorage.SIDED, ItemVariant.class);
        initStorage(FluidStorage.SIDED, FluidVariant.class);
//        initStorage(BlockEntityStack.lookup, BlockEntityStack.class);
    }
    public static void init(){

    }
}
