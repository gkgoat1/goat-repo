package at.gkgo.canon.idcode;

import net.minecraft.util.Identifier;

import java.util.Deque;
import java.util.Stack;

public interface IDCode {
    InstructionKind<?> kind();
    void exec(Deque<Identifier> all);
}
