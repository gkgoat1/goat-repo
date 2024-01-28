package at.gkgo.canon.meta;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
@FunctionalInterface
public interface MetaRewrite {
    Meta<?> rewrite(Meta<?> x);
    public static Event<MetaRewrite> EVENT = EventFactory.createArrayBacked(MetaRewrite.class,(a) -> (b) -> {
        for(var c: a){
            b = c.rewrite(b);
        }
        return b;
    });
}
