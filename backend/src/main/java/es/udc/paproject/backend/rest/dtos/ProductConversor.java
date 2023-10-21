package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductConversor {

    private ProductConversor() {}

    //PARA VER LOS RESULTADOS DE LA BÚSQUEDA
    private final static ProductSummaryDto toProductSummaryDto(Product product) {
        return new ProductSummaryDto(product.getId(),
                product.getCategory().getId(), product.getName(),
                product.getActualPrice(),
                product.getRemainingMinutes());
    }

    public final static List<ProductSummaryDto> toProductSummaryDtos(List<Product> products) {
        return products.stream().map(p -> toProductSummaryDto(p)).collect(Collectors.toList());
    }

    //PARA VER LOS DETALLES DE UN PRODUCTO
    //solo vamos a ver los detalles de un producto de uno en uno, por eso no hago el de la lista
    public final static ProductDetailsDto toProductDetailsDto(Product product){
        return new ProductDetailsDto(product.getId(),product.getCategory().getId(), product.getName(), product.getDescription(),
                product.getUser().getFirstName()+" "+product.getUser().getLastName(),
                product.getStartingDate(),product.getStartingPrice(),
                product.getActualPrice(), product.getDeliveryInformation(), product.getRemainingMinutes(), product.getBidWinner());
    }

    //PARA VER LOS PRODUCTOS ANUNCIADOS DE UN USUARIO
    public final static ProductAnnouncedDto toProductAnnouncedDto(Product product){
        if(product.getBidWinner()==null)    //comprobar que el producto tenga pujas
            return new ProductAnnouncedDto(product.getName(), product.getActualPrice(),
                    "No existen pujas", product.getRemainingMinutes());
        else
            return new ProductAnnouncedDto(product.getName(), product.getActualPrice(),
                    product.getBidWinner().getUserBid().getEmail(), product.getRemainingMinutes());
    }

    public final static List<ProductAnnouncedDto> toProductAnnouncedDtos(List<Product> products) {
        return products.stream().map(p -> toProductAnnouncedDto(p)).collect(Collectors.toList());
    }
}