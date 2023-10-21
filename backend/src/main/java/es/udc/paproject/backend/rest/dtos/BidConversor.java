package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Bid;

import java.util.List;
import java.util.stream.Collectors;

public class BidConversor {

    public BidConversor() {
    }

    public final static BidDto toBidDto(Bid bid){
        return new BidDto(bid.statusBid(), bid.getProduct().getActualPrice(),
                bid.getProduct().getFinishingDate());
    }

    public final static List<BidSummaryDto> toBidSummaryDtos(List<Bid> bids){
        return bids.stream().map(b -> toBidSummaryDto(b)).collect(Collectors.toList());
    }

    public final static BidSummaryDto toBidSummaryDto(Bid bid){
        return new BidSummaryDto(bid.getDateBid(), bid.getMaxPrice(), bid.getProduct().getName(),
                bid.statusBid());
    }
}