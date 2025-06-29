package com.retailstore.customer.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AddressDTO {
    private String street;
    private String city;
    private String state;
    
    @Pattern(regexp = "^[0-9]{6}$", message = "Postal code must be exactly 6 digits")
    private String postalCode;
    private String country;
}

