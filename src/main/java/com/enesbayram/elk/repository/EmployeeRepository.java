package com.enesbayram.elk.repository;


import com.enesbayram.elk.model.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseDaoRepository <Employee> {
}
