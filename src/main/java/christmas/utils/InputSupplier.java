package christmas.utils;

public interface InputSupplier<T> {
    T get() throws IllegalArgumentException;
}
