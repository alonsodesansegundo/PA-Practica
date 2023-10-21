package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductParamsDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal startingPrice;
    private int bidMinutes;
    private String deliveryInformation;
    private long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull()
    @Size(min=1, max=60)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull()
    @Size(min=1, max=2000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull()
    @Min(value=1)
    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    @NotNull()
    @Min(value=1)
    public int getBidMinutes() {
        return bidMinutes;
    }

    public void setBidMinutes(int bidMinutes) {
        this.bidMinutes = bidMinutes;
    }

    @NotNull()
    @Size(min=1, max=2000)
    public String getDeliveryInformation() {
        return deliveryInformation;
    }

    public void setDeliveryInformation(String deliveryInformation) {
        this.deliveryInformation = deliveryInformation;
    }

    @NotNull
    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
