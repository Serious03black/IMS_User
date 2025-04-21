package com.mgt.controller;

import com.mgt.model.Customer;
import com.mgt.repository.CustomerRepo;
import com.mgt.serviceimpl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // allow Angular frontend
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private CustomerRepo customerRepo;

    @PostMapping("/customers")
    public String saveCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return "Invoice created successfully to database";
    }

    @GetMapping("/fetchAllBills")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/count")
    public Long countInvoice(){
        return customerRepo.count();
    }

}
