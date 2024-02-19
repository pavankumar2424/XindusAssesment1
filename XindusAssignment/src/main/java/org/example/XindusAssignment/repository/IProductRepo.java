package org.example.XindusAssignment.repository;

import org.example.XindusAssignment.model.Product;
import org.example.XindusAssignment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByUser(User user);
}
