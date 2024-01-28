package at.gkgo.canon.util;

public class TypeUtils {
    public static <T,U> U unsafeCoerce(T x){
        return (U)(Object)x;
    }
}
