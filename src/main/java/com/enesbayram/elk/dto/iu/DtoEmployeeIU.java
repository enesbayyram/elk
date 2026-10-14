package com.enesbayram.elk.dto.iu;

import com.enesbayram.elk.dto.DtoAddress;
import com.enesbayram.elk.dto.DtoCrud;
import com.enesbayram.elk.enums.GenderType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoEmployeeIU extends DtoCrud {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Integer age;

    private GenderType genderType;

    private BigDecimal salary;

    @NotBlank
    private String tckn;

    private DtoAddress address;
}
