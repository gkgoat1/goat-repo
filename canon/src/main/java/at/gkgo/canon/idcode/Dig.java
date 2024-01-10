package at.gkgo.canon.idcode;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayDeque;
import java.util.Deque;

public class Dig implements IDCode{
    public final int depth;
    public final int shift;
    public static Codec<Dig> CODEC = RecordCodecBuilder.create((i) -> i.group(
            Codec.INT.fieldOf("depth").forGetter((a) -> a.depth),
            Codec.INT.fieldOf("shift").forGetter((a) -> a.shift)
    ).apply(i,Dig::new));
    public static InstructionKind<Dig> KIND = Registry.register(InstructionKind.ALL,Identifier.of("canon","dig"), new InstructionKind<>(CODEC));

    public Dig(int depth, int shift) {
        this.depth = depth;
        this.shift = shift;
    }

    @Override
    public InstructionKind<?> kind() {
        return KIND;
    }

    @Override
    public void exec(Deque<Identifier> all) {
        var toOperateOn = new ArrayDeque<Identifier>();
        for(int i = 0; i < depth; i++)toOperateOn.push(all.pop());
        for(int i = 0; i < shift; i++)toOperateOn.addLast(toOperateOn.pop());
        for(var i: toOperateOn)all.push(i);
    }
}
