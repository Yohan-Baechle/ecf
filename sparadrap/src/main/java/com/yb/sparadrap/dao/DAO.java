package com.yb.sparadrap.dao;

import java.util.List;

public interface DAO<T, K> {
    void add(T entity);
    T getById(K id);
    List<T> getAll();
    void update(T entity);
    void delete(K id);
}
