package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Bid;
import es.udc.paproject.backend.model.exceptions.IncorrectBidUserException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.MaxPriceBidException;
import es.udc.paproject.backend.model.exceptions.OutOfBidTimeException;

import java.math.BigDecimal;

public interface BidService {
    // Acción de realizar una puja de un producto --> excepción de pujar por algo fuera de plazo
    Bid bidding (Long userId, Long productId, BigDecimal maxPrice)
            throws InstanceNotFoundException, OutOfBidTimeException, MaxPriceBidException, IncorrectBidUserException;

    // Ver las pujas que ha realizado un usuario
    Block<Bid> findBidsByUserId (Long id, int page, int size) throws InstanceNotFoundException;


}
