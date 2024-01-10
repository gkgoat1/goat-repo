package at.gkgo.canon.material;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MaterialEvents {
    public static MaterialTagWrapper EMPTY_TAG_WRAPPER = new MaterialTagWrapper() {

        @Override
        public <T> Optional<T> get(Material m, MatTagKey<T> tag, TagGetter next) {
            return next.getTag(tag);
        }
    };
    public static Event<MaterialTagWrapper> TAG = EventFactory.createArrayBacked(MaterialTagWrapper.class, EMPTY_TAG_WRAPPER, MaterialEvents::combineTags);
    public static MaterialTagWrapper combineTags(MaterialTagWrapper []all){
        if(all.length == 1){
            return all[0];
        }
        if(all.length == 0){
            return EMPTY_TAG_WRAPPER;
        }
        var a = all[0];
        var next = Arrays.stream(all).skip(1).toList();
        var n2 = combineTags(next.toArray(MaterialTagWrapper[]::new));
        return new MaterialTagWrapper() {
            @Override
            public <T> Optional<T> get(Material m, MatTagKey<T> tag, TagGetter next) {
                return a.get(m, tag, new TagGetter() {
                    @Override
                    public <T> Optional<T> getTag(MatTagKey<T> tMatTagKey) {
                        return n2.get(m,tMatTagKey,next);
                    }
                });
            }
        };
    }
}
