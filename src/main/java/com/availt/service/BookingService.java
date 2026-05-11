package com.availt.service;

import com.availt.dto.BookingRequest;
import com.availt.model.Booking;
import com.availt.model.User;
import com.availt.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public Booking create(User user, BookingRequest req) throws Exception {
        if (req.getServiceId() == null) {
            throw new IllegalArgumentException("serviceId is required");
        }
        if (req.getCategory() == null || req.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("category is required");
        }
        if (req.getBookingData() == null) {
            throw new IllegalArgumentException("bookingData is required");
        }
        if ("Catering".equalsIgnoreCase(req.getCategory().trim())
                && (req.getSelectedMenu() == null)) {
            throw new IllegalArgumentException("Catering bookings require a selected menu");
        }
        Booking b = new Booking();
        b.setUserId(user.getId());
        b.setServiceId(req.getServiceId());
        b.setCategory(req.getCategory().trim());
        b.setBookingData(objectMapper.writeValueAsString(req.getBookingData()));
        if (req.getSelectedMenu() != null) {
            b.setSelectedMenu(objectMapper.writeValueAsString(req.getSelectedMenu()));
        } else {
            b.setSelectedMenu(null);
        }
        b.setTotalPrice(req.getTotalPrice() != null ? req.getTotalPrice() : 0d);
        b.setStatus("CONFIRMED");
        return bookingRepository.save(b);
    }

    public Booking getById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> listForUser(Long userId) {
        return bookingRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
}
