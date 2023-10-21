package es.udc.paproject.backend.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal startingPrice;
    private BigDecimal actualPrice;
    private LocalDateTime finishingDate;
    private LocalDateTime startingDate;
    private String deliveryInformation;
    private Category category;
    private User user;
    private Bid bidWinner;
    //tiempo restante de puja
    private long remainingMinutes;
    private long version;

    public Product() {
    }

    //constructor sin el precio actual, ya que al comienzo va a ser igual al precio de salida
    //sin la puja ganadora ya que al principio no hay
    public Product(String name, String description,
                   BigDecimal startingPrice, LocalDateTime finishingDate,
                   LocalDateTime startingDate, String deliveryInformation,
                   Category category, User user) {
        this.name = name;
        this.description = description;
        this.startingPrice = startingPrice;
        //ojo aqui
        this.actualPrice = startingPrice;
        this.finishingDate = finishingDate;
        this.startingDate = startingDate;
        this.deliveryInformation = deliveryInformation;
        this.category = category;
        this.user = user;
        //ojo aqui
        this.bidWinner = null;
        //tiempo restante de puja
        this.remainingMinutes = ChronoUnit.MINUTES.between(LocalDateTime.now(),this.finishingDate);
        if(this.remainingMinutes<0)
            this.remainingMinutes=0;
    }

    public long getRemainingMinutes() {
        this.remainingMinutes = ChronoUnit.MINUTES.between(LocalDateTime.now(),this.finishingDate);
        if(this.remainingMinutes<0)
            this.remainingMinutes=0;
        return remainingMinutes;
    }

    public void setRemainingMinutes(long remainingMinutes) {
        this.remainingMinutes = remainingMinutes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDateTime getFinishingDate() {
        return finishingDate;
    }

    public void setFinishingDate(LocalDateTime finishingDate) {
        this.finishingDate = finishingDate;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDateTime startingDate) {
        this.startingDate = startingDate;
    }

    public String getDeliveryInformation() {
        return deliveryInformation;
    }

    public void setDeliveryInformation(String deliveryInformation) {
        this.deliveryInformation = deliveryInformation;
    }

    @Version
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="categoryId")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="userId")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="bidWinnerId")
    public Bid getBidWinner() {
        return bidWinner;
    }

    public void setBidWinner(Bid bidWinner) {
        this.bidWinner = bidWinner;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startingPrice=" + startingPrice +
                ", actualPrice=" + actualPrice +
                ", finishingDate=" + finishingDate +
                ", startingDate=" + startingDate +
                ", deliveryInformation='" + deliveryInformation + '\'' +
                ", category=" + category +
                ", user=" + user +
                ", bidWinner=" + bidWinner +
                '}';
    }
}