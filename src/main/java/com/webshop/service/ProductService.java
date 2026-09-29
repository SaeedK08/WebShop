package com.webshop.service;

import com.webshop.dto.ProductInfo;
import com.webshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    public static List<ProductInfo> getAllProducts() {
        List<Product> products = Product.getAllProducts();
        List<ProductInfo> dtoList = new ArrayList<>();
        for (Product p : products) {
            dtoList.add(new ProductInfo(p.getId(),
                            p.getName(),
                            p.getDescription(),
                            p.getPrice(),
                            p.getStock()));
        }
        return dtoList;
    }
}
