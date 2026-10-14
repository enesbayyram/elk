package com.enesbayram.elk.dto;

import com.enesbayram.elk.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoEmployee extends DtoBaseEntity{

    private String firstName;

    private String lastName;

    private Integer age;

    private GenderType genderType;

    private BigDecimal salary;

    private String tckn;

    private DtoAddress address;
}
