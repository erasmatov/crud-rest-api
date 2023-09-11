package net.erasmatov.crudapi.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    T save(T t);

    List<T> getAll();

    T getById(ID id);

    T update(T t);

    void deleteById(ID id);

}
