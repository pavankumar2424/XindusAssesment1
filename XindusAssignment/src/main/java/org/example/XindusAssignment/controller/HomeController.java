package org.example.XindusAssignment.controller;

import org.example.XindusAssignment.model.Product;
import org.example.XindusAssignment.model.User;
import org.example.XindusAssignment.service.ProductService;
import org.example.XindusAssignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class HomeController {

    //This is our controller class that contains various APIs for wishlists management
    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping("/wishlists")
    public List<Product> getList(@RequestParam String UserEmail,@RequestParam String UserPassword)
    {
       return productService.getList(UserPassword, UserEmail);
    }

    @PostMapping("/wishlists")
    public String addItem(@RequestBody Product product)
    {
        return productService.addItem(product);
    }


    //This api endpoint is to add user
    @PostMapping("/addUser")
    public String addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @DeleteMapping("/api/wishlists/{Id}")
    public String deleteItem(@PathVariable Integer Id, @RequestParam String UserEmail,@RequestParam String UserPassword)
    {
        return productService.deleteItem(Id, UserEmail, UserPassword);
    }


}
