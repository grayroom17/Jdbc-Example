package com.jdbc.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    E save(E entity);

    List<E> findAll();

    Optional<E> findById(K id);

    void updateById(E entity);

    boolean deleteById(K id);
}
