package at.gkgo.canon.block;

import com.mojang.serialization.Codec;
import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IDComponent implements Component {
    public Map<UUID,Loc> all = new HashMap<>();
    public static Codec<Map<UUID,Loc>> CODEC = Codec.unboundedMap(Codec.STRING.xmap(UUID::fromString, UUID::toString),Loc.CODEC);
    // retrieving a type for my component or for a required dependency's
    public static final ComponentKey<IDComponent> KEY =
            ComponentRegistry.getOrCreate(Identifier.of("canon","id"), IDComponent.class);
    @Override
    public void readFromNbt(NbtCompound tag) {
        all = new HashMap<>(CODEC.decode(NbtOps.INSTANCE,tag).result().get().getFirst());

    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        CODEC.encode(all,NbtOps.INSTANCE,tag).result().get();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}
