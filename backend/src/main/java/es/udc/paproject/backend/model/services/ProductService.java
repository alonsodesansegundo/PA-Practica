package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    // Busco un producto por su id
    Product findProductById (Long id) throws InstanceNotFoundException;

    // Lista con todas las categorias --> para el desplegable
    List<Category> findAllCategories();

    // Lista paginada de productos para que los usuarios puedan pujar
    Block<Product> findProducts (Long categoryId, String keywords, int page, int size);

    // Añadir un producto para que se produzcan pujas
    Product addProduct (String name, String description, int bidMinutes, BigDecimal startPrice,
                        String  deliveryInformation, Long categoryId, Long userId) throws InstanceNotFoundException;

    // Lista de los productos que ha anunciado un usuario por id
    Block<Product> findProductsByUserId (Long id, int page, int size);

}