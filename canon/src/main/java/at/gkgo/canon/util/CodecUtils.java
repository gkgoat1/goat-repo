package at.gkgo.canon.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;

public class CodecUtils {
    public static <T>Codec<T> jsonKey(Codec<T> x){
        return Codec.STRING.flatXmap((s) -> x.decode(JsonOps.INSTANCE,new Gson().fromJson(s, JsonElement.class)).map(Pair::getFirst),(v) -> x.encodeStart(JsonOps.INSTANCE,v).map(JsonElement::toString));
    }
}
