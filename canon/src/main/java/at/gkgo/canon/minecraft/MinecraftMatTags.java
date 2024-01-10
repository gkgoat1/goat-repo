package at.gkgo.canon.minecraft;

import at.gkgo.canon.material.MatTagKey;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;

public class MinecraftMatTags {
    public static MatTagKey<WoodType> WOOD = MatTagKey.of(Identifier.of("minecraft","wood"));
    public static MatTagKey<Unit> METALLIC = MatTagKey.of(Identifier.of("minecraft","metallic"));
}
