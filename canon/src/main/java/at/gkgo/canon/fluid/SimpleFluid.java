package at.gkgo.canon.fluid;

import at.gkgo.canon.meta.Meta;
import at.gkgo.canon.meta.MetaItem;
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

import java.util.function.Function;

public abstract class SimpleFluid extends TutorialFluid implements ItemConvertible, MetaItem {
    @Override
    public Item asItem() {
        return getBucketItem();
    }

    final Identifier id;
    final Function<Meta<?>,Meta<?>> meta;
    SimpleFluid(Identifier x, Function<Meta<?>,Meta<?>> meta){
        id = x;
        this.meta = meta;
    }

    @Override
    public Meta<?> canon$meta() {
        return meta.apply(MetaItem.super.canon$meta());
    }

    public static Still register(Identifier i,Meta<?> a){
        Function<Meta<?>,Meta<?>> m = (b) -> b.with(a);
        var s = Registry.register(Registries.FLUID,i,new Still(i,m));
        Registry.register(Registries.FLUID,new Identifier(i.toString() + "/flowing"),new Flowing(i,m));
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
        Flowing(Identifier x, Function<Meta<?>,Meta<?>> meta) {
            super(x, meta);
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
        Still(Identifier x,Function<Meta<?>,Meta<?>> meta) {
            super(x, meta);
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