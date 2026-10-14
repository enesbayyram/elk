package com.enesbayram.elk.controller.impl;

import com.enesbayram.elk.base.RestRootEntity;
import com.enesbayram.elk.controller.IRestEmployeeController;
import com.enesbayram.elk.dto.DtoEmployee;
import com.enesbayram.elk.dto.iu.DtoEmployeeIU;
import com.enesbayram.elk.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestEmployeeControllerImpl extends RestBaseController implements IRestEmployeeController {

    private final IEmployeeService employeeService;

    @Override
    @PostMapping(path = "/employee/save")
    public RestRootEntity<DtoEmployee> saveEmployee(@RequestBody @Validated DtoEmployeeIU input) {
        return ok(employeeService.saveEmployee(input));
    }

    @Override
    @GetMapping(path = "/employee/get/{employeeId}")
    public RestRootEntity<DtoEmployee> getEmployeeById(@PathVariable(name = "employeeId", required = true) String employeeId) {
        return ok(employeeService.getEmployeeById(employeeId));
    }

    @Override
    @DeleteMapping(path = "/employee/delete/{employeeId}")
    public RestRootEntity<Boolean> deleteEmployeeById(@PathVariable(name = "employeeId", required = true) String employeeId) {
        return ok(employeeService.deleteEmployeeById(employeeId));
    }

    @Override
    @PutMapping(path = "/employee/update/{employeeId}")
    public RestRootEntity<DtoEmployee> updateEmployee(@PathVariable(name = "employeeId", required = true) String employeeId,
                                                      @RequestBody DtoEmployeeIU input) {
        return ok(employeeService.updateEmployee(employeeId, input));
    }

}
