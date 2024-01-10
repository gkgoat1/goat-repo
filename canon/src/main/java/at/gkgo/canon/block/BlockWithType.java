package at.gkgo.canon.block;

import net.minecraft.block.entity.BlockEntityType;

public interface BlockWithType {
    public BlockEntityType<?> getType();
}
