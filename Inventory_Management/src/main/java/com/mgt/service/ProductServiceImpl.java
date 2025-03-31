package com.mgt.service;

import com.mgt.model.Product;
import com.mgt.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl {

    @Autowired
    private ProductRepo productRepo;

    public boolean addPro(Product product) {
       boolean status = false;

       if(product != null){
           productRepo.save(product);
           status = true;
       }else{
           status = false;
       }
       return status;
    }


    public List<Product> getAllPro() {
        return productRepo.findAll();
    }


    public boolean updatePro(Product product) {
        boolean status = false;

        try{
            productRepo.save(product);
            status=true;
        }catch(Exception e){
            e.printStackTrace();
            status=false;
        }

        return status;
    }


    public boolean deletePro(Integer product_id) {
        boolean status = false;
        if(product_id != null){
            productRepo.deleteById(product_id);
            status = true;
        }else {
            status = false;
        }
        return status;
    }


}
