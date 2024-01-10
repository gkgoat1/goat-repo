package at.gkgo.canon.material;

import java.util.Optional;

public interface Handler {
    <T>Optional<T> getTag(MatTagKey<T> x);
}
