package at.gkgo.canon.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ShulkerBoxScreenHandler;
import net.minecraft.util.math.BlockPos;

public class ModCrateBlockEntity <Self extends ModCrateBlockEntity<Self,B,I>, B extends ModBlockWithEntity<B,Self,I>, I extends ModBlockItem<B,I>> extends ModInventoryBlockEntity<Self,B,I>{
    public ModCrateBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state, 27);
    }


    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new ShulkerBoxScreenHandler(syncId,playerInventory,this);
    }

    @Override
    public boolean onSyncedBlockEvent(int type, int data) {
        if(type == 1){
            return true;
        }
        return super.onSyncedBlockEvent(type, data);
    }
}
