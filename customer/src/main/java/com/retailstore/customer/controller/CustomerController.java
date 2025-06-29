package com.retailstore.customer.controller;

import com.retailstore.customer.Exception.CustomerNotFoundException;
import com.retailstore.customer.Exception.ResourceIsAlreadyThereException;
import com.retailstore.customer.dto.CustomerRequestDTO;
import com.retailstore.customer.dto.CustomerResponseDTO;
import com.retailstore.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/customers/v1")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<?> saveCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO){
        try {
            CustomerResponseDTO customerResponseDTO = customerService.saveCustomer(customerRequestDTO);
            log.info("Customer created successfully with email: {}", customerRequestDTO.getEmail());
            return new ResponseEntity<>(customerResponseDTO, HttpStatusCode.valueOf(201));
        } catch (ResourceIsAlreadyThereException e) {
            log.error("Customer already exists with email: {}", customerRequestDTO.getEmail());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(409));
        } catch (Exception e) {
            log.error("Error creating customer: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/searchCustomer/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId) {
        try {
            CustomerResponseDTO customerResponseDTO = customerService.getCustomerById(customerId);
            log.info("Customer found with id: {}", customerId);
            return new ResponseEntity<>(customerResponseDTO, HttpStatusCode.valueOf(200));
        } catch (CustomerNotFoundException e) {
            log.error("Customer not found with id: {}", customerId);
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            log.error("Error finding customer: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @GetMapping("/searchCustomer")
    public ResponseEntity<?> getCustomerByEmailAndPhoneNumber(@RequestParam Map<String,String> map) {
        String email = map.get("email");
        String phoneNumber = map.get("phoneNumber");
        try {
            List<CustomerResponseDTO> customerResponseDTOs = customerService.getCustomerByEmailAndPhoneNumber(email, phoneNumber);
            log.info("Customer found with email: {} and phone number: {}", email, phoneNumber);
            return new ResponseEntity<>(customerResponseDTOs, HttpStatusCode.valueOf(200));
        } catch (CustomerNotFoundException e) {
            log.error("Customer not found with email: {} and phone number: {}", email, phoneNumber);
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            log.error("Error finding customer : {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @PutMapping("/updateCustomer/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId, @RequestBody @Valid CustomerRequestDTO customerRequestDTO) throws CustomerNotFoundException {
        try {
            CustomerResponseDTO customerResponseDTO = customerService.updateCustomer(customerId, customerRequestDTO);
            log.info("Customer updated successfully with id: {}", customerId);
            return new ResponseEntity<>(customerResponseDTO, HttpStatusCode.valueOf(200));
        } catch (CustomerNotFoundException e) {
            log.error("Customer not found with id : {}", customerId);
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            log.error("Error updating customer : {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId){
        try {
            boolean result = customerService.deleteCustomer(customerId);
            log.info("Customer deleted successfully with id: {}", customerId);
            return new ResponseEntity<>("Customer Deleted Successfully", HttpStatusCode.valueOf(204));
        } catch (CustomerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(404));
        } catch (Exception e) {
            log.error("Error deleting customer: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(400));
        }
    }
}
