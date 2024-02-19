package org.example.XindusAssignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.XindusAssignment.model.Product;
import org.example.XindusAssignment.model.User;
import org.example.XindusAssignment.repository.IProductRepo;
import org.example.XindusAssignment.repository.IUserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductServiceTest {
    @MockBean
    private IProductRepo iProductRepo;

    @MockBean
    private IUserRepo iUserRepo;

    @Autowired
    private ProductService productService;

    /**
     * Method under test: {@link ProductService#getList(String, String)}
     */
    @Test
    void testGetList() {
        // Arrange
        ArrayList<Product> productList = new ArrayList<>();
        when(iProductRepo.findByUser(Mockito.<User>any())).thenReturn(productList);

        User user = new User();
        user.setUserEmail("jane.doe@example.org");
        user.setUserId(1);
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        user.setUserPhoneNumber("6625550144");
        when(iUserRepo.findByUserEmail(Mockito.<String>any())).thenReturn(user);

        // Act
        List<Product> actualList = productService.getList("iloveyou", "jane.doe@example.org");

        // Assert
        verify(iProductRepo).findByUser(Mockito.<User>any());
        verify(iUserRepo).findByUserEmail(eq("jane.doe@example.org"));
        assertTrue(actualList.isEmpty());
        assertSame(productList, actualList);
    }

    /**
     * Method under test: {@link ProductService#getList(String, String)}
     */
    @Test
    void testGetList2() {
        // Arrange
        User user = mock(User.class);
        when(user.getUserPassword()).thenReturn("foo");
        doNothing().when(user).setUserEmail(Mockito.<String>any());
        doNothing().when(user).setUserId(Mockito.<Integer>any());
        doNothing().when(user).setUserName(Mockito.<String>any());
        doNothing().when(user).setUserPassword(Mockito.<String>any());
        doNothing().when(user).setUserPhoneNumber(Mockito.<String>any());
        user.setUserEmail("jane.doe@example.org");
        user.setUserId(1);
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        user.setUserPhoneNumber("6625550144");
        when(iUserRepo.findByUserEmail(Mockito.<String>any())).thenReturn(user);

        // Act
        List<Product> actualList = productService.getList("iloveyou", "jane.doe@example.org");

        // Assert
        verify(user).getUserPassword();
        verify(user).setUserEmail(eq("jane.doe@example.org"));
        verify(user).setUserId(Mockito.<Integer>any());
        verify(user).setUserName(eq("janedoe"));
        verify(user).setUserPassword(eq("iloveyou"));
        verify(user).setUserPhoneNumber(eq("6625550144"));
        verify(iUserRepo).findByUserEmail(eq("jane.doe@example.org"));
        assertNull(actualList);
    }

    /**
     * Method under test: {@link ProductService#addItem(Product)}
     */
    @Test
    void testAddItem() {
        // Arrange
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
        when(iProductRepo.save(Mockito.<Product>any())).thenReturn(product);

        User user2 = new User();
        user2.setUserEmail("jane.doe@example.org");
        user2.setUserId(1);
        user2.setUserName("janedoe");
        user2.setUserPassword("iloveyou");
        user2.setUserPhoneNumber("6625550144");
        Optional<User> ofResult = Optional.of(user2);
        when(iUserRepo.findById(Mockito.<Integer>any())).thenReturn(ofResult);
        when(iUserRepo.existsById(Mockito.<Integer>any())).thenReturn(true);

        User user3 = new User();
        user3.setUserEmail("jane.doe@example.org");
        user3.setUserId(1);
        user3.setUserName("janedoe");
        user3.setUserPassword("iloveyou");
        user3.setUserPhoneNumber("6625550144");

        Product product2 = new Product();
        product2.setProductId(1);
        product2.setProductName("Product Name");
        product2.setUser(user3);

        // Act
        String actualAddItemResult = productService.addItem(product2);

        // Assert
        verify(iUserRepo).existsById(Mockito.<Integer>any());
        verify(iUserRepo).findById(Mockito.<Integer>any());
        verify(iProductRepo).save(Mockito.<Product>any());
        assertEquals("Item added to Wishlist", actualAddItemResult);
        assertEquals(user, product2.getUser());
    }

    /**
     * Method under test: {@link ProductService#addItem(Product)}
     */
    @Test
    void testAddItem2() {
        // Arrange
        when(iUserRepo.existsById(Mockito.<Integer>any())).thenReturn(false);

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

        // Act
        String actualAddItemResult = productService.addItem(product);

        // Assert
        verify(iUserRepo).existsById(Mockito.<Integer>any());
        assertEquals("User does not exists", actualAddItemResult);
    }

    /**
     * Method under test: {@link ProductService#deleteItem(Integer, String, String)}
     */
    @Test
    void testDeleteItem() {
        // Arrange
        doNothing().when(iProductRepo).deleteById(Mockito.<Integer>any());

        User user = new User();
        user.setUserEmail("jane.doe@example.org");
        user.setUserId(1);
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        user.setUserPhoneNumber("6625550144");
        when(iUserRepo.findByUserEmail(Mockito.<String>any())).thenReturn(user);

        // Act
        String actualDeleteItemResult = productService.deleteItem(1, "jane.doe@example.org", "iloveyou");

        // Assert
        verify(iUserRepo).findByUserEmail(eq("jane.doe@example.org"));
        verify(iProductRepo).deleteById(Mockito.<Integer>any());
        assertEquals("Product deleted", actualDeleteItemResult);
    }

    /**
     * Method under test: {@link ProductService#deleteItem(Integer, String, String)}
     */
    @Test
    void testDeleteItem2() {
        // Arrange
        User user = mock(User.class);
        when(user.getUserPassword()).thenReturn("foo");
        doNothing().when(user).setUserEmail(Mockito.<String>any());
        doNothing().when(user).setUserId(Mockito.<Integer>any());
        doNothing().when(user).setUserName(Mockito.<String>any());
        doNothing().when(user).setUserPassword(Mockito.<String>any());
        doNothing().when(user).setUserPhoneNumber(Mockito.<String>any());
        user.setUserEmail("jane.doe@example.org");
        user.setUserId(1);
        user.setUserName("janedoe");
        user.setUserPassword("iloveyou");
        user.setUserPhoneNumber("6625550144");
        when(iUserRepo.findByUserEmail(Mockito.<String>any())).thenReturn(user);

        // Act
        String actualDeleteItemResult = productService.deleteItem(1, "jane.doe@example.org", "iloveyou");

        // Assert
        verify(user).getUserPassword();
        verify(user).setUserEmail(eq("jane.doe@example.org"));
        verify(user).setUserId(Mockito.<Integer>any());
        verify(user).setUserName(eq("janedoe"));
        verify(user).setUserPassword(eq("iloveyou"));
        verify(user).setUserPhoneNumber(eq("6625550144"));
        verify(iUserRepo).findByUserEmail(eq("jane.doe@example.org"));
        assertEquals("Invalid User Password", actualDeleteItemResult);
    }
}
