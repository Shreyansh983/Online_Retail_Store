package com.retailstore.customer.service;

import com.retailstore.customer.entity.Customer;
import com.retailstore.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long customerId){
        return customerRepository.findByCustomerId(customerId);
    }

    public Customer updateCustomer(Long customerId,Customer customer){
        Customer customer1 = getCustomerById(customerId);
        if(customer1!=null){
            customer1.setCustomerName(customer.getCustomerName());
            customer1.setCustomerEmail(customer.getCustomerEmail());
            customer1.setCustomerBillingAddress(customer.getCustomerBillingAddress());
            customer1.setCustomerShippingAddress(customer.getCustomerShippingAddress());
            return customerRepository.save(customer1);
        }
        return null;
    }

    public boolean deleteCustomer(Long customerId){
        Customer customer = getCustomerById(customerId);
        if (customer!=null){
            customerRepository.delete(customer);
            return true;
        }
        return false;
    }
}
