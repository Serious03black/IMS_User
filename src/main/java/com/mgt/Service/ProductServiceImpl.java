package com.mgt.Service;

import com.mgt.Model.Product;
import com.mgt.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;

	public Product getProductById(int id) {
		return productRepo.findById(id).orElse(null);
	}

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

	public List<Product> getAllPro() {
		return productRepo.findAll();
	}

	public boolean updatePro(Product product) {
		boolean status = false;

		try {
			productRepo.save(product);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}

		return status;
	}

	public boolean deletePro(Integer product_id) {
		boolean status = false;
		if (product_id != null) {
			productRepo.deleteById(product_id);
			status = true;
		}
		return status;
	}
}
