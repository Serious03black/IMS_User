package com.mgt.service;

import com.mgt.model.Product;

import java.util.List;

public interface ProductService {

    public boolean addPro(Product product);

    public List<Product> getAllPro();

    public boolean updatePro(Product product);

    public boolean deletePro(Integer product_id);
}
