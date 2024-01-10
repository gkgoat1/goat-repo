package at.gkgo.canon.block.form;

import at.gkgo.canon.block.ModBlockEntity;
import at.gkgo.canon.block.ModBlockItem;
import at.gkgo.canon.block.ModBlockWithEntity;
import at.gkgo.canon.block.ModTokenWithEntity;
import at.gkgo.canon.material.Form;
import at.gkgo.canon.material.Material;
import com.mojang.datafixers.util.Function3;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class InternalBlockWithEntityForm<E extends ModBlockEntity<E,B,I>,B extends ModBlockWithEntity<B,E,I>,I extends ModBlockItem<B,I>> implements Form<ModTokenWithEntity<B,E,I>> {
    public final Identifier id;
    public final BiFunction<Material,Supplier<BlockEntityType<E>>,B> block;
    public final Function<B,I> item;
    public final Function3<BlockEntityType<E>, BlockPos, BlockState,E> entity;
    abstract boolean gate(Material m);

    @Override
    public Set<Object> components(Object value) {
        var w = (ModTokenWithEntity<B,E,I>)value;
        return Set.of(w.getBlock(),w.getItem(),w.getType());
    }

    public InternalBlockWithEntityForm(Identifier id, BiFunction<Material,Supplier<BlockEntityType<E>>,B> block, Function<B, I> item, Function3<BlockEntityType<E>, BlockPos, BlockState, E> entity) {
        this.id = id;
        this.block = block;
        this.item = item;
        this.entity = entity;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public ModTokenWithEntity<B, E,I> create(Material m) {
        if(!gate(m))return null;
        return ModTokenWithEntity.create(m.mangle(getId()),entity,(a) -> block.apply(m,a),item);
    }
}
