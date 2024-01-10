package at.gkgo.canon.fluid;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;

public abstract class SimpleFluid extends TutorialFluid implements ItemConvertible {
    @Override
    public Item asItem() {
        return getBucketItem();
    }

    final Identifier id;
    SimpleFluid(Identifier x){
        id = x;
    }
    public static Still register(Identifier i){
        var s = Registry.register(Registries.FLUID,i,new Still(i));
        Registry.register(Registries.FLUID,new Identifier(i.toString() + "/flowing"),new Flowing(i));
        Registry.register(Registries.ITEM,new Identifier(i.toString() + "_bucket"), (Item)new BucketItem(s, new FabricItemSettings()));
        Registry.register(Registries.BLOCK, i, new FluidBlock(s, FabricBlockSettings.copy(Blocks.WATER)){});
        return s;
    }

    @Override
    public Fluid getStill() {
        return Registries.FLUID.get(id);
    }

    @Override
    public Fluid getFlowing() {
        return Registries.FLUID.get(new Identifier(id.toString() + "/flowing"));
    }

    @Override
    public Item getBucketItem() {
        return Registries.ITEM.get(new Identifier(id.toString() + "_bucket"));
    }

    @Override
    protected BlockState toBlockState(FluidState fluidState) {
        return Registries.BLOCK.get(id).getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(fluidState));
    }

    public static class Flowing extends SimpleFluid {
        Flowing(Identifier x) {
            super(x);
        }

        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends SimpleFluid {
        Still(Identifier x) {
            super(x);
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}