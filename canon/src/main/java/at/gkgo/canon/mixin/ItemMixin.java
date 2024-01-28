package at.gkgo.canon.mixin;

import at.gkgo.canon.meta.MetaItem;
import com.mojang.serialization.Codec;
import net.minecraft.item.Item;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin implements MetaItem {

}
