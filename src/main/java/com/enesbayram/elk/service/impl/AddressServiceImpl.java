package com.enesbayram.elk.service.impl;

import com.enesbayram.elk.dto.DtoAddress;
import com.enesbayram.elk.dto.DtoBaseEntity;
import com.enesbayram.elk.model.Address;
import com.enesbayram.elk.repository.AddressRepository;
import com.enesbayram.elk.service.IAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl extends BaseDbServiceImpl <Address , AddressRepository > implements IAddressService {

    @Override
    public Class<? extends DtoBaseEntity> getDTOClassForService() {
        return DtoAddress.class;
    }
}
