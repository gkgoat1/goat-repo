package at.gkgo.canon.material;

import java.util.Optional;

@FunctionalInterface
public interface TagGetter {
    <T>Optional<T> getTag(MatTagKey<T> tMatTagKey);
}
