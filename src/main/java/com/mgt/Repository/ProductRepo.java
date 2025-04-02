package com.mgt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.mgt.Model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
