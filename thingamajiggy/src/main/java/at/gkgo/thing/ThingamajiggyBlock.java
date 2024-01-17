package at.gkgo.thing;

import at.gkgo.canon.block.ModBlockWithEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ThingamajiggyBlock extends ModBlockWithEntity<ThingamajiggyBlock,ThingamajiggyBlockEntity,ThingamajiggyItem> {
    public ThingamajiggyBlock(Supplier<BlockEntityType<ThingamajiggyBlockEntity>> type) {
        super(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK), type);
    }
}
