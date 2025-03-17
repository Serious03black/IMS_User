package com.mgt.Service;

import com.mgt.Model.Product;
import com.mgt.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepo productRepo;

    @Override
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

    @Override
    public List<Product> getAllPro() {
        return productRepo.findAll();
    }

    @Override
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

    @Override
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
