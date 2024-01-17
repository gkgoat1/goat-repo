package at.gkgo.canon.util;

import net.fabricmc.fabric.api.transfer.v1.storage.SlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedSlottedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;

import java.util.List;
import java.util.stream.Collectors;

public class StorageUtils {
    public static <T>Storage<T> combine(List<Storage<T>> all){
        if(all.stream().allMatch((s) -> s instanceof SlottedStorage<T>)){
            return new CombinedSlottedStorage<>(all.stream().map((s) -> (SlottedStorage<T>)s).collect(Collectors.toList()));
        }
        return new CombinedStorage<>(all);
    }
}
