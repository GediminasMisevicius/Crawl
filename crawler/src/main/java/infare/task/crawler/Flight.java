package infare.task.crawler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Flight {
  
  private String departureAirport;
  private String connectingAirport;
  private String arrivalAirport;
  private LocalTime departureTime;
  private LocalTime arrivalTime;
  private BigDecimal price;
  private BigDecimal tax;
  
  public Flight() {}

  public String getDepartureAirport() {
    return departureAirport;
  }

  public void setDepartureAirport(String departureAirport) {
    this.departureAirport = departureAirport;
  }

  public String getConnectingAirport() {
    return connectingAirport;
  }

  public void setConnectingAirport(String connectingAirport) {
    this.connectingAirport = connectingAirport;
  }

  public String getArrivalAirport() {
    return arrivalAirport;
  }

  public void setArrivalAirport(String arrivalAirport) {
    this.arrivalAirport = arrivalAirport;
  }

  public LocalTime getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(LocalTime departureTime) {
    this.departureTime = departureTime;
  }

  public LocalTime getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(LocalTime arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getTax() {
    return tax;
  }

  public void setTax(BigDecimal tax) {
    this.tax = tax;
  }

  @Override
  public String toString() {
    return "Flight [departureAirport=" + departureAirport + ", connectingAirport="
        + connectingAirport + ", arrivalAirport=" + arrivalAirport + ", departureTime="
        + departureTime + ", arrivalTime=" + arrivalTime + ", price=" + price + ", tax=" + tax
        + "]";
  }
  


}
