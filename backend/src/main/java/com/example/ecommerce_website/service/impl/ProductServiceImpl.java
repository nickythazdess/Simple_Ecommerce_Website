package com.example.ecommerce_website.service.impl;

import com.example.ecommerce_website.entity.Product;
import com.example.ecommerce_website.repository.ProductRepository;
import com.example.ecommerce_website.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository repo;

    public void setProductRepo(ProductRepository repo) { this.repo = repo; }

    public List<Product> getProductList() { return repo.findAll(); }

    public Optional<Product> getProduct(Long id) { return repo.findById(id); }

    public Product getProductByName(String name) { return repo.findProductByName(name); }

    public Product getProductByDev(String dev) { return repo.findProductByDev(dev); }

    public Product saveProduct(Product product) { return repo.save(product); }

    public void deleteProduct(Long id) { repo.delete(getProduct(id).get()); }

    public void updateProduct(Product product) { repo.save(product); }
}