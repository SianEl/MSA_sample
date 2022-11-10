package com.demo.api.system.repository;

import java.util.List;

public interface CrudMapper<E, I, P>{
    long count(P param);

    boolean exists(P param);

    List<E> selectAll(P param);

    E selectById(I id);

    boolean existsById(I id);

    int inert(E entity);

    int updateById(E entity);

    int deleteById(I id);
}
