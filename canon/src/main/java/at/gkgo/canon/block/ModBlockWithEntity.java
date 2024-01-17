package at.gkgo.canon.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ModBlockWithEntity<Self extends ModBlockWithEntity<Self,E,I>,E extends ModBlockEntity<E,Self,I>,I extends ModBlockItem<Self,I>> extends ModBlock<Self,I> implements BlockEntityProvider, BlockWithType {
    public final Supplier<BlockEntityType<E>> type;
    public ModBlockWithEntity(Settings settings, Supplier<BlockEntityType<E>> type) {
        super(settings);
        this.type = type;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return type.get().instantiate(pos,state);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
            var be = (E)world.getBlockEntity(pos);
            be.tick(random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);
        var be = (E)world.getBlockEntity(pos);
        be.tick(random);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return (w,p,s,t) -> {
          if(t instanceof ModBlockEntity<?,?,?> e){
              e.tick(w.getRandom());
          }
        };
    }

    @Override
    public BlockEntityType<?> getType() {
        return type.get();
    }


}
