package org.example.XindusAssignment.controller;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.example.XindusAssignment.model.Product;
import org.example.XindusAssignment.model.User;
import org.example.XindusAssignment.service.ProductService;
import org.example.XindusAssignment.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {HomeController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class HomeControllerTest {
    @Autowired
    private HomeController homeController;

    @MockBean
    private ProductService productService;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link HomeController#getList(String, String)}
     */
    @Test
    void testGetList() throws Exception {
        // Arrange
        when(productService.getList(Mockito.<String>any(), Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/wishlists")
                .param("UserEmail", "foo")
                .param("UserPassword", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link HomeController#addItem(Product)}
     */
    @Test
    void testAddItem() throws Exception {
        // Arrange
        when(productService.addItem(Mockito.<Product>any())).thenReturn("Add Item");

        User user = new User();
        user.setUserEmail("jane.doe@example.org");
        user.setUserId(1);
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        user.setUserPhoneNumber("6625550144");

        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Product Name");
        product.setUser(user);
        String content = (new ObjectMapper()).writeValueAsString(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/wishlists")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Add Item"));
    }

    /**
     * Method under test: {@link HomeController#addUser(User)}
     */
    @Test
    void testAddUser() throws Exception {
        // Arrange
        when(userService.addUser(Mockito.<User>any())).thenReturn("Add User");

        User user = new User();
        user.setUserEmail("jane.doe@example.org");
        user.setUserId(1);
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        user.setUserPhoneNumber("6625550144");
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Add User"));
    }

    /**
     * Method under test: {@link HomeController#deleteItem(Integer, String, String)}
     */
    @Test
    void testDeleteItem() throws Exception {
        // Arrange
        when(productService.deleteItem(Mockito.<Integer>any(), Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn("Delete Item");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/api/wishlists/{Id}", 1)
                .param("UserEmail", "foo")
                .param("UserPassword", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(homeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Delete Item"));
    }
}
