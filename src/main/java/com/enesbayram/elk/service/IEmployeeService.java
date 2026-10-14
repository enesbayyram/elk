package com.enesbayram.elk.service;

import com.enesbayram.elk.dto.DtoEmployee;
import com.enesbayram.elk.dto.iu.DtoEmployeeIU;
import com.enesbayram.elk.model.Employee;

public interface IEmployeeService extends IBaseDbService<Employee> {

    DtoEmployee saveEmployee(DtoEmployeeIU input);

    DtoEmployee getEmployeeById(String employeeId);

    Boolean deleteEmployeeById(String employeeId);

    DtoEmployee updateEmployee(String employeeId , DtoEmployeeIU input);



}
