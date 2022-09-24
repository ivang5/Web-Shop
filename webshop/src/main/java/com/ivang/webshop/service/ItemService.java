package com.ivang.webshop.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.ivang.webshop.dto.ItemDTO;
import com.ivang.webshop.entity.Item;
import com.ivang.webshop.repository.ItemRepository;
import com.ivang.webshop.repository.OrderRepository;
import com.ivang.webshop.repository.ProductRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ItemService implements ItemServiceInterface {

    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public Item findOne(Long id) {
        log.info("Fetching item {}", id);
        return itemRepository.getById(id);
    }
    
    @Override
    public List<ItemDTO> findAll() {
        log.info("Fetching all items");
        List<ItemDTO> itemsDTO = new ArrayList<ItemDTO>();

		for(Item i : itemRepository.findAll()) {
			itemsDTO.add(new ItemDTO(i));
		}
        
		return itemsDTO;
    }

    @Override
    public List<ItemDTO> findAllByOrder(Long id) {
        log.info("Fetching all items for order {}", id);
        List<ItemDTO> itemsDTO = new ArrayList<ItemDTO>();

		for(Item i : itemRepository.getByOrder(id)) {
			itemsDTO.add(new ItemDTO(i));
		}
        
		return itemsDTO;
    }

    @Override
    public void save(ItemDTO itemDTO) {
        log.info("Saving new item {} to the database", itemDTO.getId());
        Item item = new Item();
        populateItem(item, itemDTO);
    }

    @Override
    public void update(ItemDTO itemDTO) {
        log.info("Updating item {}", itemDTO.getId());
        Item item = itemRepository.getById(itemDTO.getId());

        if (item != null) {
            populateItem(item, itemDTO);
        }
    }

    @Override
    public void remove(Long id) {
        log.info("Removing item {}", id);
        itemRepository.deleteById(id);
    }

    private void populateItem(Item item, ItemDTO itemDTO) {
        item.setQuantity(itemDTO.getQuantity());
        item.setProduct(productRepository.getById(itemDTO.getProduct().getId()));
        item.setOrder(orderRepository.getById(itemDTO.getOrder().getId()));
        
        itemRepository.save(item);
    }
}
