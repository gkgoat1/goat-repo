package at.gkgo.canon.idcode;

import at.gkgo.canon.material.MaterialRegistries;
import at.gkgo.canon.rel.RelKey;
import at.gkgo.canon.rel.RelationListener;
import at.gkgo.canon.rel.RelationProvider;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import java.util.Deque;
import java.util.Stack;

public class InstructionKind<T extends IDCode> {
    public final Codec<T> codec;

    public InstructionKind(Codec<T> codec) {
        this.codec = codec;
    }
    public static <T extends IDCode> InstructionKind<T> only(T x){
        return new InstructionKind<>(Codec.unit(x));
    }

    public static Registry<InstructionKind<?>> ALL = FabricRegistryBuilder.createSimple(RegistryKey.<InstructionKind<?>>ofRegistry(Identifier.of("canon","idcode/instruction"))).buildAndRegister();
    public static Codec<IDCode> CODEC = ALL.getCodec().dispatch((i) -> i.kind(),(k) -> k.codec);
    public static InstructionKind<?> RELATE = Registry.register(ALL,Identifier.of("canon","relate"),only(new IDCode() {
        @Override
        public InstructionKind<?> kind() {
            return RELATE;
        }

        @Override
        public void exec(Deque<Identifier> all) {
            var a = all.pop();
            var b = all.pop();
            all.push(RelationListener.getAll(a).get(RelKey.of(b)));
        }
    }));
    public static InstructionKind<?> FORM_ITEM = Registry.register(ALL,Identifier.of("canon","form_item"),only(new IDCode() {
        @Override
        public InstructionKind<?> kind() {
            return FORM_ITEM;
        }

        @Override
        public void exec(Deque<Identifier> all) {
            var a = all.pop();
            var b = all.pop();
            var i = (ItemConvertible)MaterialRegistries.material(a).get().all.get(b).value;
            all.push(Registries.ITEM.getId(i.asItem()));
        }
    }));
    public static InstructionKind<?> INVERT_ITEM = Registry.register(ALL,Identifier.of("canon","invert_item"),only(new IDCode() {
        @Override
        public InstructionKind<?> kind() {
            return INVERT_ITEM;
        }

        @Override
        public void exec(Deque<Identifier> all) {
            var a = all.pop();
            var i = MaterialRegistries.invert(Registries.ITEM.get(a)).get();
            all.push(i.getLeft().id);
            all.push(i.getRight().getId());
        }
    }));
}
