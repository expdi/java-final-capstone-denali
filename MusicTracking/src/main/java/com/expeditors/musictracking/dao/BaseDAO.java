package com.expeditors.musictracking.dao;

import org.springframework.context.annotation.Profile;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Profile("inmemory")
public interface BaseDAO<T> {
    T insert(T object);

    boolean deleteById(int id);

    boolean update(T object);

    T findById(int id);

    List<T> findAll();

}
