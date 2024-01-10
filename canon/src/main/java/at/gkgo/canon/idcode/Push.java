package at.gkgo.canon.idcode;

import com.mojang.serialization.Codec;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class Push implements IDCode {
    public static Codec<Push> CODEC = Identifier.CODEC.listOf().xmap((l) -> new Push(l),p -> p.all).fieldOf("to_push").codec();
    public static InstructionKind<Push> KIND =Registry.register(InstructionKind.ALL,Identifier.of("canon","push"), new InstructionKind<>(CODEC));
    public final List<Identifier> all;

    public Push(List<Identifier> all) {
        this.all = all;
    }

    @Override
    public InstructionKind<?> kind() {
        return KIND;
    }

    @Override
    public void exec(Deque<Identifier> all) {
        for(var a: this.all){
            all.push(a);
        }
    }
}
