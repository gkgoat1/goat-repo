package at.gkgo.canon.bake;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.PackResourceMetadata;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BakedResourcePack implements ResourcePack {
    public final ResourceType type;

    public BakedResourcePack(ResourceType type) {
        this.type = type;
    }

    @Nullable
    @Override
    public InputSupplier<InputStream> openRoot(String... segments) {
        return null;
    }

    @Nullable
    @Override
    public InputSupplier<InputStream> open(ResourceType type, Identifier id) {
        if(BakerRegistry.BAKED.containsKey(id) && BakerRegistry.BAKED.get(id).validFor == type && type ==this.type){
            return () -> new ByteArrayInputStream(BakerRegistry.BAKED.get(id).obj);
        }
        return null;
    }

    @Override
    public void findResources(ResourceType type, String namespace, String path, ResultConsumer consumer) {
        if (path.endsWith("/")) path = path.substring(0,path.length()-1);
//        Goatlib.LOGGER.info(path);
//        if(path.startsWith(type.getDirectory())){
//            path = path.substring(type.getDirectory().length());
//        }
        final String finalPath = path;
        BakerRegistry.BAKED.keySet().stream().filter(Objects::nonNull).filter(loc -> loc.getPath().startsWith(finalPath)).forEach((id) -> {
            InputSupplier<InputStream> resource = this.open(type, id);
            if (resource != null) {
                consumer.accept(id, resource);
            }
        });
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return FabricLoader.getInstance().getAllMods().stream().map((m) -> m.getMetadata().getId()).collect(Collectors.toSet());
    }

    @Nullable
    @Override
    public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        if(metaReader == PackResourceMetadata.SERIALIZER) {
            return (T) new PackResourceMetadata(Text.literal("Canon-generated dynamic data"), SharedConstants.getGameVersion().getResourceVersion(type), Optional.empty());
        }
        return null;
    }

    @Override
    public String getName() {
        return "canon";
    }

    @Override
    public void close() {

    }
}
