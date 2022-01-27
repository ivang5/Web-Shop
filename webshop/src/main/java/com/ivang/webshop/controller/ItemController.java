package com.ivang.webshop.controller;

import java.util.List;

import com.ivang.webshop.dto.ItemDTO;
import com.ivang.webshop.entity.Item;
import com.ivang.webshop.service.ItemServiceInterface;

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
@RequestMapping(value = "shop/items")
@RequiredArgsConstructor
public class ItemController {
    
    private final ItemServiceInterface itemService;

    @GetMapping
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return new ResponseEntity<List<ItemDTO>>(itemService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemDTO> getItemById(@PathVariable("id") Long id) {
		Item item = itemService.findOne(id);
		
		if(item == null) {
			return new ResponseEntity<ItemDTO>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ItemDTO>(new ItemDTO(item), HttpStatus.OK);
	}

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody ItemDTO item) {
        try {
            itemService.save(item);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateItem(@RequestBody ItemDTO item) {
        try {
            itemService.update(item);
        } catch (Exception e) {
            return new ResponseEntity<String>("Bad request, something went wrong", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        Item item = itemService.findOne(id);

        if (item != null) {
            itemService.remove(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
