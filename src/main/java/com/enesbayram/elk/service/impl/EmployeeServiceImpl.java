package com.enesbayram.elk.service.impl;

import com.enesbayram.elk.dto.DtoBaseEntity;
import com.enesbayram.elk.dto.DtoEmployee;
import com.enesbayram.elk.dto.iu.DtoEmployeeIU;
import com.enesbayram.elk.model.Address;
import com.enesbayram.elk.model.Employee;
import com.enesbayram.elk.repository.EmployeeRepository;
import com.enesbayram.elk.service.IAddressService;
import com.enesbayram.elk.service.IEmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl extends BaseDbServiceImpl<Employee, EmployeeRepository> implements IEmployeeService {

    private final IAddressService addressService;

    @Override
    public Class<? extends DtoBaseEntity> getDTOClassForService() {
        return DtoEmployee.class;
    }

    @Override
    public DtoEmployee saveEmployee(DtoEmployeeIU input) {
        Address address = addressService.toDaoForInsert(input.getAddress());
        Employee employee = toDaoForInsert(input);
        employee.setAddress(addressService.save(address));

        return toDTO(save(employee));
    }

    @Override
    public DtoEmployee getEmployeeById(String employeeId) {
        Optional<Employee> optional = findById(employeeId);
        if (optional.isEmpty()) {
            throw new RuntimeException("Employee bulunamadı : " + employeeId);
        }
        return toDTO(optional.get());
    }

    @Override
    public Boolean deleteEmployeeById(String employeeId) {
        Optional<Employee> optional = findById(employeeId);
        if (optional.isEmpty()) {
            throw new RuntimeException("Employee bulunamadı : " + employeeId);
        }
        delete(optional.get());
        return Boolean.TRUE;
    }

    @Override
    public DtoEmployee updateEmployee(String employeeId, DtoEmployeeIU input) {
        Optional<Employee> optionalEmployee = findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new RuntimeException("Employee bulunamadı : " + employeeId);
        }
        Employee employee = toDaoForUpdate(input, optionalEmployee.get());
        employee.getAddress().setId(optionalEmployee.get().getAddress().getId());

        return toDTO(update(employee));
    }
}