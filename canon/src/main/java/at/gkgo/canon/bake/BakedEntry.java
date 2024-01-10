package at.gkgo.canon.bake;

import com.google.gson.JsonObject;
import net.minecraft.resource.ResourceType;

public class BakedEntry {
    public final byte[] obj;
    public final ResourceType validFor;

    public BakedEntry(byte[] obj, ResourceType validFor) {
        this.obj = obj;
        this.validFor = validFor;
    }
}
