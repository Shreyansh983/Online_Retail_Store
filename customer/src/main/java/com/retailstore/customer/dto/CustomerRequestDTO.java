package com.retailstore.customer.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CustomerRequestDTO {

    @NotBlank
    private String firstName;

    private String lastName;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^[0-9\\-\\+]{10,15}$")
    private String phoneNumber;

    @Valid
    private AddressDTO billingAddress;

    @Valid
    private AddressDTO shippingAddress;
}

