package com.mgt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgt.model.Product;
import com.mgt.service.ProductService;
import com.mgt.service.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
   private ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


    @PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> addProduct(@RequestPart("product") String productJson,
                                                          @RequestPart("product_image") MultipartFile productImage) {

      //  System.out.println("Received JSON: " + productJson); // Debugging Line

        ObjectMapper objectMapper = new ObjectMapper();
        Product product;
        try {
            product = objectMapper.readValue(productJson, Product.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("message", "Invalid product JSON format"));
        }

        // Process product & image
        boolean status = productService.addPro(product, productImage);

        Map<String, String> response = new HashMap<String, String>();
        response.put("message", status ? "Product added successfully." : "Failed to add product.");
        return ResponseEntity.status(status ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(response);
    }

    // Get product details without image
    @GetMapping("/getProduct/{product_id}")
    public ResponseEntity<Product> getProductById(@PathVariable("product_id") Integer id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    private static final String UPLOAD_DIR = "D:/MGT/uploads/";

    // Get product image using product_id
    @GetMapping("/getImageByProductId/{product_id}")
    public ResponseEntity<Resource> getImageByProductId(@PathVariable("product_id") Integer productId) throws IOException {
        // Fetch the product from the database
        Product product = productService.getProductById(productId);

        if (product == null || product.getProduct_image() == null || product.getProduct_image().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Ensure the stored image path is only a filename, not a full path
        String imageName = Paths.get(product.getProduct_image()).getFileName().toString();

        // Construct the correct absolute path
        Path imagePath = Paths.get(UPLOAD_DIR, imageName).normalize(); // Normalize to fix path issues

        // Check if file exists
        Resource resource = new UrlResource(imagePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG) // Change dynamically if needed
                    .body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Get all product without image
    @GetMapping("/getProduct")
    public List<Product> getProduct() {
        return productService.getAllPro();
    }
/*
    @PutMapping("/updateProduct")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        boolean status = productService.updatePro(product);
        if (status) {
            return ResponseEntity.ok("Product updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update product. Please try again.");
        }
    }
*/
@PutMapping(value = "/updateProduct", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
public ResponseEntity<Map<String, String>> updateProduct(
        @RequestPart("product") String productJson,
        @RequestPart("product_image") MultipartFile productImage) {

    // Covert JSON to java object
    ObjectMapper objectMapper = new ObjectMapper();
    Product product;
    try {
        // Parse the productJson to a java object
        product = objectMapper.readValue(productJson, Product.class);
    } catch (JsonProcessingException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "Invalid product JSON format."));
    }

    try {
        boolean status = productService.updatePro(product, productImage);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Map.of("message", status ? "Product updated successfully." : "Failed to update product."));
    } catch (RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "Error updating product: " + e.getMessage()));
    }
}



    @DeleteMapping("/deleteProduct/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("product_id") Integer product_id) {
        boolean status = productService.deletePro(product_id);
        if (status) {
            return ResponseEntity.ok("Product deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete product. Please try again.");
        }
    }

}
