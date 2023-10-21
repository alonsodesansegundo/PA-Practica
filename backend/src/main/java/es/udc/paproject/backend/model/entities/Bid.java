package es.udc.paproject.backend.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Bid {

    public enum BidType {WINNIG, WINNER, LOSER}

    private Long id;
    private LocalDateTime dateBid;
    private BigDecimal maxPrice;
    private Product product;
    private User userBid;

    public Bid() {
    }

    public Bid(LocalDateTime dateBid, BigDecimal maxPrice, Product product, User userBid) {
        this.dateBid = dateBid;
        this.maxPrice = maxPrice;
        this.product = product;
        this.userBid = userBid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "userBidId")
    public User getUserBid() {
        return userBid;
    }

    public void setUserBid(User userBid) {
        this.userBid = userBid;
    }

    @Transient
    public BidType statusBid() {
        var now = LocalDateTime.now().withNano(0);

        if (getId().equals(product.getBidWinner().getId())) {
            if (now.isAfter(product.getFinishingDate())) {
                return BidType.WINNER;
            } else {
                return BidType.WINNIG;
            }
        } else {
            return BidType.LOSER;
        }
    }

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", dateBid=" + dateBid +
                ", maxPrice=" + maxPrice +
                ", product=" + product +
                ", userBid=" + userBid +
                '}';
    }
}
