package at.gkgo.wurm;

import at.gkgo.canon.block.ModBlockItem;
import net.minecraft.item.Item;

public class WurmholeItem extends ModBlockItem<WurmholeBlock,WurmholeItem> {
    public WurmholeItem(WurmholeBlock block) {
        super(block, new Item.Settings());
    }
}
