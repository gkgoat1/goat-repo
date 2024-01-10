package at.gkgo.canon.minecraft;

import at.gkgo.canon.material.Form;
import at.gkgo.canon.material.Material;
import at.gkgo.canon.material.MaterialRegistries;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class MinecraftForms {
    public static Form<Item> metalForm(Identifier id){
        return MaterialRegistries.registerForm(new Form<Item>() {
            @Override
            public Identifier getId() {
                return id;
            }

            @Override
            public Item create(Material m) {
                if(m.getTag(MinecraftMatTags.METALLIC).isPresent()){
                    return Registry.register(Registries.ITEM,m.mangle(getId()),new Item(new Item.Settings()));
                }
                return null;
            }
        });
    }
    public static Form<BlockItem> metalBlockForm(Identifier id, Block defaul){
        return MaterialRegistries.registerForm(new Form<BlockItem>() {
            @Override
            public Identifier getId() {
                return id;
            }

            @Override
            public BlockItem create(Material m) {
                if(m.getTag(MinecraftMatTags.METALLIC).isPresent()){
                    var i = m.mangle(getId());
                    return Registry.register(Registries.ITEM,i,new BlockItem(Registry.register(Registries.BLOCK,i,new Block(FabricBlockSettings.copyOf(defaul))),new Item.Settings()));
                }
                return null;
            }
        });
    }
    public static Form<Item> INGOT = metalForm(Identifier.of("minecraft","ingot"));
    public static Form<Item> RAW = metalForm(Identifier.of("minecraft","raw"));
    public static Form<BlockItem> BLOCK = metalBlockForm(Identifier.of("minecraft","block"),Blocks.IRON_BLOCK);
    public static Form<BlockItem> RAW_BLOCK = metalBlockForm(Identifier.of("minecraft","raw_block"),Blocks.RAW_IRON_BLOCK);
    public static void init(){

    }
}
