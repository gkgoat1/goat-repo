package at.gkgo.thing;

import at.gkgo.canon.block.ModBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;

public class ThingamajiggyBlockEntity extends ModBlockEntity<ThingamajiggyBlockEntity, ThingamajiggyBlock, ThingamajiggyItem> {
    public ThingamajiggyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private ItemStack craft(ItemStack in) {
        return in;
    }

    @Override
    public void tick(Random random) {
        super.tick(random);
        if (!io_buffers.get(Direction.DOWN).isEmpty()) {
            return;
        }
//        Thingamajiggy.LOGGER.info("tick");
        var in = io_buffers.get(Direction.UP);
        io_buffers.put(Direction.UP, ItemStack.EMPTY);
        io_buffers.put(Direction.DOWN, craft(in));
    }

    public final Map<Direction, ItemStack> io_buffers = Util.make(new HashMap<>(), (m) -> {
        for (var d : Direction.values()) {
            m.put(d, ItemStack.EMPTY);
        }
    });

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        for (var d : Direction.values()) {
            var e = nbt.get(d.getName());
            if (e != null) {
                io_buffers.put(d, ItemStack.fromNbt((NbtCompound) e));
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        for (var e : io_buffers.entrySet()) {
            nbt.put(e.getKey().getName(), e.getValue().writeNbt(new NbtCompound()));
        }
    }

    public final Map<Direction, SingleStackStorage> io = Util.make(new HashMap<>(), (m) -> {
        for (var d$ : Direction.values()) {
            var d = d$;
            m.put(d, new SingleStackStorage() {
                @Override
                protected ItemStack getStack() {
                    return io_buffers.get(d);
                }

                @Override
                protected void setStack(ItemStack stack) {
                    io_buffers.put(d, stack);
                }
            });
        }
    });
}
