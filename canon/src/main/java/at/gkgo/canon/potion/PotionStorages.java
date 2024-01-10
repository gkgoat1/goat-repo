package at.gkgo.canon.potion;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.item.Items;

public class PotionStorages {
    static{
        FluidStorage.combinedItemApiProvider(Items.GLASS_BOTTLE).register(EmptyPotionStorage::new);
        FluidStorage.combinedItemApiProvider(Items.POTION).register(FluidPotionStorage::find);
    }
    public static void init(){}
}
