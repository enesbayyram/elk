package com.enesbayram.elk.service.impl;

import com.enesbayram.elk.base.BaseEntity;
import com.enesbayram.elk.dto.DtoBaseEntity;
import com.enesbayram.elk.repository.BaseDaoRepository;
import com.enesbayram.elk.service.IBaseDbService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public abstract class BaseDbServiceImpl<T extends BaseEntity, R extends BaseDaoRepository<T>> implements IBaseDbService<T> {

    public abstract Class<? extends DtoBaseEntity> getDTOClassForService();

    @Autowired
    protected ModelMapper modelMapper;

    @Autowired
    protected R dao;

    @PersistenceContext
    private EntityManager em;

    private Class<T> getClazz() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        return (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }


    @Override
    @SuppressWarnings("unchecked")
    public <D extends DtoBaseEntity> D toDTO(T entity) {
        D dto;
        try {
            return (D) modelMapper.map(entity, getDTOClassForService());
        } catch (Exception e) {
            throw new RuntimeException("toDTO method error : " + e.getMessage());
        }
    }

    @Override
    public <D extends DtoBaseEntity> List<D> toDTOList(List<T> list) {
        List<D> dtoList = List.of();
        try {
            for (T entity : list) {
                dtoList.add(toDTO(entity));
            }
            return dtoList;
        } catch (Exception e) {
            throw new RuntimeException("toDTO method error : " + e.getMessage());
        }
    }

    @Override
    public T save(T entity) {
        return dao.save(entity);
    }

    @Override
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Override
    public T update(T entity) {
        entity.setUpdateTime(LocalDateTime.now());
        entity.setUpdateUser("ebayram");
        return em.merge(entity);
    }

    @Override
    public <D extends DtoBaseEntity> T toDaoForInsert(D dto) {
        try {
            T entity = (T) getClazz().getDeclaredConstructor().newInstance();
            modelMapper.map(dto, entity);
            entity.setCreateTime(LocalDateTime.now());
            entity.setCreateUser("ebayram");
            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public <D extends DtoBaseEntity> T toDaoForUpdate(D dto, T entity) {
        T model;
        try {
            model = (T) getClazz().getDeclaredConstructor().newInstance();
            modelMapper.map(dto , model);

            model.setId(entity.getId());
            model.setCreateUser(entity.getCreateUser());
            model.setCreateTime(entity.getCreateTime());
            return model;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<T> findById(String id) {
        return dao.findById(id);
    }
}
