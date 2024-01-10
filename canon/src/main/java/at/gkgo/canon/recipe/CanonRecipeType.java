package at.gkgo.canon.recipe;

import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
@FunctionalInterface
public interface CanonRecipeType<T extends Recipe<?>> extends RecipeType<T>, RecipeSerializer<T> {
    @Override
    default T read(PacketByteBuf buf){
        return codec().decode(NbtOps.INSTANCE,buf.readNbt()).result().get().getFirst();
    }

    @Override
    default void write(PacketByteBuf buf, T recipe){
        buf.writeNbt(codec().encodeStart(NbtOps.INSTANCE,recipe).get().orThrow());
    }
}
