package com.ecommerce.orderservice.service;


import com.ecommerce.orderservice.model.Item;
import com.ecommerce.orderservice.repository.ItemRepository;
import com.ecommerce.orderservice.util.ConfigSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImp implements ItemService{


    @Autowired
    private ItemRepository itemRepository;

    public Item getItemById(Long id){
    return itemRepository.findById(id).orElse(null);
    }

    /**
     * Get all items
     */
    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    /**
     * Create items
     */
    public Item createItems(Item item){
        return itemRepository.save(item);
    }

    /**
     * Update item
     */
    public Item updateItem(Long id, Item item){
        Item findItem=getItemById(id);
        if(findItem!=null){
            findItem.setName(item.getName());
            findItem.setQuantity(item.getQuantity());
            findItem.setUnitPrice(item.getUnitPrice());
            findItem.setTotalPrice(item.getTotalPrice());
            findItem.setDescription(item.getDescription());

            return itemRepository.save(findItem);
        }
        return null;
    }

    /**
     * Delete item
     */
    public void deleteItem(Long id){
        Item findItem=getItemById(id);
        if(findItem!=null){
        itemRepository.delete(findItem);
        }
    }
}
