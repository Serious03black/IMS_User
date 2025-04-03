package com.mgt.service;

import com.mgt.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    boolean addPro(Product product, MultipartFile imageFile);

    List<Product> getAllPro();

    boolean updatePro(Product product , MultipartFile productImage);

    boolean deletePro(Integer product_id);

    Product getProductById(int id);
}
