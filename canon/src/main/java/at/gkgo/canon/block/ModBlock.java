package at.gkgo.canon.block;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.NoSuchElementException;

public class ModBlock <Self extends ModBlock<Self,I>, I extends ModBlockItem<Self,I>> extends Block {
//    public final boolean tickable;
//    public ModBlock(Settings settings, boolean tickable) {
//        super(settings);
//        this.tickable = tickable;
//    }
    public ModBlock(Settings settings){
        super(settings);
    }

    public I getItem(){
        return (I) Registries.ITEM.get(Registries.BLOCK.getId(this));
    }
protected List<ItemStack> getBaseDrops(LootContextParameterSet lootContextParameterSet){
    var x = new ItemStack(getItem());
//                if(this instanceof ModBlockWithEntity<?,?,?> entity){
    try {
        var e = lootContextParameterSet.get(LootContextParameters.BLOCK_ENTITY);
        x.getOrCreateNbt().put("BlockEntityTag", e.createNbt());
    }catch(NoSuchElementException ignored){

    }
//                }
    return List.of(x);
}
    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        Identifier identifier = this.getLootTableId();
        LootContextParameterSet lootContextParameterSet = builder.add(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
        if (identifier == LootTables.EMPTY) {
//            return Collections.emptyList();
            return getBaseDrops(lootContextParameterSet);
        } else {
            ServerWorld serverWorld = lootContextParameterSet.getWorld();
            LootTable lootTable = serverWorld.getServer().getLootManager().getLootTable(identifier);
            if(lootTable == LootTable.EMPTY){
                return getBaseDrops(lootContextParameterSet);
            }
            return lootTable.generateLoot(lootContextParameterSet);
        }
    }

    public Block vanilla(BlockState state){
        return Blocks.DIRT;
    }
}
