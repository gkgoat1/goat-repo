package at.gkgo.canon.material;

import java.util.Optional;
import java.util.function.Function;

public interface MaterialTagWrapper {
    public <T>Optional<T> get(Material m, MatTagKey<T> tag, TagGetter next);
}
