package com.availt.controller;

import com.availt.dto.BookingRequest;
import com.availt.model.Booking;
import com.availt.model.User;
import com.availt.service.BookingService;
import com.availt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody BookingRequest request, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body(msg("Unauthorized"));
        }
        User user = userService.findByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.status(401).body(msg("Unauthorized"));
        }
        try {
            Booking saved = bookingService.create(user, request);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(msg(ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(msg(ex.getMessage()));
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> listForUser(@PathVariable Long userId, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userService.findByEmail(principal.getName());
        if (user == null || !user.getId().equals(userId)) {
            return ResponseEntity.status(403).body(msg("Forbidden"));
        }
        return ResponseEntity.ok(bookingService.listForUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        User user = userService.findByEmail(principal.getName());
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        Booking b = bookingService.getById(id);
        if (b == null) {
            return ResponseEntity.notFound().build();
        }
        if (!b.getUserId().equals(user.getId())) {
            return ResponseEntity.status(403).body(msg("Forbidden"));
        }
        return ResponseEntity.ok(b);
    }

    private Map<String, String> msg(String m) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("message", m);
        return map;
    }
}
