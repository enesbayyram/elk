package com.enesbayram.elk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoAddress extends DtoBaseEntity{

    private String city;

    private String county;

    private String street;

    private String no;
}
