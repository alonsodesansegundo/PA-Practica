package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BidSummaryDto {

    private LocalDateTime dateBid;
    private BigDecimal maxPrice;
    private String productName;
    private Bid.BidType statusBid;

    public BidSummaryDto() {
    }

    public BidSummaryDto(LocalDateTime dateBid, BigDecimal maxPrice, String productName, Bid.BidType statusBid) {
        this.dateBid = dateBid;
        this.maxPrice = maxPrice;
        this.productName = productName;
        this.statusBid = statusBid;
    }

    public LocalDateTime getDateBid() {
        return dateBid;
    }

    public void setDateBid(LocalDateTime dateBid) {
        this.dateBid = dateBid;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Bid.BidType getStatusBid() {
        return statusBid;
    }

    public void setStatusBid(Bid.BidType statusBid) {
        this.statusBid = statusBid;
    }
}