package at.gkgo.canon.block;

//import at.gkgo.tech.GoatTech;
//import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModBlockItem<B extends ModBlock<B,Self>,Self extends ModBlockItem<B,Self>> extends BlockItem {
    public ModBlockItem(B block, Settings settings) {
        super(block, settings);
    }
    public B getBlock(){
        return (B)super.getBlock();
    }
    public Item vanilla(ItemStack stack){
        return Items.IRON_BLOCK;
    }
//    @Override
//    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
//        if(!GoatTech.doPolymer){
//            return this;
//        }
//        return vanilla(itemStack);
//    }
}
