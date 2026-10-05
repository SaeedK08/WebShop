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
                            p.getStock(),
                            p.getCategoryId(),
                            p.getCategoryName()));
        }
        return dtoList;
    }

    public static ProductInfo getProductById(int productId) {
        Product product = Product.getProductById(productId);
        return new ProductInfo(product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice(),
                                product.getStock(),
                                product.getCategoryId(),
                                product.getCategoryName());
    }

    public static boolean createProduct(ProductInfo pi) {
        return Product.create(pi.getName(), pi.getDescription(), pi.getPrice(), pi.getStock(), pi.getCategoryId());
    }
    public static boolean updateProduct(ProductInfo pi) {
        if (pi.stock() <= 0) return false;
        return Product.update(pi.getId(), pi.getName(), pi.getDescription(),
                pi.getPrice(), pi.getStock(), pi.getCategoryId());
    }
    public static boolean updateProductStock(int productId, int stock) {
        if (stock <= 0) return false;
        return Product.updateStock(productId, stock);
    }
    public static boolean deleteProduct(int productId) {
        return Product.delete(productId);
    }

}
