package at.gkgo.canon.guard;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;

@FunctionalInterface
public interface GuardHandler {
    boolean guarded(World w, BlockPos pos);
    public static Event<GuardHandler> EVENT = EventFactory.createArrayBacked(GuardHandler.class,(l) -> (w,p) -> Arrays.stream(l).anyMatch((h) -> h.guarded(w,p)));
}
