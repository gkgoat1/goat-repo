package at.gkgo.canon.mixin;

import at.gkgo.canon.meta.MetaItem;
import com.mojang.serialization.Codec;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Unit;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Fluid.class)
public class FluidMixin implements MetaItem {

}
