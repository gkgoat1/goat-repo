package at.gkgo.canon.block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModToken <B extends ModBlock<B,I>, I extends ModBlockItem<B,I>> implements ItemConvertible {
    private B block;
    private I item;

    public I getItem() {
        return item;
    }

    public B getBlock(){
        return block;
    }

    public static <B extends ModBlock<B,I>, I extends ModBlockItem<B,I>> ModToken<B,I> create(Identifier name, Supplier<B> block, Function<B,I> item){
        var a = new ModToken<B,I>();
        a.block = Registry.register(Registries.BLOCK,name,block.get());
        a.item = Registry.register(Registries.ITEM,name,item.apply(a.block));
        return a;
    }

    @Override
    public Item asItem() {
        return item;
    }
}
