package at.gkgo.canon.meta;

public class MatMeta <T,U>{
    public final T forMaterial;
    public final U forForm;

    public MatMeta(T forMaterial, U forForm) {
        this.forMaterial = forMaterial;
        this.forForm = forForm;
    }
}
