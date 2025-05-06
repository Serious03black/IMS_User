package com.mgt.controller;

import com.mgt.jwtServices.JwtService;
import com.mgt.model.Customer;
import com.mgt.model.CustomerProduct;
import com.mgt.model.Product;
import com.mgt.model.User;
import com.mgt.repository.CustomerProductRepo;
import com.mgt.repository.CustomerRepo;
import com.mgt.repository.ProductRepo;
import com.mgt.repository.UserRepo;
import com.mgt.serviceimpl.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200") // allow Angular frontend
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepo userRepo; 

    @Autowired
    private CustomerProductRepo customerProductRepo;

    @Autowired
    private ProductRepo productRepo;


    @PostMapping("/customers")
    public String saveCustomer(@RequestBody Customer customer) {
        customerService.saveCustomer(customer);
        return "Invoice created successfully to database";
    }

    
    @PostMapping("/addBill")
    public ResponseEntity<Map<String, String>> saveCustomer(
            @RequestBody Customer customer,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
    
        System.out.println("Received Authorization Header: " + authHeader);
    
        try {
            // Validate Authorization Header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("message", "Missing or invalid Authorization header"));
            }
    
            // Extract User ID from JWT Token
            String token = authHeader.substring(7);
            Long userId = jwtService.extractUserId(token); // Ensure jwtService has this method
    
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("message", "Invalid JWT token"));
            }


            // Fetch Authenticated User
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
    
            // Associate user with customer
            customer.setUser(user);
    
            // Save customer and customer products
            customerService.saveCustomer(customer);

            // Handle subarray: Update stock quantities for products
            List<CustomerProduct> products = customer.getCustomerProductList();

            if (products != null && !products.isEmpty()) {
                for (CustomerProduct item : products) {
                    String productName = item.getName();
                    int quantity = item.getQuantity();

                    System.out.println("Product Name: " + productName);
                    System.out.println("Product Quantity: " + quantity);

                    Optional<Product> optionalProduct = productRepo.findByProductName(productName);

                    if (optionalProduct.isPresent()) {
                        Product product = optionalProduct.get();
                        int currentStock = product.getProduct_available_stock_quantity();
                        System.out.println("Current Stock: " + currentStock);

                        product.setProduct_available_stock_quantity(currentStock - quantity);
                        productRepo.save(product);

                        System.out.println("Updated stock for " + productName);
                    } else {
                        System.out.println("Product not found with name: " + productName);
                        // Optionally: throw new RuntimeException("Product not found: " + productName);
                    }
                }
            } else {
                System.out.println("Product list is null or empty");
            }
    
            return ResponseEntity.ok(Collections.singletonMap("message", "Invoice created successfully"));
    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("message", "Error saving invoice: " + e.getMessage()));
        }
    }
    

@GetMapping("/fetchAllBillsByUser")
public ResponseEntity<?> getBillsByUserId(@RequestHeader(value = "Authorization", required = false) String authHeader) {
    try {
        // Validate Authorization Header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Missing or invalid Authorization header"));
        }

        // Extract user ID from JWT
        String token = authHeader.substring(7);
        Long userId = jwtService.extractUserId(token);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Invalid JWT token"));
        }

        // Fetch customer bills for this user
        List<Customer> bills = customerRepo.findByUserId(userId);
        return ResponseEntity.ok(bills);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("message", "Error fetching bills: " + e.getMessage()));
    }
}


    @GetMapping("/customers")
    public Customer getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/count")
    public Long countInvoice() {
        return customerRepo.count();
    }





}
