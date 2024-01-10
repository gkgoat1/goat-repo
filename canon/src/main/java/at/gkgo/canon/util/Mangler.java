package at.gkgo.canon.util;

public class Mangler {
    public static String mangle(String x){
        return x.replaceAll("_","__").replaceAll("/","_");
    }
}
