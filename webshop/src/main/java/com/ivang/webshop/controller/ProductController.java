package com.ivang.webshop.controller;

import java.util.List;

import com.ivang.webshop.dto.ProductDTO;
import com.ivang.webshop.entity.Product;
import com.ivang.webshop.service.ProductServiceInterface;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping(value = "shop/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductServiceInterface productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return new ResponseEntity<List<ProductDTO>>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/active")
    public ResponseEntity<List<ProductDTO>> getAllActiveProducts() {
        return new ResponseEntity<List<ProductDTO>>(productService.findByActiveSellers(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        Product product = productService.findOne(id);
		
		if(product == null) {
            return new ResponseEntity<ProductDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProductDTO>(new ProductDTO(product), HttpStatus.OK);
	}
    
    @GetMapping(value= "/seller/{username}")
    public ResponseEntity<List<ProductDTO>> getProductsBySeller(@PathVariable("username") String username) {
        return new ResponseEntity<List<ProductDTO>>(productService.findBySeller(username), HttpStatus.OK);
    }

    @GetMapping(value = "/sale/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsBySale(@PathVariable("id") Long id) {
		return new ResponseEntity<List<ProductDTO>>(productService.findBySale(id), HttpStatus.OK);
	}

    @GetMapping(value = "/name/order/{id}")
    public ResponseEntity<List<String>> getNamesByOrder(@PathVariable("id") Long id) {
		return new ResponseEntity<List<String>>(productService.getNamesByOrder(id), HttpStatus.OK);
	}

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO product) {
        try {
            productService.save(product);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO product) {
        try {
            productService.update(product);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productService.findOne(id);

        if (product != null) {
            productService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
