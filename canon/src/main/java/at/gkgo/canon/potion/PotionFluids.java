package at.gkgo.canon.potion;

import at.gkgo.canon.fluid.SimpleFluid;
import at.gkgo.canon.meta.*;
import com.mojang.serialization.Codec;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class PotionFluids {
    public static Codec<MetaVariant<Potion>> CODEC = MetaVariant.codec(Registries.POTION.getCodec());
    public static SimpleFluid.Still POTION = SimpleFluid.register(Identifier.of("canon","potion"),Meta.variant(Registries.POTION.getCodec(), Potions.AWKWARD, null));
}
