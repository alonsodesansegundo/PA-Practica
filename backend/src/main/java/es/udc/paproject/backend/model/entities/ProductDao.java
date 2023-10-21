package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface ProductDao extends PagingAndSortingRepository <Product, Long>, CustomizerProductDao {

    Optional<Product> findByUserId(Long userId);

    Slice<Product> findProductsByUserIdOrderByFinishingDateDesc(Long userId, Pageable pageable);
}
