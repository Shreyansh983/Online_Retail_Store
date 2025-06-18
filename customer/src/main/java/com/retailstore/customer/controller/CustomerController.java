package com.retailstore.customer.controller;

import com.retailstore.customer.Exception.CustomerNotThereException;
import com.retailstore.customer.entity.Customer;
import com.retailstore.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api1")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer){
        Customer customer1 = customerService.saveCustomer(customer);
        return  new ResponseEntity<>(customer1, HttpStatusCode.valueOf(201));
    }

    @GetMapping("/searchCustomer/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId) throws CustomerNotThereException {
        Customer customer = customerService.getCustomerById(customerId);
        if(customer!=null)
             return  new ResponseEntity<>(customer, HttpStatusCode.valueOf(200));
        else
            throw new CustomerNotThereException();
    }

    @PutMapping("/updateCustomer/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId,@RequestBody Customer customer) throws CustomerNotThereException {
        Customer customer1 =  customerService.updateCustomer(customerId,customer);
        if(customer!=null)
            return  new ResponseEntity<>(customer1, HttpStatusCode.valueOf(200));
        else
            throw new CustomerNotThereException();
    }

    @DeleteMapping("/deleteCustomer/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId){
        boolean result = customerService.deleteCustomer(customerId);
        if(result)
            return "Deleted Successfully";
        else
            return "Not Deleted Successfully";
    }
}
