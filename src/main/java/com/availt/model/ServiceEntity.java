package com.availt.model;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    private String type;

    private String address;

    private String contact;

    private Double rating;

    private Double price;

    @Column(name = "image_url", length = 1024)
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    /** City or locality (join-as-provider and display). */
    private String city;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "provider_email")
    private String providerEmail;

    @Column(name = "opening_hours", length = 512)
    private String openingHours;

    @Column(name = "price_max")
    private Double priceMax;

    /** JSON array of extra image URLs for gallery strip. */
    @Column(name = "gallery_image_urls", columnDefinition = "TEXT")
    private String galleryImageUrls;

    /**
     * Community-submitted listings sort after seeded providers when false/null.
     */
    @Column(name = "community_submitted")
    private Boolean communitySubmitted;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public Double getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(Double priceMax) {
        this.priceMax = priceMax;
    }

    public String getGalleryImageUrls() {
        return galleryImageUrls;
    }

    public void setGalleryImageUrls(String galleryImageUrls) {
        this.galleryImageUrls = galleryImageUrls;
    }

    public Boolean getCommunitySubmitted() {
        return communitySubmitted;
    }

    public void setCommunitySubmitted(Boolean communitySubmitted) {
        this.communitySubmitted = communitySubmitted;
    }
}
