package com.ivang.webshop.service;

import java.util.List;

import com.ivang.webshop.dto.ItemDTO;
import com.ivang.webshop.entity.Item;

public interface ItemServiceInterface {
    
    public Item findOne(Long id);
    public List<ItemDTO> findAll();
    public List<ItemDTO> findAllByOrder(Long id);
    public void save(ItemDTO item);
    public void update(ItemDTO item);
    public void remove(Long id);
}
