package com.enesbayram.elk.model;

import com.enesbayram.elk.base.BaseEntity;
import com.enesbayram.elk.enums.GenderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender_type")
    @Enumerated(EnumType.STRING)
    private GenderType genderType;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "tckn" , length = 11 , nullable = false)
    private String tckn;

    @OneToOne
    private Address address;
}
