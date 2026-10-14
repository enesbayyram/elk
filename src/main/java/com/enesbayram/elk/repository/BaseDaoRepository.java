package com.enesbayram.elk.repository;

import com.enesbayram.elk.base.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseDaoRepository<T extends BaseEntity> extends JpaRepository<T , String>  {
}
