package org.example.XindusAssignment.service;

import org.example.XindusAssignment.model.Product;
import org.example.XindusAssignment.model.User;
import org.example.XindusAssignment.repository.IProductRepo;
import org.example.XindusAssignment.repository.IUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    IProductRepo productRepo;

    @Autowired
    IUserRepo userRepo;

    public List<Product> getList(String UserPassword, String UserEmail) {
        User user = userRepo.findByUserEmail(UserEmail);
        if(user == null)
            return null;

        if(!user.getUserPassword().equals(UserPassword))
            return null;

        return productRepo.findByUser(user);
    }

    public String addItem(Product product) {
        if(!userRepo.existsById(product.getUser().getUserId()))
        {
            return "User does not exists";
        }

        User user = userRepo.findById(product.getUser().getUserId()).orElse(null);

        product.setUser(user);

        productRepo.save(product);

        return "Item added to Wishlist";
    }

    public String deleteItem(Integer id, String userEmail, String userPassword) {
        User user = userRepo.findByUserEmail(userEmail);
        if(user == null)
            return "Invalid User Email";

        if(!user.getUserPassword().equals(userPassword))
            return "Invalid User Password";

        productRepo.deleteById(id);

        return "Product deleted";
    }
}
