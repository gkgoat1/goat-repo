package at.gkgo.canon.block.form;

import at.gkgo.canon.block.ModBlock;
import at.gkgo.canon.block.ModBlockItem;
import at.gkgo.canon.block.ModToken;
import at.gkgo.canon.material.Form;
import at.gkgo.canon.material.Material;
import net.minecraft.util.Identifier;

import java.util.Set;
import java.util.function.Function;

public abstract class InternalBlockForm <B extends ModBlock<B,I>,I extends ModBlockItem<B,I>> implements Form<ModToken<B,I>> {
    public final Identifier id;
    public final Function<Material,B> block;
    public final Function<B,I> item;

    @Override
    public Set<Object> components(Object value) {
        var w = (ModToken<B,I>)value;
        return Set.of(w.getBlock(),w.getItem());
    }

    public InternalBlockForm(Identifier id, Function<Material, B> block, Function<B, I> item) {
        this.id = id;
        this.block = block;
        this.item = item;
    }

    @Override
    public Identifier getId() {
        return id;
    }
    abstract boolean gate(Material m);

    @Override
    public ModToken<B,I> create(Material m) {
        if(!gate(m))return null;
        return ModToken.create(m.mangle(getId()),() -> block.apply(m),item);
    }
}
