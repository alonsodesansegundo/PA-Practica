package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ProductSummaryDto {
    private long id;
    private long categoryId;
    private String productName;
    private BigDecimal actualPrice;
    private long remainingTime;    //tiempo restante en minutos

    public ProductSummaryDto(long id, long categoryId, String productName,
                             BigDecimal actualPrice, long remainingTime) {
        this.id = id;
        this.categoryId = categoryId;
        this.productName = productName;
        this.actualPrice = actualPrice;
        //atentos aqui
        this.remainingTime = remainingTime;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return productName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String productName) {
        this.productName = productName;
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
