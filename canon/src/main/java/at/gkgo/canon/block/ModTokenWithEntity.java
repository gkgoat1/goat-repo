package at.gkgo.canon.block;

//import at.gkgo.tech.GoatTech;
//import at.gkgo.tech.foundation.dyn.DynReg;

import com.mojang.datafixers.util.Function3;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;

import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModTokenWithEntity <B extends ModBlockWithEntity<B,E,I>,E extends ModBlockEntity<E,B,I>,I extends ModBlockItem<B,I>> implements ItemConvertible {
    private B block;
    private I item;
    private BlockEntityType<E> type;
    public static<B extends ModBlockWithEntity<B,E,I>,E extends ModBlockEntity<E,B,I>,I extends ModBlockItem<B,I>> ModTokenWithEntity<B,E,I> create(Identifier name, Function3<BlockEntityType<E>,BlockPos,BlockState,E> entity, Function<Supplier<BlockEntityType<E>>,B> block, Function<B,I> item){
        var x = new ModTokenWithEntity<B,E,I>();
        x.block = Registry.register(Registries.BLOCK,name,block.apply(() -> x.type));
        x.item = Registry.register(Registries.ITEM,name,item.apply(x.block));
        x.type = Registry.register(Registries.BLOCK_ENTITY_TYPE,name,new BlockEntityType<>((b,c) -> entity.apply(x.type,b,c), Set.of(x.block), Util.getChoiceType(TypeReferences.BLOCK_ENTITY, name.toString())));
//        if(GoatTech.doPolymer){
//            PolymerBlockUtils.registerBlockEntity(x.type);
//        }
        return x;
    }
//    public static<B extends ModBlockWithEntity<B,E,I>,E extends ModBlockEntity<E,B,I>,I extends ModBlockItem<B,I>> ModTokenWithEntity<B,E,I> create_in(DynReg r, Identifier name, Function3<BlockEntityType<E>,BlockPos,BlockState,E> entity, Function<Supplier<BlockEntityType<E>>,B> block, Function<B,I> item){
//        var x = new ModTokenWithEntity<B,E,I>();
//        x.block = r.register(Registries.BLOCK,name,block.apply(() -> x.type));
//        x.item = r.register(Registries.ITEM,name,item.apply(x.block));
//        x.type = r.register(Registries.BLOCK_ENTITY_TYPE,name,new BlockEntityType<>((b,c) -> entity.apply(x.type,b,c), Set.of(x.block), Util.getChoiceType(TypeReferences.BLOCK_ENTITY, name.toString())));
//        if(GoatTech.doPolymer){
//            PolymerBlockUtils.registerBlockEntity(x.type);
//        }
//        return x;
//    }

    public I getItem() {
        return item;
    }

    public B getBlock(){
        return block;
    }

    public BlockEntityType<E> getType(){
        return type;
    }

    @Override
    public Item asItem() {
        return item;
    }
}
