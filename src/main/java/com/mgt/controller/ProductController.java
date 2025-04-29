package com.mgt.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import com.mgt.jwtServices.JwtService;
import com.mgt.model.User;
import com.mgt.repository.ProductRepo;
import com.mgt.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.mgt.model.Product;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private JwtService jwtService;

    private final String uploadDir = "uploads/products/";

    @PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProductWithImage(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam("name") String name,
            @RequestParam(value = "price", required = false) Float price,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "gstType", required = false) String gstType,
            @RequestParam(value = "gstRate", required = false) Float gstRate,
            @RequestParam("imageFile") MultipartFile imageFile
    ) {
        try {
            // Validate authorization header
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Missing or invalid Authorization header");
            }

            String token = authorizationHeader.substring(7);
            Long userId = jwtService.extractUserId(token);
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid JWT token");
            }

            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (imageFile == null || imageFile.isEmpty()) {
                return ResponseEntity.badRequest().body("Image file is required");
            }

            // Save image
            Files.createDirectories(Paths.get(uploadDir));
            String filename = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path filepath = Paths.get(uploadDir, filename);
            Files.copy(imageFile.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

            // Build and save product
            Product product = new Product();
            product.setProduct_name(name);
            product.setProduct_price(price);
            product.setProduct_category(category);
            product.setProduct_available_stock_quantity(quantity);
            product.setProduct_description(description);
            product.setGst_type(gstType);
            product.setGst_rate(gstRate);
            product.setProduct_image(filepath.toString());
            product.setUser(user);

            Product savedProduct = productRepo.save(product);
            return ResponseEntity.ok(savedProduct);

        } catch (Exception e) {
            e.printStackTrace(); // log to console or use logger
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while creating product: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
}
