package model.builder;

public interface IBuilder<T> {
    T build();
    void reset();
}
