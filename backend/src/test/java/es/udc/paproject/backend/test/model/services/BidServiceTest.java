package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.BidService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BidServiceTest {
    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private BidService bidService;

    @Autowired
    private BidDao bidDao;

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

    private Product InsertProduct() throws InstanceNotFoundException {
        Category category1 = new Category("Pelicula");
        categoryDao.save(category1);

        User user1 = signUpUser("Andrea");

        Product product = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(24.95F),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), user1.getId());
        productDao.save(product);

        return product;
    }

    @Test
    public void testBiddingNotExistentUserId() throws InstanceNotFoundException {
        Product product = InsertProduct();

        assertThrows(
                InstanceNotFoundException.class, () ->
                        bidService.bidding(NON_EXISTENT_ID, product.getId(), BigDecimal.valueOf(30)));
    }

    @Test
    public void testBiddingNotExistentProductId() {
        User user1 = signUpUser("Pedro");

        assertThrows(
                InstanceNotFoundException.class, () ->
                        bidService.bidding(user1.getId(), NON_EXISTENT_ID, BigDecimal.valueOf(30)));
    }

    @Test
    public void testBiddingOutTime() throws InstanceNotFoundException {
        User user1 = signUpUser("Pedro");
        Product product = InsertProduct();

        //Para poder comprobar la excepción de que no se pueda pujar fuera de tiempo,
        //modificamos el tiempo de finalización de la puja, a hace 20 minutos.
        product.setFinishingDate(LocalDateTime.now().minusMinutes(20));
        productDao.save(product);

        assertThrows(
                OutOfBidTimeException.class, () ->
                        bidService.bidding(user1.getId(), product.getId(), BigDecimal.valueOf(30)));
    }

    @Test
    public void testSellerBiding() throws InstanceNotFoundException {
        Product product = InsertProduct();

        assertThrows(
                IncorrectBidUserException.class, () ->
                        bidService.bidding(product.getUser().getId(), product.getId(), BigDecimal.valueOf(30)));
    }


    @Test
    public void testBiddingByMinusPrice() throws InstanceNotFoundException {
        User seller = signUpUser("Pedro");

        Category category1 = new Category("Pelicula");
        categoryDao.save(category1);

        Product product = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(24.95F),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), seller.getId());
        productDao.save(product);

        User user1 = signUpUser("Celia");


        assertThrows(
                MaxPriceBidException.class, () ->
                        bidService.bidding(user1.getId(), product.getId(), BigDecimal.valueOf(12)));
    }

    @Test
    public void testBidding() throws InstanceNotFoundException, OutOfBidTimeException, MaxPriceBidException,
            IncorrectBidUserException {
        Category category1 = new Category("Pelicula");
        categoryDao.save(category1);

        User seller = signUpUser("Alfredo");

        Product product = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(10),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), seller.getId());
        productDao.save(product);

        User customer = signUpUser("Celia");
        Bid bid = bidService.bidding(customer.getId(), product.getId(), BigDecimal.valueOf(12));

        Optional<Bid> bid2 = bidDao.findById(bid.getId());

        //Comprobamos que hemos creado la puja correctamente.
        assertEquals(bid2.get().getId(), bid.getId());
        assertEquals(bid2.get().getDateBid(), bid.getDateBid());
        assertEquals(bid2.get().getMaxPrice(), bid.getMaxPrice());
        assertEquals(Bid.BidType.WINNIG, bid.statusBid());
        assertEquals(product, bid.getProduct());
        assertEquals(customer, bid.getUserBid());

        //Comprobamos además, que como somos la primera puja el precio actual del producto no hay sido modificado.
        assertEquals(BigDecimal.valueOf(10), product.getActualPrice());

    }

    @Test
    public void testBiddingFirst() throws InstanceNotFoundException, OutOfBidTimeException, MaxPriceBidException,
            IncorrectBidUserException {
        Category category1 = new Category("Pelicula");
        categoryDao.save(category1);

        User seller = signUpUser("Alfredo");

        Product product = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(10.00),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), seller.getId());
        productDao.save(product);

        User customer = signUpUser("Celia");
        Bid bid = bidService.bidding(customer.getId(), product.getId(), BigDecimal.valueOf(10.00));

        Optional<Bid> bid2 = bidDao.findById(bid.getId());

        //Comprobamos que hemos creado la puja correctamente.
        assertEquals(bid2.get().getId(), bid.getId());
        assertEquals(bid2.get().getDateBid(), bid.getDateBid());
        assertEquals(bid2.get().getMaxPrice(), bid.getMaxPrice());
        assertEquals(Bid.BidType.WINNIG, bid.statusBid());
        assertEquals(product, bid.getProduct());
        assertEquals(customer, bid.getUserBid());

        //Comprobamos además, que como somos la primera puja el precio actual del producto no hay sido modificado.
        assertEquals(BigDecimal.valueOf(10.00), product.getActualPrice());

        User user1 = signUpUser("Pedro");
        assertThrows(
                MaxPriceBidException.class, () ->
                        bidService.bidding(user1.getId(), product.getId(), BigDecimal.valueOf(10.00)));

    }

    @Test
    public void testTwoBidsWinFirstBid() throws InstanceNotFoundException, OutOfBidTimeException, MaxPriceBidException,
            IncorrectBidUserException {
        Category category1 = new Category("Pelicula");
        categoryDao.save(category1);

        User seller = signUpUser("Alfredo");

        Product product = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(10),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), seller.getId());
        productDao.save(product);

        User customer = signUpUser("Celia");
        Bid bid1 = bidService.bidding(customer.getId(), product.getId(), BigDecimal.valueOf(12));

        User customer2 = signUpUser("Miguel");
        Bid bid2 = bidService.bidding(customer2.getId(), product.getId(), BigDecimal.valueOf(11));

        //Comprobamos el estado de las dos pujas, para ver cual va ganando
        assertEquals(product.getBidWinner(), bid1);
        assertEquals(Bid.BidType.WINNIG, bid1.statusBid());
        assertEquals(Bid.BidType.LOSER, bid2.statusBid());

        //Comprobamos además que el precio actual del producto esta bien recalculado
        assertEquals(BigDecimal.valueOf(11.5), product.getActualPrice());
    }

    @Test
    public void testTwoBidsWinLastBid() throws InstanceNotFoundException, OutOfBidTimeException, MaxPriceBidException,
            IncorrectBidUserException {
        Category category1 = new Category("Pelicula");
        categoryDao.save(category1);

        User seller = signUpUser("Alfredo");

        Product product = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(10),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), seller.getId());
        productDao.save(product);

        User customer = signUpUser("Celia");
        Bid bid1 = bidService.bidding(customer.getId(), product.getId(), BigDecimal.valueOf(12));

        User customer2 = signUpUser("Miguel");
        Bid bid2 = bidService.bidding(customer2.getId(), product.getId(), BigDecimal.valueOf(14));

        //Comprobamos el estado de las dos pujas, para ver cual va ganando
        assertEquals(product.getBidWinner(), bid2);
        assertEquals(Bid.BidType.LOSER, bid1.statusBid());
        assertEquals(Bid.BidType.WINNIG, bid2.statusBid());

        //Comprobamos además que el precio actual del producto esta bien recalculado
        assertEquals(BigDecimal.valueOf(12.5), product.getActualPrice());
    }

    @Test
    public void testLotsOfBids() throws InstanceNotFoundException, OutOfBidTimeException, MaxPriceBidException,
            IncorrectBidUserException {
        Category category1 = new Category("Zapatos");
        categoryDao.save(category1);

        User seller = signUpUser("Alfredo");

        Product product = productService.addProduct("Nike Jordan 2", "Jordan",
                50, BigDecimal.valueOf(10.00),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), seller.getId());
        productDao.save(product);

        User customer = signUpUser("Celia");
        Bid bid1 = bidService.bidding(customer.getId(), product.getId(), BigDecimal.valueOf(20.00));
        System.out.println("precio1: " + product.getActualPrice().toString());
        User customer2 = signUpUser("Miguel");
        Bid bid2 = bidService.bidding(customer2.getId(), product.getId(), BigDecimal.valueOf(15.00));
        System.out.println("precio2: " + product.getActualPrice().toString());

        User customer3 = signUpUser("Adrián");
        Bid bid3 = bidService.bidding(customer3.getId(), product.getId(), BigDecimal.valueOf(20.00));
        System.out.println("precio3: " + product.getActualPrice().toString());

        Bid bid4 = bidService.bidding(customer2.getId(), product.getId(), BigDecimal.valueOf(25.00));
        System.out.println("precio4: " + product.getActualPrice().toString());


        //Comprobamos el estado de las pujas, para ver cual va ganando
        assertEquals(product.getBidWinner(), bid4);
        assertEquals(Bid.BidType.LOSER, bid1.statusBid());
        assertEquals(Bid.BidType.LOSER, bid2.statusBid());
        assertEquals(Bid.BidType.LOSER, bid3.statusBid());
        assertEquals(Bid.BidType.WINNIG, bid4.statusBid());

        //Comprobamos además que el precio actual del producto esta bien recalculado
        assertEquals(BigDecimal.valueOf(20.5), product.getActualPrice());
    }

    @Test
    public void testBiddingIncrements() throws InstanceNotFoundException, OutOfBidTimeException, MaxPriceBidException,
            IncorrectBidUserException {
        Category category1 = new Category("Pelicula");
        categoryDao.save(category1);

        User seller = signUpUser("Alfredo");

        Product product = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(10.00),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), seller.getId());
        productDao.save(product);

        User customer = signUpUser("Celia");
        Bid bid1 = bidService.bidding(customer.getId(), product.getId(), BigDecimal.valueOf(12.00));

        User customer2 = signUpUser("Pedro");
        Bid bid2 = bidService.bidding(customer2.getId(), product.getId(), BigDecimal.valueOf(11.90));

        assertEquals(Bid.BidType.WINNIG, bid1.statusBid());

        //Comprobamos además, que como somos la primera puja el precio actual del producto no hay sido modificado.
        assertEquals(BigDecimal.valueOf(12.00), product.getActualPrice());
    }


    @Test
    public void testFindBidsNotExistingUser() {

        assertThrows(
                InstanceNotFoundException.class, () ->
                        bidService.findBidsByUserId(NON_EXISTENT_ID, 2, 2));
    }

    @Test
    public void testFindBidsByUserId() throws InstanceNotFoundException, OutOfBidTimeException, MaxPriceBidException,
            IncorrectBidUserException {
        User user1 = signUpUser("Andrea");
        User user2 = signUpUser("Sabela");


        Category category1 = new Category("Zapatos");
        categoryDao.save(category1);

        Product product1 = productService.addProduct("Nike Jordan 2", "Jordan",
                50, BigDecimal.valueOf(10),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), user1.getId());
        productDao.save(product1);

        Product product2 = productService.addProduct("Star Trek", "Star Trek, Edición Coleccionista",
                45, BigDecimal.valueOf(10),
                "envío por codigo postal, con plazo de entrega de una semana",
                category1.getId(), user1.getId());
        productDao.save(product2);

        Bid bid1 = bidService.bidding(user2.getId(), product1.getId(), BigDecimal.valueOf(20.00));
        Bid bid2 = bidService.bidding(user2.getId(), product2.getId(), BigDecimal.valueOf(30.00));

        bid2.setDateBid(LocalDateTime.now().minusMinutes(10));
        bidDao.save(bid2);

        Block<Bid> expectedBlock = new Block<>(Arrays.asList(bid1, bid2), false);

        assertEquals(expectedBlock, bidService.findBidsByUserId(user2.getId(), 0, 2));

    }
}