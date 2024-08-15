package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.model.Item;

import java.util.List;

public interface ItemService {

    /**
     * Get item by Id
     */
    public Item getItemById(Long id);

    /**
     * Get all items
     */
    public List<Item> getAllItems();


    /**
     * Create items
     */
    public Item createItems(Item item);

    /**
     * Update item
     */
    public Item updateItem(Long id, Item item);

    /**
     * Delete item
     */
    public void deleteItem(Long id);
}
