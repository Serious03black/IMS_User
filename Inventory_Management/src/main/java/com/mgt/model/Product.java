package com.mgt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer product_id;

    @Column(name="name")
    private String product_name;

    @Column(name="category")
    private String product_category;

    @Column(name="stock_quantity")
    private Integer product_available_stock_quantity;

    @Column(name="description")
    private String product_description;

    @Column(name="price")
    private Float product_price;

}
