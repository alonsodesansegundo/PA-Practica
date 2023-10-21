package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BidDao extends PagingAndSortingRepository <Bid, Long> {

    Slice<Bid> findBidsByUserBidIdOrderByDateBidDesc(Long userBidId, Pageable pageable);
}
