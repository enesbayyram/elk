package com.enesbayram.elk.controller;

import com.enesbayram.elk.base.RestRootEntity;
import com.enesbayram.elk.dto.DtoEmployee;
import com.enesbayram.elk.dto.iu.DtoEmployeeIU;

public interface IRestEmployeeController {

    RestRootEntity<DtoEmployee> saveEmployee(DtoEmployeeIU input);

    RestRootEntity<DtoEmployee> getEmployeeById(String employeeId);

    RestRootEntity<Boolean> deleteEmployeeById(String employeeId);

    RestRootEntity<DtoEmployee> updateEmployee(String employeeId, DtoEmployeeIU input);

}
