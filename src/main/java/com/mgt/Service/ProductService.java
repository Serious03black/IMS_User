package com.mgt.Service;

import com.mgt.Model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
	public boolean addPro(Product product, MultipartFile imageFile);

	public List<Product> getAllPro();

	public Product updatePro(Product product, MultipartFile image);

	public boolean deletePro(Integer product_id);

	public Product getProductById(int id);

}
