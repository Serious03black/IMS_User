package com.mgt.service;

import com.mgt.model.Product;
import com.mgt.repository.ProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public boolean addPro(Product product, MultipartFile productImage) {
        try {
            // Store the image and get file path
            String filePath = storeImage(productImage);

            // Save file path to database
            product.setProduct_image(filePath);

            // Save product to database
            productRepo.save(product);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private String storeImage(MultipartFile file) throws IOException {
        String directory = "D:/MGT/uploads/"; // Change this path as needed
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs(); // Create directory if not exists
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String filePath = directory + fileName;

        // Save file to the server
        file.transferTo(new File(filePath));

        return filePath;
    }

    @Override
    public Product getProductById(int id) {
        return productRepo.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllPro() {
        return productRepo.findAll();
    }

    @Override
    @Transactional
    public boolean updatePro(Product product, MultipartFile productImage) {
        try {
            logger.info("Starting product update...");

            String filePath = storeImage(productImage);
            product.setProduct_image(filePath);
            productRepo.save(product);

            logger.info("Product updated successfully.");
            return true;
        } catch (DataAccessException e) {
            logger.error("Database error during product update", e);
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch (IOException e) {
            logger.error("File storage error during product update", e);
            throw new RuntimeException("File storage error: " + e.getMessage(), e);
        }
    }


    @Override
    public boolean deletePro(Integer product_id) {
        boolean status = false;
        if (product_id != null) {
            productRepo.deleteById(product_id);
            status = true;
        }
        return status;
    }


}
