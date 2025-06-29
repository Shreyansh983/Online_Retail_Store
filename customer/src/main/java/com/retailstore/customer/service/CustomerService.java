package com.retailstore.customer.service;

import com.retailstore.customer.Exception.CustomerNotFoundException;
import com.retailstore.customer.Exception.ResourceIsAlreadyThereException;
import com.retailstore.customer.dto.CustomerRequestDTO;
import com.retailstore.customer.dto.CustomerResponseDTO;
import com.retailstore.customer.entity.Customer;
import com.retailstore.customer.mapper.CustomerMapper;
import com.retailstore.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    private final CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Transactional(rollbackFor = Exception.class)
    public CustomerResponseDTO saveCustomer(CustomerRequestDTO customerRequestDTO) throws ResourceIsAlreadyThereException {
        String email = customerRequestDTO.getEmail();
        log.info("Attempting to create customer with email: {}", email);
        Customer isCustomerExistWithEmailAndPhoneNumber = customerRepository.findByEmail(email);
        if (isCustomerExistWithEmailAndPhoneNumber != null) {
            log.error("Customer with email: {} already exists", email);
            throw new ResourceIsAlreadyThereException("Customer with email: " + email + " already exists");
        }
        Customer customer = customerMapper.customerRequestDTOToCustomer(customerRequestDTO);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Successfully created customer with id: {}", savedCustomer.getId());
        return customerMapper.customerToCustomerResponseDTO(savedCustomer);
    }

    public CustomerResponseDTO getCustomerById(Long customerId) throws CustomerNotFoundException {
        log.info("Searching for customer with id: {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.error("Customer not found with id: {}", customerId);
                    return new CustomerNotFoundException("Customer not found with id: " + customerId);
                });
        log.info("Found customer with id: {}", customerId);
        return customerMapper.customerToCustomerResponseDTO(customer);
    }

    public List<CustomerResponseDTO> getCustomerByEmailAndPhoneNumber(String email, String phoneNumber) throws CustomerNotFoundException {
        log.info("Searching for customer with email: {} and phone number: {}", email, phoneNumber);
        List<CustomerResponseDTO> customerResponseDTOS = new ArrayList<>();
        List<Customer> customers = customerRepository.findByEmailAndPhoneNumber(email, phoneNumber);
        if (customers.isEmpty()) {
            log.error("Customer not found with email: {} and phone number: {}", email, phoneNumber);
            throw new CustomerNotFoundException("Customer not found with email: " + email + " and phone number: " + phoneNumber);
        }
        log.info("Found customer with email: {} and phone number: {}", email, phoneNumber);
        customers.forEach(customer -> {
            customerResponseDTOS.add(customerMapper.customerToCustomerResponseDTO(customer));
        });
        return customerResponseDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public CustomerResponseDTO updateCustomer(Long customerId, CustomerRequestDTO customerRequestDTO) throws CustomerNotFoundException {
        log.info("Attempting to update customer with id: {}", customerId);
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> {
                    log.error("Customer not found with id : {}", customerId);
                    return new CustomerNotFoundException("Customer not found with id: " + customerId);
                });

        Customer updatedCustomer = customerMapper.customerRequestDTOToCustomer(customerRequestDTO);
        updatedCustomer.setId(existingCustomer.getId());

        Customer savedCustomer = customerRepository.save(updatedCustomer);
        log.info("Successfully updated customer with id: {}", customerId);
        return customerMapper.customerToCustomerResponseDTO(savedCustomer);
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean deleteCustomer(Long customerId) throws CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            log.info("Customer not found with id: {}", customerId);
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        customerRepository.deleteById(customerId);
        return true;
    }
}
