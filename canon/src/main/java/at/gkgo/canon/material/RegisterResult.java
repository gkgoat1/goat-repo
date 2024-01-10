package at.gkgo.canon.material;

public class RegisterResult <T>{
    public final T value;
    public final boolean registered;

    public RegisterResult(T value, boolean registered) {
        this.value = value;
        this.registered = registered;
    }
}
