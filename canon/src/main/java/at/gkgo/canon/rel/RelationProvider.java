package at.gkgo.canon.rel;

import at.gkgo.canon.bake.Baker;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RelationProvider implements DataProvider {
    protected final DataOutput.PathResolver pathResolver;
    public RelationProvider(FabricDataOutput o){
        pathResolver = o.getResolver(DataOutput.OutputType.DATA_PACK,"relations");
    }
//    public static RelationProvider of(DataGenerator.Pack p, Handler h){
//        return p.addProvider((o) -> new RelationProvider(o));
//    }
    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        Table<Identifier, RelKey, Identifier> all = HashBasedTable.create();
        generate(all);
        List<CompletableFuture<?>> l = new ArrayList<>();
        for(var r: all.rowMap().entrySet()){
            var j = new JsonObject();
            var k = new JsonObject();
            j.addProperty("type",r.getKey().toString());
            for(var e: r.getValue().entrySet()){
                k.addProperty(e.getKey().id.toString(),e.getValue().toString());
            }
            j.add("entries",k);
            l.add(DataProvider.writeToPath(writer,j,pathResolver.resolveJson(r.getKey())));
        }
        return CompletableFuture.allOf(l.toArray((m) -> new CompletableFuture<?>[m]));
    }

    public static void runBaked(Table<Identifier, RelKey, Identifier> all, Baker b){
        for(var r: all.rowMap().entrySet()){
            var j = new JsonObject();
            var k = new JsonObject();
            j.addProperty("type",r.getKey().toString());
            for(var e: r.getValue().entrySet()){
                k.addProperty(e.getKey().id.toString(),e.getValue().toString());
            }
            j.add("entries",k);
//            l.add(DataProvider.writeToPath(writer,j,pathResolver.resolveJson(r.getKey())));
            b.bake(Identifier.of(r.getKey().getNamespace(),String.join("","relations/",r.getKey().getPath(),".json")),j, ResourceType.SERVER_DATA);
        }

    }

    public void generate(Table<Identifier, RelKey, Identifier> all){

    }

    @Override
    public String getName() {
        return "canon/relations";
    }
}
