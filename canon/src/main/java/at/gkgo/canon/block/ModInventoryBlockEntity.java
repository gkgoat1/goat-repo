package at.gkgo.canon.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ModInventoryBlockEntity <Self extends ModInventoryBlockEntity<Self,B,I>, B extends ModBlockWithEntity<B,Self,I>, I extends ModBlockItem<B,I>> extends ModBlockEntity<Self,B,I> implements ImplementedInventory{
    private final DefaultedList<ItemStack> items;
    public ModInventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int size) {
        super(type, pos, state);
        this.items = DefaultedList.ofSize(size,ItemStack.EMPTY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt,items);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt,items);
    }

}
