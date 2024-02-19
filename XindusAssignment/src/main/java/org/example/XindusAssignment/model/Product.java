package org.example.XindusAssignment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

//This is the product class which will contain detail about product along with what user it is related to
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    private String productName;

    @ManyToOne
    @JoinColumn(name = "user_Id")
    private User user;

}
