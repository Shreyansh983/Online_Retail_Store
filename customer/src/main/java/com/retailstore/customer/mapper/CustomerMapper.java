package com.retailstore.customer.mapper;

import com.retailstore.customer.dto.CustomerRequestDTO;
import com.retailstore.customer.dto.CustomerResponseDTO;
import com.retailstore.customer.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = AddressMapper.class)
public interface CustomerMapper {
    
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "fullName", expression = "java(customer.getFirstName() + \" \" + (customer.getLastName() != null ? customer.getLastName() : \"\"))")
    CustomerResponseDTO customerToCustomerResponseDTO(Customer customer);

    @Mapping(target = "id", ignore = true)
    Customer customerRequestDTOToCustomer(CustomerRequestDTO customerRequestDTO);
}