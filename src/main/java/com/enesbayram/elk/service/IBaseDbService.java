package com.enesbayram.elk.service;

import com.enesbayram.elk.base.BaseEntity;
import com.enesbayram.elk.dto.DtoBaseEntity;

import java.util.List;
import java.util.Optional;

public interface IBaseDbService<T extends BaseEntity> {

    <D extends DtoBaseEntity> D toDTO(T entity);

    <D extends DtoBaseEntity> List<D> toDTOList(List<T> list);

    T save(T entity);

    void delete(T entity);

    T update(T entity);

    <D extends DtoBaseEntity> T toDaoForInsert(D dto);

    <D extends DtoBaseEntity> T toDaoForUpdate(D dto , T entity);

    Optional<T> findById(String id);
}
