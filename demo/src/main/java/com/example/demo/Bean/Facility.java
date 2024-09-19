package com.example.demo.Bean;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityID;

    @Column(nullable = false)
    private String facilityName;

    @Column(nullable = false)
    private String facilityImage;

    @Column(nullable = false)
    private BigDecimal pricePerHour;

    @Column(nullable = false)
    private Integer totalCourt;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalTime openingHour;

    @Column(nullable = false)
    private LocalTime  closingHour;

    @Column(nullable = false)
    private String facilityType;

    @Column(nullable = false)
    private Boolean status =true;
    @OneToMany(mappedBy = "facility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Court> courts = new ArrayList<>();


    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Facility() {
    }

    public List<Court> getCourts() {
        return courts;
    }

    public void setCourts(List<Court> courts) {
        this.courts = courts;
    }

//    public Facility(String facilityName, String facilityImage, BigDecimal pricePerHour, Integer totalCourt, String location, LocalTime openingHour, LocalTime closingHour, String facilityType, Boolean status) {
//        this.facilityName = facilityName;
//        this.facilityImage = facilityImage;
//        this.pricePerHour = pricePerHour;
//        this.totalCourt = totalCourt;
//        this.location = location;
//        this.openingHour = openingHour;
//        this.closingHour = closingHour;
//        this.facilityType = facilityType;
//        this.status = status;
//    }

    public Facility(String facilityName, String facilityImage, BigDecimal pricePerHour, Integer totalCourt, String location, LocalTime openingHour, LocalTime closingHour, String facilityType, Boolean status, List<Court> courts, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.facilityName = facilityName;
        this.facilityImage = facilityImage;
        this.pricePerHour = pricePerHour;
        this.totalCourt = totalCourt;
        this.location = location;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.facilityType = facilityType;
        this.status = status;
        this.courts = courts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Long facilityID) {
        this.facilityID = facilityID;
    }

    public String getFacilityName() {
        return facilityName;
    }

    public void setFacilityName(String facilityName) {
        this.facilityName = facilityName;
    }

    public String getFacilityImage() {
        return facilityImage;
    }

    public void setFacilityImage(String facilityImage) {
        this.facilityImage = facilityImage;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Integer getTotalCourt() {
        return totalCourt;
    }

    public void setTotalCourt(Integer totalCourt) {
        this.totalCourt = totalCourt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalTime getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(LocalTime openingHour) {
        this.openingHour = openingHour;
    }

    public LocalTime getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(LocalTime closingHour) {
        this.closingHour = closingHour;
    }

    public String getFacilityType() {
        return facilityType;
    }

    public void setFacilityType(String facilityType) {
        this.facilityType = facilityType;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
