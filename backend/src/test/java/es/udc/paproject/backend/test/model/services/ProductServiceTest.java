package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ProductService;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ProductServiceTest {
    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    private User signUpUser(String userName) {

        User user = new User(userName, "password", "firstName", "lastName",
                userName + "@" + userName + ".com");

        try {
            userService.signUp(user);
        } catch (DuplicateInstanceException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Test
    void testAddProduct() throws InstanceNotFoundException {
        Category category1 = new Category("Pelicula");
        categoryDao.save(category1);

        User user1 = signUpUser("Pedro");

        Product product = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(24.95F),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), user1.getId());
        productDao.save(product);

        Optional<Product> product2 = productDao.findByUserId(user1.getId());

        assertEquals(product, product2.get());

        assertEquals(product.getId(), product2.get().getId());
        assertEquals(product.getName(), product2.get().getName());
        assertEquals(product.getDescription(), product2.get().getDescription());
        assertEquals(product.getFinishingDate(), product2.get().getFinishingDate());
        assertEquals(product.getStartingDate(), product2.get().getStartingDate());
        assertEquals(product.getActualPrice(), product2.get().getActualPrice());
        assertEquals(product.getDeliveryInformation(), product2.get().getDeliveryInformation());
        assertEquals(product.getCategory(), product2.get().getCategory());
        assertEquals(product.getUser(), product2.get().getUser());

    }

    @Test
    public void testAddProductWithCategoryNotExistentId() {
        User user1 = signUpUser("Pedro");

        assertThrows(
                InstanceNotFoundException.class, () ->
                        productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                                45, BigDecimal.valueOf(24.95F),
                                "envío por codigo postal, con plazo de entrega de una semana",
                                NON_EXISTENT_ID, user1.getId()));

    }

    // Comienzo Tests FUNC-3 Ver la información detallada de un producto
    @Test
    public void testFindProductById() throws InstanceNotFoundException {
        User user = signUpUser("Jaime");

        Category category = new Category("Videojuego");
        categoryDao.save(category);

        Product product = productService.addProduct("Bomberman", "Videojuego Bomberman para PS1. " +
                        "Se encuentra en perfecto estado.", 60, BigDecimal.valueOf(15),
                "Envío por código postal, con plazo de entrega de dos semanas.", category.getId(), user.getId());
        productDao.save(product);

        assertEquals(product, productService.findProductById(product.getId()));
    }

    @Test
    public void testFindProductByNonExistentId() {
        assertThrows(InstanceNotFoundException.class, () -> productService.findProductById(NON_EXISTENT_ID));
    }
    // Fin Tests FUNC-3 Ver la información detallada de un producto

    //Tests FUNC-2
    @Test
    public void testFindAllCategories() {
        Category c1 = new Category("category1");
        Category c2 = new Category("category2");

        categoryDao.save(c2);
        categoryDao.save(c1);

        assertEquals(Arrays.asList(c1, c2), productService.findAllCategories());

    }

    @Test
    public void testFindProductsByKeywords() throws InstanceNotFoundException {
        User user1 = signUpUser("Pedro");

        Category c = new Category("category");
        categoryDao.save(c);

        Product p1 = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(24.95F),
                "envío por codigo postal, con plazo de entrega de una semana",
                c.getId(), user1.getId());
        Product p2 = productService.addProduct("Nike Jordan 2", "Jordan",
                50, BigDecimal.valueOf(300.00),
                "envío por codigo postal, con plazo de entrega de una semana",
                c.getId(), user1.getId());
        Product p3 = productService.addProduct("Shrek 2", "Shrek 2, Edición Coleccionista",
                55, BigDecimal.valueOf(19.95),
                "envío por codigo postal, con plazo de entrega de una semana",
                c.getId(), user1.getId());

        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);

        Block<Product> expectedBlock = new Block<>(Arrays.asList(p2, p3), false);

        assertEquals(expectedBlock, productService.findProducts(null, "2", 0, 2));

    }

    @Test
    public void testFindProductsByCategory() throws InstanceNotFoundException {
        User user1 = signUpUser("Pedro");

        Category c1 = new Category("category1");
        Category c2 = new Category("category2");
        categoryDao.save(c1);
        categoryDao.save(c2);

        Product p1 = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(24.95F),
                "envío por codigo postal, con plazo de entrega de una semana",
                c1.getId(), user1.getId());
        Product p2 = productService.addProduct("Nike Jordan 2", "Jordan",
                50, BigDecimal.valueOf(300.00),
                "envío por codigo postal, con plazo de entrega de una semana",
                c2.getId(), user1.getId());


        productDao.save(p1);
        productDao.save(p2);

        Block<Product> expectedBlock = new Block<>(Arrays.asList(p1), false);

        assertEquals(expectedBlock, productService.findProducts(c1.getId(), null, 0, 1));

    }

    @Test
    public void testFindProductByUserId() throws InstanceNotFoundException {
        User user1 = signUpUser("Andrea");
        User user2 = signUpUser("Pepe");

        Category c1 = new Category("Pelicula");
        Category c2 = new Category("Videojuego");
        categoryDao.save(c1);
        categoryDao.save(c2);

        Product p1 = productService.addProduct("Bomberman", "Videojuego Bomberman para PS1. " +
                        "Se encuentra en perfecto estado.",
                30, BigDecimal.valueOf(15),
                "Envío por código postal, con plazo de entrega de dos semanas.",
                c2.getId(), user1.getId());

        Product p2 = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(24.95F),
                "Envío por código postal, con plazo de entrega de tres semanas.",
                c1.getId(),
                user2.getId());
        Product p3 = productService.addProduct("Grand Theft Auto: San Andreas",
                "Videojuego Grand Theft Auto: San Andreas para PS2. Se encuentra en perfecto estado",
                60, BigDecimal.valueOf(30.00),
                "Envío por código postal, con plazo de entrega de una semana.",
                c2.getId(), user1.getId());

        Product p4 = productService.addProduct("FIFA 98",
                "Videojuego FIFA 98 para PS1. Se encuentra en perfecto estado",
                0, BigDecimal.valueOf(50.00),
                "Envío por código postal, con plazo de entrega de una semana.",
                c2.getId(),
                user1.getId());
        p4.setFinishingDate(LocalDateTime.now().minusMinutes(60));

        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);
        productDao.save(p4);

        //tiene que enseñar primero el producto p3, ya que queda 1h para que acabe
        //mientras que para que acabe el producto p1 quedan 30 mins
        //y el p4 ya habia acabado hace 1h
        Block<Product> expectedBlock = new Block<>(Arrays.asList(p3, p1), true);
        Block<Product> expectedBlock2 = new Block<>(Arrays.asList(p4), false);
        assertEquals(expectedBlock, productService.findProductsByUserId(user1.getId(), 0, 2));
        assertEquals(expectedBlock2, productService.findProductsByUserId(user1.getId(), 1, 2));

    }

    @Test
    public void testFindProductByUserIdEmpty() throws InstanceNotFoundException {
        User user1 = signUpUser("Juan");
        User user2 = signUpUser("Pepe");

        Category c1 = new Category("Pelicula");
        Category c2 = new Category("Videojuego");
        categoryDao.save(c1);
        categoryDao.save(c2);

        Product p1 = productService.addProduct("Bomberman", "Videojuego Bomberman para PS1. " +
                        "Se encuentra en perfecto estado.",
                30, BigDecimal.valueOf(15),
                "Envío por código postal, con plazo de entrega de dos semanas.",
                c2.getId(), user2.getId());

        Product p2 = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(24.95F),
                "Envío por código postal, con plazo de entrega de tres semanas.",
                c1.getId(), user2.getId());
        Product p3 = productService.addProduct("Grand Theft Auto: San Andreas",
                "Videojuego Grand Theft Auto: San Andreas para PS2. Se encuentra en perfecto estado",
                60, BigDecimal.valueOf(30.00),
                "Envío por código postal, con plazo de entrega de una semana.",
                c2.getId(), user2.getId());

        Product p4 = productService.addProduct("FIFA 98",
                "Videojuego FIFA 98 para PS1. Se encuentra en perfecto estado",
                0, BigDecimal.valueOf(50.00),
                "Envío por código postal, con plazo de entrega de una semana.",
                c2.getId(), user2.getId());
        p4.setFinishingDate(LocalDateTime.now().minusMinutes(60));


        productDao.save(p1);
        productDao.save(p2);
        productDao.save(p3);
        productDao.save(p4);

        //todos los productos son del user 2, por lo que el user 1 no deberia de tener
        Block<Product> expectedBlock = new Block<>(Arrays.asList(), false);
        assertEquals(expectedBlock, productService.findProductsByUserId(user1.getId(), 0, 2));

    }
}