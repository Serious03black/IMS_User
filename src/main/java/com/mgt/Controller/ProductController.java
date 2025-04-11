package com.mgt.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgt.Model.Product;
import com.mgt.Service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

  @Autowired
  private ProductService productService;

  @PostMapping(value = "/addProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, String>> addProduct(@RequestPart("product") String productJson,
      @RequestPart("product_image") MultipartFile productImage) {

    System.out.println("Received JSON: " + productJson); // Debugging Line

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

  @GetMapping("/getProduct/{product_id}")
  public ResponseEntity<Product> getProductById(@PathVariable("product_id") int id) {
    Product product = productService.getProductById(id);
    if (product != null) {
      return ResponseEntity.ok(product);
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }

  private static final String UPLOAD_DIR = "C:/MGT/uploads/";

  @GetMapping("/getImageByProductId/{product_id}")
  public ResponseEntity<Resource> getImageByProductId(@PathVariable("product_id") int productId) throws IOException {
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

  @GetMapping("/getProduct")
  public List<Product> getProduct() {
    return productService.getAllPro();
  }

  @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Product> updateProduct(@RequestPart("product") String productJson,
      @RequestPart(value = "product_image", required = false) MultipartFile image) {
    ObjectMapper objectMapper = new ObjectMapper();
    Product product;
    try {
      product = objectMapper.readValue(productJson, Product.class);
    } catch (JsonProcessingException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    Product updatedProduct = productService.updatePro(product, image);
    return ResponseEntity.ok(updatedProduct);
  }

  @DeleteMapping("/deleteProduct/{product_id}")
  public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable("product_id") Integer product_id) {
    boolean status = productService.deletePro(product_id);
    Map<String, String> response = new HashMap<>();
    if (status) {
      response.put("message", "Product deleted successfully.");
      return ResponseEntity.ok().body(response);
    } else {
      response.put("error", "Product not found.");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
  }

}
