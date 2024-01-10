package at.gkgo.canon.rel;

//import at.gkgo.spin.ExampleMod;
import at.gkgo.canon.Canon;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class RelationListener implements IdentifiableResourceReloadListener {
    private static Table<Identifier, RelKey, Identifier> all = HashBasedTable.create();
    public static Identifier get(Identifier ty, RelKey k){
        return all.get(ty,k);
    }
    public static Map<RelKey, Identifier> getAll(Identifier ty){
        return all.row(ty);
    }

    public static  RelationListener INSTANCE = new RelationListener();

    @Override
    public Identifier getFabricId() {
        return new Identifier("canon:relationship");
    }

    public static Table<Identifier, RelKey, Identifier> loadAll(ResourceManager manager) {
        Table<Identifier, RelKey, Identifier> descriptions = HashBasedTable.create();

        var resources = manager.findResources("relations", id -> id.getPath().endsWith(".json"));

        for (Map.Entry<Identifier, Resource> entry : resources.entrySet()) {
            Identifier id = entry.getKey();
//            var realId = new Identifier(id.getNamespace(), id.getPath().substring("entries".length() + 1, id.getPath().length() - 5));

            try (var br = new BufferedReader(new InputStreamReader(entry.getValue().getInputStream()))) {
                JsonObject obj = JsonHelper.deserialize(br, true);
                Identifier type = new Identifier(JsonHelper.getString(obj, "type"));
                for(var i: JsonHelper.getObject(obj,"entries").entrySet()){
                    descriptions.put(type, RelKey.of(new Identifier(i.getKey())), new Identifier(i.getValue().getAsString()));
                }
//                var reader = EntryDescriptionReaders.getReader(type);
//
//                if (reader == null) {
//                    throw new IllegalStateException(type + " is an unknown entry reader type.");
//                }
//
//                RegistrationEntry desc = reader.apply(realId, obj);
//
//                descriptions.put(realId, desc);
            } catch (Exception e) {
                Canon.LOGGER.error("Encountered error while loading {}", id, e);
            }
        }

        Canon.LOGGER.info("Loaded {} relations", descriptions.size());

        return descriptions;
    }
    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        all = loadAll(manager);
        return synchronizer.whenPrepared(null);
    }
}
