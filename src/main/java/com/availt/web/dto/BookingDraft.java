package com.availt.web.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * Session-scoped booking review payload before persistence.
 */
public class BookingDraft implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long serviceId;
    private String serviceName;
    private String category;
    /** String values from the form (people as string before parse). */
    private Map<String, String> bookingData;
    /** Catering: menu id + display fields */
    private Long selectedMenuId;
    private String selectedMenuName;
    private String selectedMenuItems;
    private Double selectedMenuPricePerPerson;
    private Double totalPrice;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<String, String> getBookingData() {
        return bookingData;
    }

    public void setBookingData(Map<String, String> bookingData) {
        this.bookingData = bookingData;
    }

    public Long getSelectedMenuId() {
        return selectedMenuId;
    }

    public void setSelectedMenuId(Long selectedMenuId) {
        this.selectedMenuId = selectedMenuId;
    }

    public String getSelectedMenuName() {
        return selectedMenuName;
    }

    public void setSelectedMenuName(String selectedMenuName) {
        this.selectedMenuName = selectedMenuName;
    }

    public String getSelectedMenuItems() {
        return selectedMenuItems;
    }

    public void setSelectedMenuItems(String selectedMenuItems) {
        this.selectedMenuItems = selectedMenuItems;
    }

    public Double getSelectedMenuPricePerPerson() {
        return selectedMenuPricePerPerson;
    }

    public void setSelectedMenuPricePerPerson(Double selectedMenuPricePerPerson) {
        this.selectedMenuPricePerPerson = selectedMenuPricePerPerson;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
