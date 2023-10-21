package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class BidDto {

    private Bid.BidType statusBid;
    private BigDecimal actualPrice;
    private long remainingTime;

    public BidDto() {
    }

    public BidDto(Bid.BidType statusBid, BigDecimal actualPrice, LocalDateTime finishingDate) {
        this.statusBid = statusBid;
        this.actualPrice = actualPrice;
        this.remainingTime = ChronoUnit.MINUTES.between(LocalDateTime.now(),finishingDate);;
    }

    public Bid.BidType getStatusBid() {
        return statusBid;
    }

    public void setStatusBid(Bid.BidType statusBid) {
        this.statusBid = statusBid;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }
}