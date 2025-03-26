package com.mgt.Service;

import com.mgt.Model.Product;

import java.util.List;

public interface ProductService {

    public boolean addPro(Product product);

    public List<Product> getAllPro();

    public boolean updatePro(Product product);

    public boolean deletePro(Integer product_id);
}
