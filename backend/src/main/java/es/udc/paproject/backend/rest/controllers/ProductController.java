package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Product;
import es.udc.paproject.backend.model.exceptions.IncorrectFinishingDateException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ProductService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static es.udc.paproject.backend.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.paproject.backend.rest.dtos.ProductConversor.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final static String INCORRECT_FINISHING_DATE_EXCEPTION = "project.exceptions.IncorrectFinishingDateException";

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(IncorrectFinishingDateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleIncorrectFinishingDateException(IncorrectFinishingDateException exception, Locale locale) {
        String errorMessage = messageSource.getMessage(INCORRECT_FINISHING_DATE_EXCEPTION, null,
                INCORRECT_FINISHING_DATE_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);
    }

    @PostMapping("")
    public IdDto addProduct(@RequestAttribute Long userId, @Validated @RequestBody ProductParamsDto params)
            throws InstanceNotFoundException {
        return new IdDto(productService.addProduct(params.getName(), params.getDescription(), params.getBidMinutes(),
                params.getStartingPrice(), params.getDeliveryInformation(), params.getCategoryId(), userId).getId());
    }

    @GetMapping("/categories")
    public List<CategoryDto> findAllCategories() {
        return toCategoryDtos(productService.findAllCategories());
    }

    @GetMapping("/products")
    public BlockDto<ProductSummaryDto> findProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keywords,
            @RequestParam(defaultValue = "0") int page) {

        Block<Product> productBlock = productService.findProducts(categoryId,
                keywords != null ? keywords.trim() : null, page, 2);

        return new BlockDto<>(toProductSummaryDtos(productBlock.getItems()), productBlock.getExistMoreItems());
    }

    //la [FUNC-3] se corresponde a ver la información detallada de un producto
    @GetMapping("/products/{id}")
    public ProductDetailsDto findProductById(@PathVariable Long id) throws InstanceNotFoundException {
        return toProductDetailsDto(productService.findProductById(id));
    }

    //la [FUNC-6] se corresponde a ver los productos anunciados de un usuario
    @GetMapping("/user")
    public BlockDto<ProductAnnouncedDto> findProductsByUserId(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "0") int page) {
        Block<Product> productBlock = productService.findProductsByUserId(userId, page, 2);
        return new BlockDto<>(toProductAnnouncedDtos(productBlock.getItems()), productBlock.getExistMoreItems());
    }

}