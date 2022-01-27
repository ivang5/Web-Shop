package com.ivang.webshop.controller;

import java.util.List;

import com.ivang.webshop.dto.SaleDTO;
import com.ivang.webshop.entity.Sale;
import com.ivang.webshop.service.SaleServiceInterface;

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
@RequestMapping(value = "shop/sales")
@RequiredArgsConstructor
public class SaleController {
    
    private final SaleServiceInterface saleService;

    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return new ResponseEntity<List<SaleDTO>>(saleService.findAll(), HttpStatus.OK);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleDTO> getItemById(@PathVariable("id") Long id) {
        Sale sale = saleService.findOne(id);
		
		if(sale == null) {
            return new ResponseEntity<SaleDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<SaleDTO>(new SaleDTO(sale), HttpStatus.OK);
	}
    
    @GetMapping(value = "/seller/{id}")
    public ResponseEntity<List<SaleDTO>> getSalesBySeller(@PathVariable("id") Long id) {
        return new ResponseEntity<List<SaleDTO>>(saleService.findBySeller(id), HttpStatus.OK);
    }

    @GetMapping(value = "/product/{id}")
    public ResponseEntity<List<SaleDTO>> getSalesByProduct(@PathVariable("id") Long id) {
        return new ResponseEntity<List<SaleDTO>>(saleService.findByProduct(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createSale(@RequestBody SaleDTO sale) {
        try {
            saleService.save(sale);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateSale(@RequestBody SaleDTO sale) {
        try {
            saleService.update(sale);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSale(@PathVariable Long id) {
        Sale sale = saleService.findOne(id);

        if (sale != null) {
            saleService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
