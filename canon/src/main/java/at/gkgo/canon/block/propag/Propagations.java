package at.gkgo.canon.block.propag;

import at.gkgo.canon.block.ModBlockEntity;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.function.Consumer;

public class Propagations {
    public static<T> void bootstrap(Propagation<T> p){
        ModBlockEntity.initPropag(p);
    }
    public static Event<Consumer<Propagation<?>>> created = EventFactory.createArrayBacked(Consumer.class,(l) -> (Consumer<Propagation<?>>) propagation -> {
        bootstrap(propagation);
        for (var a : l) {
            a.accept(propagation);
        }
    });
//    public static PropagationImpl<EU> newEU(Identifier id){
//       var e = new PropagationImpl<>((l) -> l.stream().reduce(new EU(0),(a,b) -> new EU(a.all + b.all)), BlockApiLookup.get(id,EU.class, Direction.class),EU.class);
//       e.lookup().registerForBlocks((w,p,s,en,c) -> new EU(Long.MAX_VALUE / 65536), Energy.CREATIVE_ENERGY.getBlock());
//       return e;
//    }
}

