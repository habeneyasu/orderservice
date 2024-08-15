package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.model.Item;
import com.ecommerce.orderservice.service.ItemService;
import com.ecommerce.orderservice.service.ItemServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/")
public class ItemContrller {

    @Autowired
    private ItemService itemService;

        /**
         * Testing hello world string message
         */
        @GetMapping("/testString")
        public String helloWorld(){
            return "Test items string.";
    }

    /**
     * Get item by id
     * @param id
     * @return
     */
    @GetMapping("/getItemById")
    public Item getItemById(@RequestParam("id") Long id){
        return itemService.getItemById(id);
    }

    /**
     * Get all items
     */
    @GetMapping("/getAllItems")
    public List<Item> getAllItems(){
        return itemService.getAllItems();
    }

    /**
     * Create items
     */
    @PostMapping("/createItem")
    public Item createItems(@RequestBody Item item){
        return itemService.createItems(item);
    }

    /**
     * Update item
     */
    @PutMapping("/updateItem")
    public Item updateItem(@RequestParam("id") Long id,@RequestBody Item item){
            return itemService.updateItem(id,item);
    }

    /**
     * Delete item
     */
    @DeleteMapping("/deleteItem")
    public void deleteItem(@RequestParam("id") Long id){
            itemService.deleteItem(id);
    }

}
