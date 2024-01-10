package at.gkgo.canon.material.ore;

import at.gkgo.canon.material.Form;
import at.gkgo.canon.material.Material;
import at.gkgo.canon.material.MaterialRegistries;
import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class OreForm implements Form<BlockItem> {
    public final Identifier id;

    public OreForm(Identifier id) {
        this.id = id;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public BlockItem create(Material m) {
        return Registry.register(Registries.ITEM,m.mangle(getId()),new BlockItem(Registry.register(Registries.BLOCK,m.mangle(getId()),new Block(FabricBlockSettings.create()){
            @Override
            protected MapCodec<? extends Block> getCodec() {
                return MaterialRegistries.MATERIAL_BLOCK.fieldOf("$");
            }
        }),new Item.Settings()));
    }
}
