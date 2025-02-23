package com.mystore.app.rest;

import com.mystore.app.entity.Product;
import com.mystore.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product p = productService.addProduct(product);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) {
        Product p = productService.getProduct(id);
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Integer id, @RequestBody Product product) {
        Product p = productService.updateProduct(id, product);
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Integer id) {
        String message = productService.deleteProduct(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // API to search products by name
    @GetMapping("/search")
    public ResponseEntity<Object> getProductsByName(@RequestParam("name") String name){
        List<Product> products = productService.getProductsByName(name);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Product found against this name");
        }else{
            return ResponseEntity.ok(products);
        }
    }


    //  API to filter products by category
    @GetMapping("/filter/category")
    public ResponseEntity<Object> getProductsByCategory(@RequestParam("category") String category){
        List<Product> products = productService.getProductsByCategory(category);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Product found against this category");
        }else{
            return ResponseEntity.ok(products);
        }
    }

    //  API to filter products by price range
    @GetMapping("/filter/price")
    public ResponseEntity<Object> getProductsByPriceRange(@RequestParam("minPrice") Double minPrice, @RequestParam("maxPrice") Double maxPrice){
        List<Product> products = productService.getProductsByPriceRange(minPrice,maxPrice);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Product found against this price range");
        }else{
            return ResponseEntity.ok(products);
        }
    }

    //  API to filter products by stock quantity range

    @GetMapping("/filter/stock")
    public ResponseEntity<Object> getProductsByStockQuantity(@RequestParam("minStock") Double minStock, @RequestParam("maxStock") Double maxStock){
        List<Product> products = productService.getProductsByStockQuantity(minStock,maxStock);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No Product found against this stock quantity");
        }else{
            return ResponseEntity.ok(products);
        }
    }
}
