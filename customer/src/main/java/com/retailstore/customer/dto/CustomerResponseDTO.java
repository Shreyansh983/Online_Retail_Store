package com.retailstore.customer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CustomerResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private AddressDTO billingAddress;
    private AddressDTO shippingAddress;
}

