package com.availt.dto;

/**
 * REST body for creating a booking. bookingData and selectedMenu are JSON objects from the client.
 */
public class BookingRequest {

    private Long serviceId;
    private String category;
    private Object bookingData;
    private Object selectedMenu;
    private Double totalPrice;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getBookingData() {
        return bookingData;
    }

    public void setBookingData(Object bookingData) {
        this.bookingData = bookingData;
    }

    public Object getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(Object selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
