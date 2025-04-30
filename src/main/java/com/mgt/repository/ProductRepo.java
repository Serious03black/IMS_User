package com.mgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mgt.model.Product;

import java.util.List;



@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    List<Product> findByUserId(Long userId);



}
