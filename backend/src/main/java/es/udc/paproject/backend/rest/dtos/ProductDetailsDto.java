package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Bid;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductDetailsDto {
    private Long id;
    private Long categoryId;
    private String productName;
    private String description;
    private String sellerName;  //nombre del vendedor
    private LocalDateTime startingDate; // dia, mes y año && hora y minutos
    //tiempo restante para que acabe la puja (minutos) o un indicador de que termino --> 0
    private long remainingMinutes;
    private BigDecimal startingPrice;    //precio de salida
    private BigDecimal actualPrice;
    private String deliveryInformation;

    private Boolean isBid;

    public ProductDetailsDto(Long id, Long categoryId, String productName, String description,
                             String sellerName, LocalDateTime startingDate,
                             BigDecimal startingPrice, BigDecimal actualPrice, String deliveryInformation,
                             long remainingMinutes, Bid bidWinner) {
        this.id = id;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.sellerName = sellerName;
        this.startingDate = startingDate;

        this.remainingMinutes = remainingMinutes;

        this.startingPrice = startingPrice;
        this.actualPrice = actualPrice;
        this.deliveryInformation = deliveryInformation;

        //para ver si un producto tiene pujas o no
        if(bidWinner == null)
            this.isBid = false;
        else
            this.isBid = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Boolean getBid() {
        return isBid;
    }

    public void setBid(Boolean bid) {
        isBid = bid;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return productName;
    }

    public void setName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public long getRemainingMinutes() {
        return remainingMinutes;
    }

    public void setRemainingMinutes(long remainingMinutes) {
        this.remainingMinutes = remainingMinutes;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getDeliveryInformation() {
        return deliveryInformation;
    }

    public void setDeliveryInformation(String deliveryInformation) {
        this.deliveryInformation = deliveryInformation;
    }
}
