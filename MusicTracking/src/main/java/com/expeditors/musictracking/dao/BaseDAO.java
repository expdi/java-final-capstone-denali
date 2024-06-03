package com.expeditors.musictracking.dao;

import java.util.List;
import java.util.Optional;

public interface BaseDAO<T> {
    T insert(T object);

    boolean delete(int id);

    boolean update(T object);

    T findById(int id);

    List<T> findAll();

}
