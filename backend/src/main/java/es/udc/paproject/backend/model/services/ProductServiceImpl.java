package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.IncorrectFinishingDateException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public Product findProductById(Long id) throws InstanceNotFoundException {
        Optional<Product> product = productDao.findById(id);

        if (!product.isPresent()) {
            throw new InstanceNotFoundException("project.entities.product", id);
        }

        return product.get();
    }

    @Override
    public List<Category> findAllCategories() {

        Iterable<Category> categories = categoryDao.findAll(Sort.by(Sort.Direction.ASC,"name"));
        List <Category> categoryList = new ArrayList<>();

        categories.forEach(c -> categoryList.add(c));

        return categoryList; //duda cambiarle el nombre a esta lista
    }

    @Override
    public Block<Product> findProducts(Long categoryId, String keywords, int page, int size) {

        Slice<Product> slice = productDao.find(categoryId, keywords, page, size);

        return new Block<>(slice.getContent(), slice.hasNext());
    }

    @Override
    public Product addProduct(String name, String description, int bidMinutes, BigDecimal startPrice,
                              String deliveryInformation, Long categoryId, Long userId) throws InstanceNotFoundException {

        Optional<User> user = userDao.findById(userId);
        Optional<Category> category = categoryDao.findById(categoryId);

        if (!category.isPresent()) {
            throw new InstanceNotFoundException("project.entities.category", categoryId);
        }

        LocalDateTime dateNow = LocalDateTime.now().withNano(0);

        Product product = new Product(name, description, startPrice, dateNow.plusMinutes(bidMinutes).withNano(0), dateNow,
                deliveryInformation, category.get(), user.get());
        productDao.save(product);

        return product;
    }

    @Override
    public Block<Product> findProductsByUserId(Long userId, int page, int size){
        //Slice<Product> slice = productDao.findByUserId(userId, page, size);
        Slice<Product> slice = productDao.findProductsByUserIdOrderByFinishingDateDesc(userId, PageRequest.of(page, size));
        return new Block<>(slice.getContent(), slice.hasNext());
    }
}
