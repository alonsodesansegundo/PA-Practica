package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;

public class ProductAnnouncedDto {
    private String productName;
    private BigDecimal actualPrice;
    //tiempo restante para que acabe la puja (minutos) o un indicador de que termino --> 0
    private long remainingMinutes;
    private String mailWinner;  //vacío si no hay puja

    public ProductAnnouncedDto(String productName, BigDecimal actualPrice,
                               String mailWinner, long remainingMinutes) {
        this.productName = productName;
        this.actualPrice = actualPrice;
        //atentos aqui
        this.remainingMinutes = remainingMinutes;
        if(mailWinner==null)
            this.mailWinner="";
        else
            this.mailWinner = mailWinner;
    }

    public String getName() {
        return productName;
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

    public long getRemainingMinutes() {
        return remainingMinutes;
    }

    public void setRemainingMinutes(long remainingMinutes) {
        this.remainingMinutes = remainingMinutes;
    }

    public String getMailWinner() {
        return mailWinner;
    }

    public void setMailWinner(String mailWinner) {
        this.mailWinner = mailWinner;
    }
}
