package com.mgt.Service;

import com.mgt.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
	boolean addPro(Product product, MultipartFile imageFile);

	List<Product> getAllPro();

	boolean updatePro(Product product);

	boolean deletePro(Integer product_id);

	Product getProductById(int id);
	
}
